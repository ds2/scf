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
package com.googlecode.socofo.core.exceptions;

/**
 * A simple exception when a translation job encounters a problem.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class TranslationException extends Exception {
    
    /**
     * the svuid.
     */
    private static final long serialVersionUID = 3370510780566872127L;
    
    /**
     * Inits the exception with the given error message.
     * 
     * @param message
     *            the error message
     */
    public TranslationException(String message) {
        super(message);
    }
    
    /**
     * Inits the exception with the given error message and cause.
     * 
     * @param message
     *            the error message
     * @param cause
     *            the error cause
     */
    public TranslationException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
    
}
