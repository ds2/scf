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

import java.io.InputStream;

import com.googlecode.socofo.core.exceptions.LoadingException;

/**
 * Interface for reading source streams.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface StreamRoot extends SourceRoot {
    /**
     * Sets the stream to read the content from.
     * 
     * @param is
     *            the input stream that contains the content
     * @param enc
     *            the possible encoding. Set null if you want to use the default
     *            encoding (set by the injector)
     * @throws LoadingException
     *             if loading the stream did not succeed
     */
    void loadStream(InputStream is, String enc) throws LoadingException;
    
    /**
     * Sets the type of the given stream.
     * 
     * @param type
     *            the type
     */
    void setType(SourceTypes type);
}
