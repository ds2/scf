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

import java.util.HashMap;
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
	 * A logger
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

	/**
	 * Inits the base object.
	 */
	public BaseXmlObject() {
		attributes = new HashMap<String, String>();
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
		sb.append(")");
		return sb.toString();
	}

	/**
	 * Writes an xml object to the source writer.
	 * 
	 * @param indent
	 *            the current indent
	 */
	public void writeElement(final int indent, SourceWriter sw,
			XmlFormatRules rules, LineHandler lh) {
		log.entering(BaseXmlObject.class.getName(), "writeElement",
				new Object[] { indent, this });
		sw.addToLine(indent, getStartSequence());
		if (getElementName() != null) {
			sw.addToLine(getElementName());
		}
		NewlineRules nlRules = rules.getNewlineRules();
		if (nlRules == null) {
			log.warning("No NL rules found, using defaults");
			nlRules = new NewlineRulesXml();
		}
		if (hasAttributes()) {
			for (Entry<String, String> keyValuePair : getAttributes()
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
		String commentSeq = rules.getCommentsRules().getCommentIndentSpacer();
		if (this instanceof Comment) {
			additionalIndent = commentSeq.length();
		}
		final int commentLineWidth = lh.calculateContentLineWidth(rules
				.getCommonAttributes().getMaxLinewidth(), additionalIndent);
		final String innerContentClean = lh.cleanComment(getInnerContent());
		final List<String> lines = lh.breakContent(commentLineWidth,
				innerContentClean, 0, rules.getCommentsRules().getBreakType());
		for (String line : lines) {
			final StringBuffer lineToPrint = new StringBuffer();
			if (this instanceof Comment) {
				lineToPrint.append(commentSeq);
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
			if (hasAttributes()) {
				sw.commitLine(false);
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
		if (nlRules != null && nlRules.getAfterXmlEndTag() && isEndTag()) {
			sw.commitLine(false);
		}
		log.exiting(BaseXmlObject.class.getName(), "writeElement");
	}

}
