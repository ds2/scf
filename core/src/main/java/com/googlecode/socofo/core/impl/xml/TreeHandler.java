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
package com.googlecode.socofo.core.impl.xml;

/**
 * Contract for setting the tree level for tree based sources.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface TreeHandler {
	/**
	 * Sets the tree level of the given xml object. The level is calculated
	 * using a history of given xml objects.
	 * 
	 * @param xo
	 *            the xml object to analyze
	 */
	void setLevel(XmlObject xo);

	/**
	 * Resets the xml object stack.
	 */
	void reset();
}
