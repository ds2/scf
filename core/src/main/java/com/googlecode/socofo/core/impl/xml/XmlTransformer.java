/**
 * SoCoFo Source Code Formatter
 * Copyright (C) 2009 Dirk Strauss <lexxy23@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package com.googlecode.socofo.core.impl.xml;

import java.util.Stack;
import java.util.logging.Logger;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

import com.google.inject.Inject;
import com.googlecode.socofo.core.api.LineHandler;
import com.googlecode.socofo.core.api.SourceTransformer;
import com.googlecode.socofo.core.api.SourceWriter;
import com.googlecode.socofo.grammar.XmlGrammar;
import com.googlecode.socofo.grammar.XmlParser;
import com.googlecode.socofo.rules.api.RuleSet;
import com.googlecode.socofo.rules.api.XmlFormatRules;

/**
 * The xml transformer.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class XmlTransformer implements SourceTransformer {
	/**
	 * the source writer to write the XML result to
	 */
	@Inject
	private SourceWriter sw = null;
	/**
	 * The rules to transform the content
	 */
	private XmlFormatRules rules = null;
	/**
	 * A logger
	 */
	private static final transient Logger log = Logger
			.getLogger(XmlTransformer.class.getName());
	/**
	 * the xml grammar that has been loaded.
	 */
	private XmlGrammar grammar = null;
	/**
	 * The line handler.
	 */
	@Inject
	private LineHandler lh = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getResult() {
		final String rc = sw.getResult();
		return rc;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void loadRules(final RuleSet r) {
		if (r == null) {
			log.warning("No rules given!");
			return;
		}
		rules = r.getXmlFormatRules();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void parseContent(final String s) {
		if (s == null || s.length() <= 0) {
			log.warning("No content given!");
			return;
		}
		final ANTLRStringStream ss = new ANTLRStringStream(s);
		final CharStream input = ss;
		grammar = new XmlGrammar(input);
		TokenStream tr = null;
		tr = new CommonTokenStream(grammar);
		XmlParser parser = new XmlParser(tr);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void performTranslation() {
		log.entering(XmlTransformer.class.getName(), "performTranslation");
		if (sw == null) {
			log.severe("No source writer has been injected!");
			return;
		}
		log.finer("preparing source writer");
		sw.prepare();
		if (rules == null || rules.getCommonAttributes() == null) {
			log.severe("No rules have been loaded!");
			return;
		}
		log.finer("Setting rules and attributes");
		sw.setCommonAttributes(rules.getCommonAttributes());
		lh.setTabSize(rules.getCommonAttributes().getTabSize());
		Token token;
		int currentIndent = 0;
		log.finer("Starting token run");
		XmlObject currentObject = null;
		Stack<String> elementStack = new Stack<String>();
		XmlObject lastObject = null;
		while ((token = grammar.nextToken()) != Token.EOF_TOKEN) {
			log.finest("Token (" + token.getType() + ") for this run: "
					+ token.getText());
			switch (token.getType()) {
			case XmlGrammar.PI_START:
				currentObject = new ProcessingInstruction();
				break;
			case XmlGrammar.PI_STOP:
				// write Element
				currentObject.writeElement(lastObject, currentIndent, sw,
						rules, lh);
				lastObject = currentObject;
				currentObject = null;
				break;
			case XmlGrammar.WS:
				if (currentObject != null) {
					break;
				}
				sw.addToLine(" ");
				break;
			case XmlGrammar.GENERIC_ID:
				if (currentObject != null) {
					if (currentObject.getElementName() == null) {
						// this is an element name
						currentObject.setElementName(token.getText());
					} else {
						// it is an attribute name
						currentObject.setAttributName(token.getText());
					}
				}
				break;
			case XmlGrammar.TAG_START_OPEN:
				currentObject = new StartElement();
				break;
			case XmlGrammar.TAG_CLOSE:
				// write element
				currentObject.writeElement(currentIndent, sw, rules, lh);
				if (!currentObject.isEndTag()) {
					currentIndent++;
				} else {
					currentIndent--;
				}
				currentObject = null;
				break;
			case XmlGrammar.TAG_EMPTY_CLOSE:
				currentObject.setEndSequence(token.getText());
				currentObject.writeElement(currentIndent, sw, rules, lh);
				currentObject = null;
				break;
			case XmlGrammar.TAG_END_OPEN:
				currentObject = new EndElement();
				break;
			case XmlGrammar.ATTR_EQ:
				// sw.addToLine(token.getText());
				break;
			case XmlGrammar.ATTR_VALUE:
				// sw.addToLine(token.getText());
				if (currentObject != null) {
					currentObject.setAttributValue(token.getText());
				}
				break;
			case XmlGrammar.PCDATA:
				currentObject = new Text();
				currentObject.setInnerContent(token.getText());
				currentObject.writeElement(currentIndent, sw, rules, lh);
				currentObject = null;
				break;
			case XmlGrammar.CDATA_SECTION:
				sw.addToLine(token.getText());
				currentObject = null;
				break;
			case XmlGrammar.COMMENT_SECTION:
				currentObject = new Comment();
				currentObject.setInnerContent(token.getText());
				currentObject.writeElement(currentIndent, sw, rules, lh);
				currentObject = null;
				break;
			default:
				log.warning("unknown token: type=" + token.getType()
						+ " with content " + token.getText());
			}
		}
		log.finer("finishing the result");
		sw.finish();
		log.exiting(XmlTransformer.class.getName(), "performTranslation");
	}

	/**
	 * Sets the source writer. This method here is usually called in a test
	 * case. DO NOT USE IT IN PRODUCTION!!
	 * 
	 * @param s
	 *            the source writer to use
	 */
	protected void setTestSw(final SourceWriter s) {
		this.sw = s;
	}

	/**
	 * Sets the test line handler to perform some unit tests.
	 * 
	 * @param i
	 *            the line handler to use for performing tests
	 */
	public void setTestLh(final LineHandler i) {
		this.lh = i;
	}

}
