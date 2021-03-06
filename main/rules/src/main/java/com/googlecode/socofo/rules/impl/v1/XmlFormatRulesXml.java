/*
 * SoCoFo - Another source code formatter
 * Copyright (C) 2013  Dirk Strauss
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
package com.googlecode.socofo.rules.impl.v1;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import com.googlecode.socofo.rules.api.v1.CommonAttributes;
import com.googlecode.socofo.rules.api.v1.FinalBracketPolicy;
import com.googlecode.socofo.rules.api.v1.NamespaceRule;
import com.googlecode.socofo.rules.api.v1.NewlineRules;
import com.googlecode.socofo.rules.api.v1.NewlineRulesUpdater;
import com.googlecode.socofo.rules.api.v1.TextBlocks;
import com.googlecode.socofo.rules.api.v1.XmlCommentsRules;
import com.googlecode.socofo.rules.api.v1.XmlCommentsRulesUpdater;
import com.googlecode.socofo.rules.api.v1.XmlFormatRulesUpdater;

/**
 * The xml format rules.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
@XmlRootElement(name = "xml-format")
@XmlSeeAlso({ NamespaceRuleXml.class, CommonAttributesXml.class })
public class XmlFormatRulesXml implements XmlFormatRulesUpdater {
    /**
     * the svuid.
     */
    private static final long serialVersionUID = -2816143684776160066L;
    @XmlElement(required = true, type = CommonAttributesXml.class)
    private CommonAttributes commonAttributes = null;
    @XmlElement(required = false)
    private Boolean separateAttributesPerLine = false;
    @XmlElement(required = false)
    private FinalBracketPolicy alignFinalBracketOnNewline =
        FinalBracketPolicy.Never;
    @XmlElement(required = false)
    private Boolean clearBlankLines = false;
    @XmlElement(required = false)
    private Boolean insertWsAtEmptyElement = false;
    @XmlElement
    private Boolean sortAttributes = false;
    @XmlElements(value = { @XmlElement(
        type = NamespaceRuleXml.class,
        name = "enforceNamespace") })
    private List<NamespaceRule> namespaceRules = null;
    @XmlElement(type = TextBlocksXml.class)
    private TextBlocks textBlocks = null;
    @XmlElement(type = XmlCommentsRulesXml.class)
    private XmlCommentsRules commentsRules = null;
    @XmlElement(type = NewlineRulesXml.class)
    private NewlineRules newlineRules = null;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public FinalBracketPolicy getAlignFinalBracketOnNewline() {
        return alignFinalBracketOnNewline;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getClearBlanklines() {
        return clearBlankLines;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CommonAttributes getCommonAttributes() {
        return commonAttributes;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getInsertWsAtEmptyElement() {
        return insertWsAtEmptyElement;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<NamespaceRule> getNamespaceRules() {
        return namespaceRules;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public TextBlocks getTextBlocks() {
        return textBlocks;
    }
    
    @Override
    public Boolean getSortAttributes() {
        return sortAttributes;
    }
    
    @Override
    public XmlCommentsRules getCommentsRules() {
        return commentsRules;
    }
    
    @Override
    public NewlineRules getNewlineRules() {
        return newlineRules;
    }
    
    @Override
    @XmlTransient
    public NewlineRulesUpdater getNewlineRulesUpdater() {
        return (NewlineRulesUpdater) newlineRules;
    }
    
    @Override
    public XmlCommentsRulesUpdater getCommentsRulesUpdater() {
        return (XmlCommentsRulesUpdater) commentsRules;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(getClass().getName());
        sb.append("(");
        sb.append("sepAttrPerLine=").append(separateAttributesPerLine);
        sb.append(",finalBracketOnNl=").append(alignFinalBracketOnNewline);
        sb.append(",clearBlankLns=").append(clearBlankLines);
        sb.append(",insertWsEmptyEl=").append(insertWsAtEmptyElement);
        sb.append(",sortAttrs=").append(sortAttributes);
        sb.append(",ca=").append(commonAttributes);
        sb.append(")");
        return sb.toString();
    }
    
}
