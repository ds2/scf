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
package com.googlecode.socofo.core.impl.io;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import com.googlecode.socofo.core.api.SourceTypeDetector;
import com.googlecode.socofo.core.api.SourceTypes;

/**
 * @author kaeto23
 * 
 */
public class SourceFileFilter implements FileFilter {
	private SourceTypeDetector localDetector = null;
	private List<SourceTypes> allowedTypes = null;

	/**
	 * 
	 */
	public SourceFileFilter(SourceTypeDetector detector,
			List<SourceTypes> sourceTypes) {
		localDetector = detector;
		allowedTypes = sourceTypes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File pathname) {
		if (pathname.isDirectory()) {
			// may contain more source files
			return true;
		}
		final SourceTypes fileType = localDetector.guessTypeByFilename(pathname
				.getName());
		if (fileType == null) {
			return false;
		}
		if (allowedTypes.contains(fileType)) {
			return true;
		}
		return false;
	}

}
