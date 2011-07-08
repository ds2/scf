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
package com.googlecode.socofo.common.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.socofo.common.api.IOHelper;

/**
 * A first implementation of the IOHelper.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
@Singleton
public class IOHelperImpl implements IOHelper {
    /**
     * A logger.
     * 
     */
    private static final transient Logger LOG = LoggerFactory
        .getLogger(IOHelperImpl.class);
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void closeInputstream(final InputStream is) {
        if (is == null) {
            LOG.warn("No inputstream given!");
            return;
        }
        try {
            is.close();
        } catch (final IOException e) {
            LOG.debug("Error on closing stream", e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void closeReader(final Reader r) {
        if (r != null) {
            try {
                r.close();
            } catch (final IOException e) {
                LOG.debug("closeReader error", e);
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void closeOutputstream(final OutputStream baos) {
        if (baos == null) {
            LOG.warn("No output stream given!");
            return;
        }
        try {
            baos.close();
        } catch (final IOException e) {
            LOG.debug("outputstream error", e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String createString(final byte[] byteArray, final String encoding) {
        String rc = null;
        if (byteArray == null || byteArray.length <= 0) {
            LOG.warn("No content given to translate!");
            return rc;
        }
        try {
            rc = new String(byteArray, encoding);
        } catch (final UnsupportedEncodingException e) {
            LOG.debug("Encoding not supported", e);
        }
        return rc;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int read(final InputStream bis, final byte[] buffer) {
        int rc;
        try {
            rc = bis.read(buffer);
        } catch (final IOException e) {
            LOG.debug("read error", e);
            rc = -1;
        }
        return rc;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void closeWriter(final Writer w) {
        if (w == null) {
            LOG.warn("No writer given!");
            return;
        }
        try {
            w.close();
        } catch (final IOException e) {
            LOG.debug("closeWriter error", e);
        }
    }
    
}
