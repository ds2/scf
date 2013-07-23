/*
 * SoCoFo - Another source code formatter
 * Copyright (C) 2013  Dirk Strauss
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
package com.googlecode.socofo.core.api;

import java.io.File;
import java.util.List;

/**
 * Scanner for source files.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface SourcefileScanner {
    /**
     * Scans the given directory for source files.
     * 
     * @param baseDirectory
     *            the directory to start scanning for source files. This method
     *            is recursive!
     * @param types
     *            the types to look for
     * @return a list of source files
     */
    List<File> scan(File baseDirectory, List<SourceTypes> types);
}
