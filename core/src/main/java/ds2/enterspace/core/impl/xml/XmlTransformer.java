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
package ds2.enterspace.core.impl.xml;

import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.Token;

import com.google.inject.Inject;

import ds2.enterspace.core.api.LineHandler;
import ds2.enterspace.core.api.SourceTransformer;
import ds2.enterspace.core.api.SourceWriter;
import ds2.enterspace.rules.api.FinalBracketPolicy;
import ds2.enterspace.rules.api.NewlineRules;
import ds2.enterspace.rules.api.RuleSet;
import ds2.enterspace.rules.api.XmlFormatRules;
import ds2.socofo.antlr.XmlGrammar;

/**
 * The xml transformer
 * 
 * @author kaeto23
 * 
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
	@Inject
	private LineHandler lh = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getResult() {
		String rc = sw.getResult();
		return rc;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void loadRules(RuleSet r) {
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
	public void parseContent(String s) {
		if (s == null || s.length() <= 0) {
			log.warning("No content given!");
			return;
		}
		ANTLRStringStream ss = new ANTLRStringStream(s);
		CharStream input = ss;
		grammar = new XmlGrammar(input);
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
		Token token;
		int currentIndent = 0;
		log.finer("Starting token run");
		WriteState state = WriteState.UNDEFINED;
		XmlObject currentObject = null;
		while ((token = grammar.nextToken()) != Token.EOF_TOKEN) {
			log.finest("Token (" + token.getType() + ") for this run: "
					+ token.getText());
			switch (token.getType()) {
			case XmlGrammar.PI_START:
				state = WriteState.MustWriteElement;
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
			case XmlGrammar.GENERIC_ID: // attribute or element name
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
				sw.addToLine(token.getText());
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
	 * Writes an xml object to the source writer
	 * 
	 * @param indent
	 *            the current indent
	 * @param xo
	 *            the object to write
	 */
	private void writeElement(int indent, XmlObject xo) {
		log.entering(XmlTransformer.class.getName(), "writeElement",
				new Object[] { indent, xo });
		sw.addToLine(indent, xo.getStartSequence());
		if (xo.getElementName() != null) {
			sw.addToLine(xo.getElementName());
		}
		NewlineRules nlRules = rules.getNewlineRules();
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
		int commentLineWidth = lh.calculateLineWidth(rules
				.getCommonAttributes().getMaxLinewidth(), 3);
		String innerContentClean = lh.cleanComment(xo.getInnerContent());
		List<String> lines = lh.breakContent(commentLineWidth,
				innerContentClean, 0, rules.getCommentsRules().getBreakType());
		for (String line : lines) {
			sw.addLine(indent, " * " + line);
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
	protected void setTestSw(SourceWriter s) {
		this.sw = s;
	}

	public void setTestLh(LineHandler i) {
		this.lh = i;
	}

}
