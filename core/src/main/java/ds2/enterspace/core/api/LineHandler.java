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
package ds2.enterspace.core.api;

import java.util.List;

import ds2.enterspace.rules.api.BreakFormat;

/**
 * A formatter for breaking too long lines of code into several lines
 * 
 * @author kaeto23
 * 
 */
public interface LineHandler {

	/**
	 * Breaks a given content into several lines
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
	List<String> breakContent(int lineWidth, String content, int firstIndent,
			BreakFormat breakType);

	/**
	 * Calculates the line width for
	 * {@link #breakContent(int, String, int, BreakFormat)}
	 * 
	 * @param maxLineWidth
	 *            the maximum line width
	 * 
	 * @param additionalChars
	 *            the count of chars that act as an indent. Set to 0 if unsure.
	 * @return the real line width to use
	 */
	int calculateLineWidth(int maxLineWidth, int additionalChars);

	/**
	 * Cleans a given comment. This method checks for any line content that
	 * starts with WS* and removes them. This method ONLY removes the trailing
	 * WS sequence!! Nothing more.
	 * 
	 * @param innerContent
	 *            the content to clean
	 * @return a cleaned comment
	 */
	String cleanComment(String innerContent);

	/**
	 * Returns the given string without any NEWLINE characters. Such characters
	 * are replaced by a single WS (whitespace).
	 * 
	 * @param s
	 *            the string to analyse
	 * @return the cleaned version
	 */
	String removeEnters(String s);
}
