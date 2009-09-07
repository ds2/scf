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

/**
 * The destination of a source code transformation.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface SourceDestination {
	/**
	 * Writes the content of the transformation to the stream.
	 * 
	 * @param s
	 *            the content to write
	 * @param encoding
	 *            the encoding to use. If null, the platform encoding will be
	 *            used.
	 */
	public void writeContent(String s, String encoding);
}
