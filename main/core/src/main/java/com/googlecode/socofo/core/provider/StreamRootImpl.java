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
package com.googlecode.socofo.core.provider;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.inject.Inject;

import com.googlecode.socofo.common.api.IOHelper;
import com.googlecode.socofo.core.api.CoreConfig;
import com.googlecode.socofo.core.api.SourceTypes;
import com.googlecode.socofo.core.api.StreamRoot;

/**
 * The implementation for reading sources via a stream.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class StreamRootImpl implements StreamRoot {
    /**
     * The content.
     */
    private String content = null;
    /**
     * The iohelper.
     */
    @Inject
    private IOHelper iohelper = null;
    /**
     * The declared type of the stream source.
     */
    private SourceTypes type = null;
    /**
     * The core config.
     */
    @Inject
    private CoreConfig config;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void loadStream(final InputStream is, final String enc) {
        if (is == null) {
            return;
        }
        String encoding = config.getDefaultEncoding().name();
        if (enc != null) {
            encoding = enc;
        }
        final BufferedInputStream bis = new BufferedInputStream(is);
        final byte[] buffer = new byte[6000];
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int numRead;
        while (true) {
            numRead = iohelper.read(bis, buffer);
            if (numRead < 0) {
                break;
            }
            baos.write(buffer, 0, numRead);
        }
        iohelper.closeInputstream(bis);
        iohelper.closeInputstream(is);
        iohelper.closeOutputstream(baos);
        content = iohelper.createString(baos.toByteArray(), encoding);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getContent() {
        return content;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SourceTypes getType() {
        return type;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setType(final SourceTypes t) {
        this.type = t;
    }
    
}
