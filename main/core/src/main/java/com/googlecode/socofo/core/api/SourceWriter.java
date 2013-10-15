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

import com.googlecode.socofo.core.exceptions.LineBufferTooLargeException;
import com.googlecode.socofo.core.exceptions.TranslationException;
import com.googlecode.socofo.rules.api.v1.CommonAttributes;

/**
 * Source writer interface for writing source file content. Implementations of this class usually
 * write to a stream which becomes the new source file content.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface SourceWriter {
    /**
     * The maximum length of a line (to prevent buffer overflows).
     */
    int MAX_LINE_LENGTH = 3000;
    
    /**
     * Adds a new line to the writer, first adding some indents, and then adding the given sequence
     * of data. After that, the line is commited (a newline sign is added).
     * 
     * @param indents
     *            the count of indents to write first
     * @param s
     *            the source code to add to this line, right after the indents
     * @return TRUE if successful, FALSE in case of the line becoming too long
     * @throws TranslationException
     *             if the line was too long to commit
     */
    boolean addLine(int indents, String s) throws TranslationException;
    
    /**
     * Adds a sequence of data to the current line.
     * 
     * @param s
     *            a sequence of data to add to the current line
     * @return TRUE if successful, otherwise FALSE in case of the line becoming too long
     */
    boolean addToLine(String s);
    
    /**
     * Returns the current result of the source writer.
     * 
     * @return an empty string, or the current result of the transformation
     */
    String getResult();
    
    /**
     * Prepares the writer. Usually this will clear the cache.
     */
    void prepare();
    
    /**
     * Finalizes the cache. Usually, this will end the source writer, clears the cache and prepares
     * the result.
     * 
     * @throws TranslationException
     *             if the last line could not be committed successfully.
     */
    void finish() throws TranslationException;
    
    /**
     * Sets the common attributes (line width, indent sequence etc.) to use.
     * 
     * @param c
     *            the common attributes
     */
    void setCommonAttributes(CommonAttributes c);
    
    /**
     * Adds to the current line the number of indents, plus the given text. No newline is added
     * after that.
     * 
     * @param currentIndent
     *            the count of indents to add to the current line
     * @param s
     *            the string to add
     * @return TRUE if successful, otherwise FALSE in case of line becoming too long, or something
     *         else.
     * @throws LineBufferTooLargeException
     *             if the line is too long
     */
    boolean addToLine(int currentIndent, String s) throws LineBufferTooLargeException;
    
    /**
     * Commits the current buffered line to the final source code. And clears the line buffer right
     * after.
     * 
     * @param ignoreLinelength
     *            flag to indicate that the line length check can be ignored. Set TRUE to ignore
     *            line length, default is FALSE
     * @return TRUE if line was commited successful, otherwise FALSE
     * @throws TranslationException
     *             if a line became too long
     */
    boolean commitLine(boolean ignoreLinelength) throws TranslationException;
    
    /**
     * Returns the result of the currentLine buffer.
     * 
     * @return the current line content
     */
    String getCurrentLine();
    
    /**
     * Returns the length of the current line.
     * 
     * @return the length of the current line (special counting for tab chars)
     */
    int getCurrentLineLength();
    
    /**
     * Calculates the line length from the given string sequence. This typically counts the letters
     * and tab chars. The tab chars are handled separately using the common attributes which contain
     * how much WS signs represent a single tab char.
     * 
     * @param indentSequence
     *            the string sequence to calculate the length
     * @return the length
     */
    int getLineLength(String indentSequence);
    
    /**
     * Clears the current line buffer.
     */
    void clearCurrentLine();
}
