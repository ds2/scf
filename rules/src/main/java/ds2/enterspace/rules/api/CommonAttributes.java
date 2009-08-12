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
package ds2.enterspace.rules.api;

/**
 * Common attributes for any file to format.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface CommonAttributes {
	/**
	 * Returns the maximum line width.
	 * 
	 * @return the maximum line width
	 */
	int getMaxLinewidth();

	/**
	 * Returns the sequence of characters to act as indent.
	 * 
	 * @return the indent sequence
	 */
	String getIndentSequence();

	/**
	 * Returns the count of spaces that represent a tab character.
	 * 
	 * @return the count of spaces for a tab. Usually 4.
	 */
	int getTabSize();
}
