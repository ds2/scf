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
package com.googlecode.socofo.core.api;

import java.util.List;

import com.googlecode.socofo.rules.api.v1.BreakFormat;

/**
 * A formatter for breaking too long lines of code into several lines.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface LineHandler {
    /**
     * Sets the size for tab chars.
     * 
     * @param v
     *            the size for tab chars
     */
    void setTabSize(int v);
    
    /**
     * Breaks a given content into several lines.
     * 
     * @param lineWidth
     *            the allowed line width
     * @param content
     *            the content to transform
     * @param firstIndent
     *            if needed: the number of chars to indent on the first line
     * @param breakType
     *            the break behaviour when a single long word is found
     * @return a list of lines to print
     */
    List<String> breakContent(int lineWidth, String content, int firstIndent, BreakFormat breakType);
    
    /**
     * Calculates the line width for {@link #breakContent(int, String, int, BreakFormat)}.
     * 
     * @param maxLineWidth
     *            the maximum line width
     * 
     * @param additionalChars
     *            the count of chars that act as an indent. Set to 0 if unsure.
     * @return the real line width to use
     */
    int calculateContentLineWidth(int maxLineWidth, int additionalChars);
    
    /**
     * Cleans a given comment. This method checks for any line content that starts with WS* and
     * removes them. This method ONLY removes the trailing WS sequence!! Nothing more.
     * 
     * @param innerContent
     *            the content to clean
     * @return a cleaned comment
     */
    String cleanComment(String innerContent);
    
    /**
     * Returns the given string without any NEWLINE characters. Such characters are replaced by a
     * single WS (whitespace).
     * 
     * @param s
     *            the string to analyse
     * @return the cleaned version
     */
    String removeEnters(String s);
    
    /**
     * Calculates the line width of the given string.
     * 
     * @param tabSize
     *            the tab size (usually 4)
     * @param s
     *            the string to analyze
     * @return the width of the given string.
     */
    int getLineWidth(int tabSize, String s);
    
    /**
     * Returns the given string several times as a single string.
     * 
     * @param level
     *            the repeat count
     * @param indentSequence
     *            the sequence to repeat
     * @return a single string containing the given sequence several times.
     */
    String getSequence(int level, String indentSequence);
}
