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

import java.util.List;
import java.util.Stack;
import java.util.Map.Entry;
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
import com.googlecode.socofo.rules.api.FinalBracketPolicy;
import com.googlecode.socofo.rules.api.NewlineRules;
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
		TokenStream tr=null;
		tr=new CommonTokenStream(grammar);
		XmlParser parser=new XmlParser(tr);
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
		Stack<String> elementStack=new Stack<String>();
		while ((token = grammar.nextToken()) != Token.EOF_TOKEN) {
			log.finest("Token (" + token.getType() + ") for this run: "
					+ token.getText());
			switch (token.getType()) {
			case XmlGrammar.PI_START:
				currentObject = new ProcessingInstruction();
				break;
			case XmlGrammar.PI_STOP:
				// write Element
				writeElement(currentIndent, currentObject);
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
				writeElement(currentIndent, currentObject);
				if (!currentObject.isEndTag()) {
					currentIndent++;
				} else {
					currentIndent--;
				}
				currentObject = null;
				break;
			case XmlGrammar.TAG_EMPTY_CLOSE:
				currentObject.setEndSequence(token.getText());
				writeElement(currentIndent, currentObject);
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
				writeElement(currentIndent, currentObject);
				currentObject = null;
				break;
			case XmlGrammar.CDATA_SECTION:
				sw.addToLine(token.getText());
				currentObject = null;
				break;
			case XmlGrammar.COMMENT_SECTION:
				currentObject = new Comment();
				currentObject.setInnerContent(token.getText());
				writeElement(currentIndent, currentObject);
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
	 * Writes an xml object to the source writer.
	 * 
	 * @param indent
	 *            the current indent
	 * @param xo
	 *            the object to write
	 *            @deprecated use {@link BaseXmlObject#writeElement(int, SourceWriter, XmlFormatRules, LineHandler)}
	 */
	@Deprecated
	private void writeElement(final int indent, final XmlObject xo) {
		log.entering(XmlTransformer.class.getName(), "writeElement",
				new Object[] { indent, xo });
		sw.addToLine(indent, xo.getStartSequence());
		if (xo.getElementName() != null) {
			sw.addToLine(xo.getElementName());
		}
		final NewlineRules nlRules = rules.getNewlineRules();
		if (xo.hasAttributes()) {
			for (Entry<String, String> keyValuePair : xo.getAttributes()
					.entrySet()) {
				if (nlRules != null && nlRules.getAfterEachXmlAttribute()) {
					sw.commitLine(false);
				} else {
					sw.addToLine(" ");
				}
				sw.addToLine(indent + 1, keyValuePair.getKey() + "="
						+ keyValuePair.getValue());
			}
		}
		// write inner content
		int additionalIndent = 0;
		if (xo instanceof Comment) {
			additionalIndent = 3;
		}
		final int commentLineWidth = lh.calculateContentLineWidth(rules
				.getCommonAttributes().getMaxLinewidth(), additionalIndent);
		final String innerContentClean = lh.cleanComment(xo.getInnerContent());
		final List<String> lines = lh.breakContent(commentLineWidth,
				innerContentClean, 0, rules.getCommentsRules().getBreakType());
		for (String line : lines) {
			final StringBuffer lineToPrint = new StringBuffer();
			if (xo instanceof Comment) {
				lineToPrint.append(" * ");
			}
			lineToPrint.append(line);
			sw.addLine(indent, lineToPrint.toString());
		}

		// align final bracket
		FinalBracketPolicy fbp = rules.getAlignFinalBracketOnNewline();
		if (fbp == null) {
			fbp = FinalBracketPolicy.Never;
		}
		switch (fbp) {
		case Always:
			sw.commitLine(false);
			break;
		case Never:
			break;
		case OnAttributes:
			if (xo.hasAttributes()) {
				sw.commitLine(false);
			}
			break;
		default:
			break;
		}
		sw.addToLine(xo.getEndSequence());

		if (nlRules != null && nlRules.getAfterXmlEndTag() && xo.isEndTag()) {
			sw.commitLine(false);
		}
		log.exiting(XmlTransformer.class.getName(), "writeElement");
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
