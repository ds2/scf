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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.socofo.core.api.LineHandler;
import com.googlecode.socofo.core.api.SourceWriter;
import com.googlecode.socofo.core.exceptions.TranslationException;
import com.googlecode.socofo.rules.api.v1.FinalBracketPolicy;
import com.googlecode.socofo.rules.api.v1.NewlineRules;
import com.googlecode.socofo.rules.api.v1.XmlFormatRules;
import com.googlecode.socofo.rules.impl.v1.NewlineRulesXml;

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
    private static final Logger LOG = LoggerFactory
        .getLogger(BaseXmlObject.class);
    /**
     * The element name.
     */
    private String elName = null;
    /**
     * The attributes of this element.
     */
    private Map<String, String> attributes = null;
    /**
     * Temporary attribute name.
     */
    private String currentAttributeName = null;
    /**
     * The start sequence of the element.
     */
    protected String startSeq = "<";
    /**
     * The end sequence of the element.
     */
    protected String endSeq = ">";
    /**
     * the inner content. This can be CDATA or DATA
     */
    private String innerContent;
    /**
     * Flag to indicate that this tag is an end tag.
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
    public BaseXmlObject(final String elName) {
        this();
        setElementName(elName);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setElementName(final String s) {
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
    public void setAttributName(final String s) {
        currentAttributeName = s;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setAttributValue(final String s) {
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
    public void setEndSequence(final String s) {
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
    public void setInnerContent(final String s) {
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
     *            the source writer
     * @param rules
     *            the xml format rules
     * @param lh
     *            the line handler
     * @throws TranslationException
     *             if the line became too long
     */
    @Override
    public void writeElement(final XmlObject lastObject, final int indent,
        final SourceWriter sw, final XmlFormatRules rules, final LineHandler lh)
        throws TranslationException {
        LOG.debug("entering with params: {} ", new Object[] { indent, this });
        NewlineRules nlRules = rules.getNewlineRules();
        if (nlRules == null) {
            LOG.warn("No NL rules found, using defaults");
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
        
        LOG.debug("exiting");
    }
    
    /**
     * Prints the begin of the xml tag.
     * 
     * @param lastObjectLevel
     *            the level of the last xml object that has been printed
     * @param lastObject
     *            the last xml object. May be null
     * @param sw
     *            the source writer
     * @param rules
     *            the xml format rules
     * @throws TranslationException
     *             if an error occurred.
     */
    private void printObjectBegin(final int lastObjectLevel,
        final XmlObject lastObject, final SourceWriter sw,
        final XmlFormatRules rules) throws TranslationException {
        LOG.debug("entering");
        boolean commitLine = false;
        commitLine |= getLevel() != lastObjectLevel;
        commitLine |=
            (this instanceof Comment)
                && (rules.getCommentsRules().getBreakAfterBegin());
        commitLine |=
            (this instanceof EndElement) && (sw.getCurrentLineLength() <= 0);
        if (commitLine) {
            LOG.debug("Committing currentLine");
            sw.commitLine(false);
        }
        LOG.debug("Assure possible indent?");
        if (commitLine) {
            sw.addToLine(getLevel() - 1, "");
        }
        boolean isElementText = false;
        isElementText =
            (lastObject instanceof StartElement) && this instanceof Text;
        LOG.debug("isElementText is {}", isElementText);
        sw.addToLine(0, getStartSequence());
        if (getElementName() != null) {
            sw.addToLine(getElementName());
        }
        if (this instanceof Comment
            && rules.getCommentsRules().getBreakAfterBegin()) {
            sw.commitLine(false);
        }
        LOG.debug("exiting");
    }
    
    /**
     * Prints the end sequence of the xml tag.
     * 
     * @param lastObject
     *            the last xml object that was printed to the source writer
     * @param rules
     *            the xml format rules
     * @param sw
     *            the source writer
     * @param nlRules
     *            the newline rules
     * @throws TranslationException
     *             if an error occurred
     */
    private void printObjectEnd(final XmlObject lastObject,
        final XmlFormatRules rules, final SourceWriter sw,
        final NewlineRules nlRules) throws TranslationException {
        LOG.debug("entering");
        // align final bracket
        FinalBracketPolicy fbp = rules.getAlignFinalBracketOnNewline();
        if (fbp == null) {
            fbp = FinalBracketPolicy.Never;
        }
        if (rules.getCommentsRules().getBreakBeforeEnd()
            && (this instanceof Comment)) {
            fbp = FinalBracketPolicy.Always;
        }
        LOG.debug("FB Policy is {}", fbp);
        switch (fbp) {
            case Always:
                if (!(lastObject instanceof Text)) {
                    LOG.debug("Committing line (1)");
                    sw.commitLine(false);
                }
                break;
            case Never:
                break;
            case OnAttributes:
                if (hasAttributes() && !(lastObject instanceof Text)) {
                    LOG.debug("Committing line (2)");
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
        
        LOG.debug("Checking if line can be committed now");
        if (isEndTag()) {
            boolean commitLine = false;
            commitLine |= nlRules.getOnLevelChange();
            commitLine |= nlRules.getAfterXmlEndTag();
            if (commitLine) {
                LOG.debug("Committing line (3)");
                sw.commitLine(false);
            }
        } else if (this instanceof Comment
            && rules.getCommentsRules().getBreakBeforeEnd()) {
            sw.commitLine(false);
        }
        LOG.debug("Checking currentLine buffer if the content makes sense for the next run");
        if (sw.getCurrentLine().trim().length() <= 0 && this instanceof Text
            && this.hasEmptyInnerContent()) {
            sw.clearCurrentLine();
        }
        LOG.debug("exiting");
    }
    
    /**
     * Prints the attributes of this xml object using the source writer.
     * 
     * @param rules
     *            the xml format rules
     * @param nlRules
     *            the newline rules
     * @param sw
     *            the source writer to write the result to
     * @throws TranslationException
     *             if an error occurred
     */
    private void printAttributes(final XmlFormatRules rules,
        final NewlineRules nlRules, final SourceWriter sw)
        throws TranslationException {
        LOG.debug("entering");
        Map<String, String> attrs = getAttributes();
        // order attributes?
        if (rules.getSortAttributes()) {
            attrs = getOrderedMap(attrs);
        }
        for (Entry<String, String> keyValuePair : attrs.entrySet()) {
            if (nlRules != null && nlRules.getAfterEachXmlAttribute()) {
                sw.commitLine(false);
            } else {
                sw.addToLine(" ");
            }
            int indentLevel = getLevel();
            if (indentLevel <= 0) {
                indentLevel = 1;
            }
            final String attributeLine =
                keyValuePair.getKey() + "=" + keyValuePair.getValue();
            final boolean success = sw.addToLine(indentLevel, attributeLine);
            if (!success) {
                // use new line
                sw.commitLine(false);
                sw.addToLine(indentLevel, attributeLine);
            }
        }
        LOG.debug("exiting");
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
        LOG.debug("entering");
        if (innerContent == null || innerContent.length() <= 0) {
            LOG.debug("exiting: no innerContent");
            return;
        }
        // write inner content
        // ADDITIONALINDENT=indent made by a comment prefix
        int additionalIndent = 0;
        final String commentPrefix =
            rules.getCommentsRules().getCommentIndentSpacer();
        if (this instanceof Comment) {
            additionalIndent = commentPrefix.length();
        }
        final int currentLineLength = sw.getCurrentLineLength();
        // COMMENTLINEWIDTH = the width of the comment, affected by
        // ADDITIONALINDENT
        int commentLineWidth =
            rules.getCommonAttributes().getMaxLinewidth() - additionalIndent;
        // FIRSTINDENT = the indent of the first line of the comment
        final int firstIndent = 0;
        if (rules.getCommentsRules().isIndentComment()) {
            commentLineWidth -=
                getLevel()
                    * sw.getLineLength(rules.getCommonAttributes()
                        .getIndentSequence());
        }
        LOG.debug("comment line length is {}", commentLineWidth);
        final String innerContentClean = lh.cleanComment(getInnerContent());
        
        final List<String> lines =
            lh.breakContent(commentLineWidth, innerContentClean, firstIndent,
                rules.getCommentsRules().getBreakType());
        boolean isOnStartLine = true;
        String commentIndent = "";
        if (rules.getCommentsRules().isIndentComment()) {
            commentIndent =
                lh.getSequence(getLevel(), rules.getCommonAttributes()
                    .getIndentSequence());
        }
        if (currentLineLength <= 0) {
            isOnStartLine = false;
        }
        LOG.debug("Entering innerLine loop");
        for (String line : lines) {
            final StringBuffer lineToPrint = new StringBuffer();
            if (this instanceof Comment) {
                lineToPrint.append(commentPrefix);
            }
            lineToPrint.append(line);
            LOG.debug("current line to print: {}", lineToPrint);
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
        LOG.debug("Finished innerLine loop, checking FinalBracket");
        if (this instanceof Comment
            && rules.getCommentsRules().getBreakBeforeEnd()) {
            sw.commitLine(false);
        }
        LOG.debug("exiting");
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
        LOG.debug("entering with param: {}", origMap);
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
        LOG.debug("exiting {}", rc);
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
    
    @Override
    public boolean hasEmptyInnerContent() {
        if (innerContent == null) {
            return true;
        }
        return innerContent.trim().length() <= 0;
    }
    
}
