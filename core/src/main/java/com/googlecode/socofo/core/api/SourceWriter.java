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
package com.googlecode.socofo.core.api;

import com.googlecode.socofo.rules.api.CommonAttributes;

/**
 * Source writer interface for writing source file content. Implementations of
 * this class usually write to a stream which becomes the new source file
 * content.
 * 
 * @author kaeto23
 * 
 */
public interface SourceWriter {

	/**
	 * Adds a new line to the writer, first adding some indents, and then adding
	 * the given sequence of data. After that, the line is commited (a newline
	 * sign is added).
	 * 
	 * @param indents
	 *            the count of indents to write first
	 * @param s
	 *            the source code to add to this line, right after the indents
	 * @return TRUE if successful, FALSE in case of the line becoming too long
	 */
	public boolean addLine(int indents, String s);

	/**
	 * Adds a sequence of data to the current line
	 * 
	 * @param s
	 *            a sequence of data to add to the current line
	 * @return TRUE if successful, otherwise FALSE in case of the line becoming
	 *         too long
	 */
	public boolean addToLine(String s);

	/**
	 * Returns the current result of the source writer.
	 * 
	 * @return an empty string, or the current result of the transformation
	 */
	public String getResult();

	/**
	 * Prepares the writer. Usually this will clear the cache.
	 */
	public void prepare();

	/**
	 * Finalizes the cache. Usually, this will end the source writer, clears the
	 * cache and prepares the result.
	 */
	public void finish();

	/**
	 * Sets the common attributes (line width, indent sequence etc.) to use
	 * 
	 * @param c
	 *            the common attributes
	 */
	public void setCommonAttributes(CommonAttributes c);

	/**
	 * Adds to the current line the number of indents, plus the given text. No
	 * newline is added after that.
	 * 
	 * @param currentIndent
	 *            the count of indents to add to the current line
	 * @param s
	 *            the string to add
	 */
	public void addToLine(int currentIndent, String s);

	/**
	 * Commits the current buffered line to the final source code. And clears
	 * the line buffer right after.
	 * 
	 * @param ignoreLinelength
	 *            flag to indicate that the line length check can be ignored.
	 *            Set TRUE to ignore line length, default is FALSE
	 * @return TRUE if line was commited successful, otherwise FALSE
	 */
	public boolean commitLine(boolean ignoreLinelength);

	/**
	 * Returns the result of the currentLine buffer.
	 * 
	 * @return the current line content
	 */
	public String getCurrentLine();

	/**
	 * Returns the length of the current line
	 * 
	 * @return the length of the current line (special counting for tab chars)
	 */
	public int getCurrentLineLength();
}
