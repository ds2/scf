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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.googlecode.socofo.core.api.LineHandler;
import com.googlecode.socofo.core.api.SourceWriter;
import com.googlecode.socofo.rules.api.FinalBracketPolicy;
import com.googlecode.socofo.rules.api.NewlineRules;
import com.googlecode.socofo.rules.api.XmlFormatRules;
import com.googlecode.socofo.rules.impl.NewlineRulesXml;

/**
 * The base class for XML elements.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class BaseXmlObject implements XmlObject {
	/**
	 * no idea.
	 */
	private static final long serialVersionUID = 1101453558589747963L;
	/**
	 * A logger.
	 */
	private static final Logger log = Logger.getLogger(BaseXmlObject.class
			.getName());
	/**
	 * The element name
	 */
	private String elName = null;
	/**
	 * The attributes of this element
	 */
	private Map<String, String> attributes = null;
	/**
	 * Temporary attribute name
	 */
	private String currentAttributeName = null;
	/**
	 * The start sequence of the element
	 */
	protected String startSeq = "<";
	/**
	 * The end sequence of the element
	 */
	protected String endSeq = ">";
	/**
	 * the inner content. This can be CDATA or DATA
	 */
	private String innerContent;
	/**
	 * Flag to indicate that this tag is an end tag
	 */
	protected boolean endTag = false;
	private int level;

	/**
	 * Inits the base object.
	 */
	public BaseXmlObject() {
		attributes = new LinkedHashMap<String, String>();
	}

	/**
	 * Constructs a xml object with the given element name.
	 * 
	 * @param elName
	 *            the name of the element
	 */
	public BaseXmlObject(String elName) {
		this();
		setElementName(elName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setElementName(String s) {
		elName = s;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getElementName() {
		return elName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasAttributes() {
		return !attributes.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAttributName(String s) {
		currentAttributeName = s;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAttributValue(String s) {
		attributes.put(currentAttributeName, s);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getEndSequence() {
		return endSeq;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getStartSequence() {
		return startSeq;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEndSequence(String s) {
		endSeq = s;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getInnerContent() {
		return innerContent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setInnerContent(String s) {
		innerContent = s;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEndTag() {
		return endTag;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append(getClass().getName());
		sb.append("(");
		sb.append("elName=").append(elName);
		sb.append(",startSeq=").append(startSeq);
		sb.append(",endSeq=").append(endSeq);
		sb.append(",endTag=").append(endTag);
		sb.append(",lvl=").append(level);
		sb.append(")");
		return sb.toString();
	}

	/**
	 * Writes an xml object to the source writer.
	 * 
	 * @param indent
	 *            the current indent
	 */
	public void writeElement(final XmlObject lastObject, final int indent,
			SourceWriter sw, XmlFormatRules rules, LineHandler lh) {
		log.entering(BaseXmlObject.class.getName(), "writeElement",
				new Object[] { indent, this });
		NewlineRules nlRules = rules.getNewlineRules();
		if (nlRules == null) {
			log.warning("No NL rules found, using defaults");
			nlRules = new NewlineRulesXml();
		}
		int lastObjectLevel = 1;
		if (lastObject != null) {
			lastObjectLevel = lastObject.getLevel();
		}
		if (getLevel() != lastObjectLevel) {
			sw.commitLine(false);
			sw.addToLine(getLevel() - 1, "");
		}
		sw.addToLine(0, getStartSequence());
		if (getElementName() != null) {
			sw.addToLine(getElementName());
		}
		if (hasAttributes()) {
			for (Entry<String, String> keyValuePair : getAttributes()
					.entrySet()) {
				if (nlRules != null && nlRules.getAfterEachXmlAttribute()) {
					sw.commitLine(false);
				} else {
					sw.addToLine(" ");
				}
				int indentLevel = getLevel();
				if (indentLevel <= 0) {
					indentLevel = 1;
				}
				sw.addToLine(indentLevel, keyValuePair.getKey() + "="
						+ keyValuePair.getValue());
			}
		}
		// write inner content
		int additionalIndent = 0;
		final String commentPrefix = rules.getCommentsRules()
				.getCommentIndentSpacer();
		if (this instanceof Comment) {
			additionalIndent = commentPrefix.length();
		}
		final int currentLineLength = sw.getCurrentLineLength();
		final int commentLineWidth = lh.calculateContentLineWidth(rules
				.getCommonAttributes().getMaxLinewidth(), additionalIndent);
		final String innerContentClean = lh.cleanComment(getInnerContent());
		int firstIndent = rules.getCommonAttributes().getMaxLinewidth()
				- currentLineLength;
		final List<String> lines = lh.breakContent(commentLineWidth,
				innerContentClean, firstIndent, rules.getCommentsRules()
						.getBreakType());
		boolean isFirstLine = true;
		log.finer("Entering innerLine loop");
		for (String line : lines) {
			final StringBuffer lineToPrint = new StringBuffer();
			if (this instanceof Comment) {
				lineToPrint.append(commentPrefix);
			}
			lineToPrint.append(line);
			log.finest("current line to print: " + lineToPrint);
			// decide to use NEWLINE
			if (!isFirstLine) {
				sw.commitLine(false);
			}
			sw.addToLine(lineToPrint.toString());
			isFirstLine = false;
		}
		log.finer("Finished innerLine loop, checking FinalBracket");

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
			if (hasAttributes()) {
				sw.commitLine(false);
				sw.addToLine(getLevel() - 1, "");
			}
			break;
		default:
			break;
		}
		sw.addToLine(getEndSequence());

		log.finer("Checking if line can be committed now");
		if (nlRules.getOnLevelChange()) {
			sw.commitLine(false);
		}
		if (nlRules.getAfterXmlEndTag() && isEndTag()) {
			sw.commitLine(false);
		}
		log.exiting(BaseXmlObject.class.getName(), "writeElement");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getLevel() {
		return level;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLevel(int l) {
		level = l;
	}

}
