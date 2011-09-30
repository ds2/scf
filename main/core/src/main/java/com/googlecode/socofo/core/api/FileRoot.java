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

import java.io.File;

import com.googlecode.socofo.core.exceptions.LoadingException;

/**
 * Interface for reading source files.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface FileRoot extends SourceRoot {
    /**
     * Loads the given file.
     * 
     * @param f
     *            the file to load
     * @throws LoadingException
     *             if an error occurred
     */
    public void loadFile(File f) throws LoadingException;
}
