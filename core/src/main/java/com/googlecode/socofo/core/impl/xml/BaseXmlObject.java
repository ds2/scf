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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.googlecode.socofo.core.api.LineHandler;
import com.googlecode.socofo.core.api.SourceWriter;
import com.googlecode.socofo.core.exceptions.TranslationException;
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
	/**
	 * the level of the element.
	 */
	private int level;
	/**
	 * Flag value to allow ordering of attributes of this xml object.
	 */
	protected boolean allowAttributeOrdering = true;

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
	 * @param lastObject
	 *            the last object, from the last write run
	 * 
	 * @param indent
	 *            the current indent
	 * @param sw
	 * @param rules
	 * @param lh
	 * @throws TranslationException
	 *             if the line became too long
	 */
	public void writeElement(final XmlObject lastObject, final int indent,
			SourceWriter sw, XmlFormatRules rules, LineHandler lh)
			throws TranslationException {
		log.entering(getClass().getName(), "writeElement", new Object[] {
				indent, this });
		NewlineRules nlRules = rules.getNewlineRules();
		if (nlRules == null) {
			log.warning("No NL rules found, using defaults");
			nlRules = new NewlineRulesXml();
		}
		int lastObjectLevel = 1;
		if (lastObject != null) {
			lastObjectLevel = lastObject.getLevel();
		}
		printObjectBegin(lastObjectLevel, lastObject, sw, rules);
		if (hasAttributes()) {
			printAttributes(rules, nlRules, sw);
		}
		printInnerText(rules, sw, lh);
		printObjectEnd(lastObject, rules, sw, nlRules);

		log.exiting(getClass().getName(), "writeElement");
	}

	private void printObjectBegin(int lastObjectLevel, XmlObject lastObject,
			SourceWriter sw, XmlFormatRules rules) throws TranslationException {
		log.entering(BaseXmlObject.class.getName(), "printObjectBegin");
		boolean commitLine = false;
		commitLine |= getLevel() != lastObjectLevel;
		commitLine |= (this instanceof Comment)
				&& (rules.getCommentsRules().getBreakAfterBegin());
		if (commitLine) {
			sw.commitLine(false);
			sw.addToLine(getLevel() - 1, "");
		}
		boolean isElementText = false;
		isElementText = (lastObject instanceof StartElement)
				&& this instanceof Text;
		log.finest("isElementText is " + isElementText);
		sw.addToLine(0, getStartSequence());
		if (getElementName() != null) {
			sw.addToLine(getElementName());
		}
		if (this instanceof Comment
				&& rules.getCommentsRules().getBreakAfterBegin()) {
			sw.commitLine(false);
		}
		log.exiting(BaseXmlObject.class.getName(), "printObjectBegin");
	}

	private void printObjectEnd(final XmlObject lastObject,
			final XmlFormatRules rules, final SourceWriter sw,
			final NewlineRules nlRules) throws TranslationException {
		log.entering(BaseXmlObject.class.getName(), "printObjectEnd");
		// align final bracket
		FinalBracketPolicy fbp = rules.getAlignFinalBracketOnNewline();
		if (fbp == null) {
			fbp = FinalBracketPolicy.Never;
		}
		if (rules.getCommentsRules().getBreakBeforeEnd()
				&& (this instanceof Comment)) {
			fbp = FinalBracketPolicy.Always;
		}
		log.finest("FB Policy is " + fbp);
		switch (fbp) {
		case Always:
			if (!(lastObject instanceof Text)) {
				log.finer("Committing line (1)");
				sw.commitLine(false);
			}
			break;
		case Never:
			break;
		case OnAttributes:
			if (hasAttributes() && !(lastObject instanceof Text)) {
				log.finer("Committing line (2)");
				sw.commitLine(false);
				sw.addToLine(getLevel() - 1, "");
			}
			break;
		default:
			break;
		}
		if (this instanceof Comment
				&& rules.getCommentsRules().getBreakBeforeEnd()) {
			sw.addToLine(getLevel() - 1, "");
		}
		sw.addToLine(getEndSequence());

		log.finer("Checking if line can be committed now");
		if (isEndTag()) {
			boolean commitLine = false;
			commitLine |= nlRules.getOnLevelChange();
			commitLine |= nlRules.getAfterXmlEndTag();
			if (commitLine) {
				log.finer("Committing line (3)");
				sw.commitLine(false);
			}
		} else if (this instanceof Comment
				&& rules.getCommentsRules().getBreakBeforeEnd()) {
			sw.commitLine(false);
		}
		log.exiting(BaseXmlObject.class.getName(), "printObjectEnd");
	}

	private void printAttributes(XmlFormatRules rules, NewlineRules nlRules,
			SourceWriter sw) throws TranslationException {
		log.entering(BaseXmlObject.class.getName(), "printAttributes");
		Map<String, String> attributes = getAttributes();
		// order attributes?
		if (rules.getSortAttributes()) {
			attributes = getOrderedMap(attributes);
		}
		for (Entry<String, String> keyValuePair : attributes.entrySet()) {
			if (nlRules != null && nlRules.getAfterEachXmlAttribute()) {
				sw.commitLine(false);
			} else {
				sw.addToLine(" ");
			}
			int indentLevel = getLevel();
			if (indentLevel <= 0) {
				indentLevel = 1;
			}
			String attributeLine = keyValuePair.getKey() + "="
					+ keyValuePair.getValue();
			boolean success = sw.addToLine(indentLevel, attributeLine);
			if (!success) {
				// use new line
				sw.commitLine(false);
				sw.addToLine(indentLevel, attributeLine);
			}
		}
		log.exiting(BaseXmlObject.class.getName(), "printAttributes");
	}

	/**
	 * Prints the inner textual content of a tag.
	 * 
	 * @param rules
	 *            the xml rules
	 * @param sw
	 *            the source writer
	 * @param lh
	 *            the line handler
	 * @throws TranslationException
	 *             if a source writer exception occurrs
	 */
	private void printInnerText(final XmlFormatRules rules,
			final SourceWriter sw, final LineHandler lh)
			throws TranslationException {
		log.entering(BaseXmlObject.class.getName(), "printInnerText");
		if (innerContent == null || innerContent.length() <= 0) {
			log.exiting(getClass().getName(), "printInnerContent");
			return;
		}
		// write inner content
		// ADDITIONALINDENT=indent made by a comment prefix
		int additionalIndent = 0;
		final String commentPrefix = rules.getCommentsRules()
				.getCommentIndentSpacer();
		if (this instanceof Comment) {
			additionalIndent = commentPrefix.length();
		}
		final int currentLineLength = sw.getCurrentLineLength();
		// COMMENTLINEWIDTH = the width of the comment, affected by
		// ADDITIONALINDENT
		int commentLineWidth = rules.getCommonAttributes().getMaxLinewidth()
				- additionalIndent;
		// FIRSTINDENT = the indent of the first line of the comment
		final int firstIndent = 0;
		if (rules.getCommentsRules().isIndentComment()) {
			commentLineWidth -= getLevel()
					* sw.getLineLength(rules.getCommonAttributes()
							.getIndentSequence());
		}
		log.finest("comment line length is " + commentLineWidth);
		final String innerContentClean = lh.cleanComment(getInnerContent());

		final List<String> lines = lh.breakContent(commentLineWidth,
				innerContentClean, firstIndent, rules.getCommentsRules()
						.getBreakType());
		boolean isOnStartLine = true;
		String commentIndent = "";
		if (rules.getCommentsRules().isIndentComment()) {
			commentIndent = lh.getSequence(getLevel(), rules
					.getCommonAttributes().getIndentSequence());
		}
		if (currentLineLength <= 0) {
			isOnStartLine = false;
		}
		log.finer("Entering innerLine loop");
		for (String line : lines) {
			final StringBuffer lineToPrint = new StringBuffer();
			if (this instanceof Comment) {
				lineToPrint.append(commentPrefix);
			}
			lineToPrint.append(line);
			log.finest("current line to print: " + lineToPrint);
			// decide to use NEWLINE
			if (!isOnStartLine) {
				sw.commitLine(false);
			}
			if (!isOnStartLine) {
				lineToPrint.insert(0, commentIndent);
			}
			sw.addToLine(lineToPrint.toString());
			isOnStartLine = false;
		}
		log.finer("Finished innerLine loop, checking FinalBracket");
		if (this instanceof Comment
				&& rules.getCommentsRules().getBreakBeforeEnd()) {
			sw.commitLine(false);
		}
		log.exiting(BaseXmlObject.class.getName(), "printInnerText");
	}

	/**
	 * Returns an ordered map of the given map. Ordering is done by the keys.
	 * 
	 * @param origMap
	 *            the original map to copy
	 * @return the ordered map
	 */
	protected Map<String, String> getOrderedMap(
			final Map<String, String> origMap) {
		log.entering(BaseXmlObject.class.getName(), "getOrderedMap", origMap);
		final Map<String, String> rc = new LinkedHashMap<String, String>();
		final String[] keys = origMap.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		final List<String> keysList2 = Arrays.asList(keys);
		final List<String> keysList = new ArrayList<String>();
		keysList.addAll(keysList2);
		if (this instanceof ProcessingInstruction) {
			if (keysList.contains("version")) {
				keysList.remove("version");
				keysList.add(0, "version");
			}
		}
		for (String key : keysList) {
			final String val = origMap.get(key);
			rc.put(key, val);
		}
		log.exiting(BaseXmlObject.class.getName(), "getOrderedMap", rc);
		return rc;
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
	public void setLevel(final int l) {
		level = l;
	}

}
