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
/**
 * 
 */
package com.googlecode.socofo.rules.api.v1;

import java.io.Serializable;

/**
 * Common attributes for any file to format.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface CommonAttributes extends Serializable {
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

	/**
	 * Shall the transformation stop in case of a line becoming too long? This
	 * is a debug parameter and should be set to FALSE in production.
	 * 
	 * @return TRUE if an exception should be thrown in case of a line becoming
	 *         too long, otherwise FALSE
	 */
	Boolean getStopOnLongline();
}
