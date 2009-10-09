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
 * A file filter for filtering several source files.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class SourceFileFilter implements FileFilter {
	/**
	 * the detector for the source files.
	 */
	private SourceTypeDetector localDetector = null;
	/**
	 * A list of allowed source file types.
	 */
	private List<SourceTypes> allowedTypes = null;

	/**
	 * Inits the filter.
	 * 
	 * @param detector
	 *            the detector for source types
	 * @param sourceTypes
	 *            the list of allowed source types
	 * 
	 */
	public SourceFileFilter(final SourceTypeDetector detector,
			final List<SourceTypes> sourceTypes) {
		localDetector = detector;
		allowedTypes = sourceTypes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accept(final File pathname) {
		boolean rc = false;
		if (pathname.isDirectory()) {
			rc = true;
		} else {
			final SourceTypes fileType = localDetector
					.guessTypeByFilename(pathname.getName());
			if (fileType != null && allowedTypes.contains(fileType)) {
				rc = true;
			}
		}
		return rc;
	}

}
