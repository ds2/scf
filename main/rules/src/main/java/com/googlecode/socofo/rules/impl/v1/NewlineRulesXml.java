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
package com.googlecode.socofo.rules.impl.v1;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.googlecode.socofo.rules.api.v1.NewlineRulesUpdater;

/**
 * The NL rules.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
@XmlType(name = "NewlineRulesType")
public class NewlineRulesXml implements NewlineRulesUpdater {
    /**
     * the svuid.
     */
    private static final long serialVersionUID = 485959883495651756L;
    @XmlElement
    private Boolean beforeIfStatements = false;
    @XmlElement
    private Boolean beforeComment = false;
    @XmlElement
    private Boolean afterXmlEndTag = false;
    @XmlElement
    private Boolean afterEachXmlAttribute = false;
    @XmlElement
    private Boolean onLevelChange = false;
    @XmlElement
    private Boolean separateCommentTags = false;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getAfterEachXmlAttribute() {
        return afterEachXmlAttribute;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getAfterXmlEndTag() {
        return afterXmlEndTag;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getBeforeComment() {
        return beforeComment;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getBeforeIfStatements() {
        return beforeIfStatements;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getOnLevelChange() {
        return onLevelChange;
    }
    
}
