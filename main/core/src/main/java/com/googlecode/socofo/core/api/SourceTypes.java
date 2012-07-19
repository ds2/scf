/*
 * SoCoFo - Another source code formatter
 * Copyright (C) 2012  Dirk Strauss
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
package com.googlecode.socofo.core.api;

/**
 * A source type.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public enum SourceTypes {
    
    /**
     * the XML source code type.
     */
    XML,
    
    /**
     * The Java source code type.
     */
    Java,
    
    /**
     * The lua source code type.
     */
    Lua,
    
    /**
     * The HTML source code type.
     */
    HTML,
    
    /**
     * The XHTML source code type.
     */
    XHTML,
    
    /**
     * The JSP source code type.
     */
    JSP,
    
    /**
     * The JSPX source code type.
     */
    JSPX,
    
    /**
     * The c# source code type.
     */
    CSharp;
    
    /**
     * Returns the source type with the given name.
     * 
     * @param t
     *            the name of the source type
     * @return the source type, or null if not found.
     */
    public static SourceTypes findByName(final String t) {
        for (SourceTypes type : values()) {
            if (type.name().equalsIgnoreCase(t)) {
                return type;
            }
        }
        return null;
    }
}
