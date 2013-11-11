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
/**
 * 
 */
package com.googlecode.socofo.core.exceptions;

/**
 * If the line buffer became too large.
 * 
 * @author dstrauss
 * @version 0.1
 */
public class LineBufferTooLargeException extends TranslationException {
    
    /**
     * The svuid.
     */
    private static final long serialVersionUID = -8292074160133767035L;
    
    public LineBufferTooLargeException(final String message) {
        super(message);
    }
    
    public LineBufferTooLargeException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
}
