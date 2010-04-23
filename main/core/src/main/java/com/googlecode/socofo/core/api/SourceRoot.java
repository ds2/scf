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
 * The definition of the origin of a source code.
 * 
 * @author kaeto23
 * 
 */
public interface SourceRoot {
	/**
	 * Returns the content of the file. This usually invokes loading if a
	 * resource is not yet available.
	 * 
	 * @return the content of the file, or null if an error occurred
	 */
	public String getContent();

	/**
	 * Returns the possible type of the source code.
	 * 
	 * @return the possible type. May return null in case of no detection.
	 */
	public SourceTypes getType();
}
