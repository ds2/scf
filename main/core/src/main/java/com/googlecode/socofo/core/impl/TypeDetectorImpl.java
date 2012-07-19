/*
 * SoCoFo - Another source code formatter
 * Copyright (C) 2012  Dirk Strauss
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

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.socofo.core.api.SourceTypeDetector;
import com.googlecode.socofo.core.api.SourceTypes;

/**
 * The implementation of a source type detector.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
@Singleton
public class TypeDetectorImpl implements SourceTypeDetector {
    /**
     * A logger.
     */
    private static final transient Logger LOG = LoggerFactory
        .getLogger(TypeDetectorImpl.class);
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SourceTypes guessTypeByFilename(String fn) {
        if (fn == null) {
            LOG.warn("No filename given!");
            return null;
        }
        String filename = fn.trim();
        if (filename.length() <= 0) {
            LOG.warn("filename to short to guess type from!");
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
        LOG.info("Unknown type: " + fn);
        return null;
    }
}
