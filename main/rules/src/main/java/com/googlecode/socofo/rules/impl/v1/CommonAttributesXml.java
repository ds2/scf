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

import com.googlecode.socofo.rules.api.v1.CommonAttributesUpdater;

import javax.xml.bind.annotation.*;

/**
 * The basic implementation of the xml common attributes rules.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
@XmlType(name = "CommonAttributesType")
@XmlAccessorType(XmlAccessType.FIELD)
public class CommonAttributesXml implements CommonAttributesUpdater {
    /**
     * the svuid.
     */
    private static final long serialVersionUID = 8599603676435230001L;
    /**
     * the maximum line width.
     */
    @XmlElement(required = true)
    private int maxLineWidth = 0;
    /**
     * the indent sequence.
     */
    @XmlElement(required = true)
    private String indentSequence = null;
    /**
     * the tab size.
     */
    @XmlElement(defaultValue = "4")
    private int tabSize = 4;
    /**
     * the flag to stop on too long lines.
     */
    @XmlElement(defaultValue = "false")
    private Boolean stopOnLongline = false;
    @XmlElement(defaultValue = "false")
    private Boolean forcedBreakOnLongLine=Boolean.FALSE;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIndentSequence() {
        return indentSequence;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxLinewidth() {
        return maxLineWidth;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getTabSize() {
        return tabSize;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getStopOnLongline() {
        return stopOnLongline;
    }

    @Override
    public Boolean getForcedBreakOnLongLine() {
        return forcedBreakOnLongLine;
    }

    public void setForcedBreakOnLongLine(Boolean forcedBreakOnLongLine) {
        this.forcedBreakOnLongLine = forcedBreakOnLongLine;
    }

    /*
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CommonAttributesXml [maxLineWidth=");
        builder.append(maxLineWidth);
        builder.append(", ");
        if (indentSequence != null) {
            builder.append("indentSequence=\"");
            builder.append(indentSequence);
            builder.append("\", ");
        }
        builder.append("tabSize=");
        builder.append(tabSize);
        builder.append(", ");
        if (stopOnLongline != null) {
            builder.append("stopOnLongline=");
            builder.append(stopOnLongline);
        }
        if (forcedBreakOnLongLine != null) {
            builder.append(", forcedBreakOnLongLine=");
            builder.append(forcedBreakOnLongLine);
        }
        builder.append("]");
        return builder.toString();
    }
    
}
