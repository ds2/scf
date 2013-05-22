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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.googlecode.socofo.rules.api.v1.BreakFormat;
import com.googlecode.socofo.rules.api.v1.CommentParsing;
import com.googlecode.socofo.rules.api.v1.XmlCommentsRulesUpdater;

/**
 * The xml comments rules.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
@XmlType(name = "XmlCommentsRulesType")
public class XmlCommentsRulesXml implements XmlCommentsRulesUpdater {
    /**
     * the svuid.
     */
    private static final long serialVersionUID = 866115633795062805L;
    @XmlElement
    private Boolean breakAfterBegin = false;
    @XmlElement
    private Boolean breakBeforeEnd = false;
    @XmlElement
    private BreakFormat breakType = BreakFormat.NoBreak;
    @XmlElement
    private CommentParsing parsing = CommentParsing.NoParsing;
    @XmlElement
    private String commentIndentSpacer = "";
    private Boolean indentComment = true;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getBreakAfterBegin() {
        return breakAfterBegin;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getBreakBeforeEnd() {
        return breakBeforeEnd;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public BreakFormat getBreakType() {
        return breakType;
    }
    
    @Override
    public CommentParsing getParsing() {
        return parsing;
    }
    
    @Override
    public String getCommentIndentSpacer() {
        return commentIndentSpacer;
    }
    
    @Override
    public Boolean isIndentComment() {
        return indentComment;
    }
    
    @Override
    public void setBreakAfterBegin(boolean b) {
        breakAfterBegin = b;
    }
    
    @Override
    public void setBreakBeforeEnd(boolean b) {
        breakBeforeEnd = b;
    }
    
    @Override
    public void setIndentComment(boolean b) {
        indentComment = b;
    }
    
}
