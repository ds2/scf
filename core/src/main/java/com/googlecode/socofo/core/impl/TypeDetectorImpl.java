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
package com.googlecode.socofo.core.impl;

import java.util.logging.Logger;

import com.google.inject.Singleton;
import com.googlecode.socofo.core.api.SourceTypeDetector;
import com.googlecode.socofo.core.api.SourceTypes;

/**
 * The implementation of a source type detector.
 * 
 * @author kaeto23
 * 
 */
@Singleton
public class TypeDetectorImpl implements SourceTypeDetector {
	/**
	 * A logger
	 */
	private static final transient Logger log = Logger
			.getLogger(TypeDetectorImpl.class.getName());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SourceTypes guessTypeByFilename(String fn) {
		if (fn == null) {
			log.warning("No filename given!");
			return null;
		}
		String filename = fn.trim();
		if (filename.length() <= 0) {
			log.warning("filename to short to guess type from!");
			return null;
		}
		filename = filename.toLowerCase();
		if (filename.endsWith(".xml")) {
			return SourceTypes.XML;
		}
		if (filename.endsWith(".cs")) {
			return SourceTypes.CSharp;
		}
		if (filename.endsWith(".java")) {
			return SourceTypes.Java;
		}
		if (filename.endsWith(".xhtml")) {
			return SourceTypes.XHTML;
		}
		if (filename.endsWith(".lua")) {
			return SourceTypes.Lua;
		}
		log.info("Unknown type: " + fn);
		return null;
	}
}
