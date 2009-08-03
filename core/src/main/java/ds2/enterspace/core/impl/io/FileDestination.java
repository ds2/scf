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
package ds2.enterspace.core.impl.io;

import java.io.File;

import ds2.enterspace.core.api.SourceDestination;

/**
 * @author kaeto23
 * 
 */
public class FileDestination implements SourceDestination {
	private File dest = null;

	/**
	 * 
	 */
	public FileDestination() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ds2.enterspace.core.api.SourceDestination#writeContent(java.lang.String)
	 */
	@Override
	public void writeContent(String s) {
		// TODO Auto-generated method stub

	}

}
