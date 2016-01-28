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

import com.googlecode.socofo.common.api.IOHelper;
import com.googlecode.socofo.core.api.*;
import com.googlecode.socofo.core.exceptions.LoadingException;
import org.slf4j.*;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Path;

/**
 * Implementation for a file source root.
 * 
 * @author Dirk Strauss
 * @version 1.0
 * 
 */
public class FileRootImpl implements FileRoot {
    /**
     * A logger.
     */
    private static final transient Logger LOG = LoggerFactory
        .getLogger(FileRootImpl.class.getName());
    /**
     * The content of the file.
     */
    private String content;
    /**
     * The source type detector.
     */
    @Inject
    private SourceTypeDetector detector;
    /**
     * The type of the source code.
     */
    private SourceTypes type;
    /**
     * The io helper.
     */
    @Inject
    private IOHelper iohelper;
    private Path file;
    
    /**
     * Loads the given file.
     * 
     * @param f
     *            the file to load
     * @throws LoadingException
     *             if an error occurred
     */
    @Override
    public final void loadFile(final File f, String enc) throws LoadingException {
        if (f == null) {
            LOG.warn("No file given!");
            return;
        }
        FileReader fr = null;
        BufferedReader br = null;
        try {
            file=f.toPath();
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            final StringBuffer sb = new StringBuffer();
            while (true) {
                final String l = br.readLine();
                if (l == null) {
                    break;
                }
                sb.append(l).append("\n");
            }
            content = sb.toString();
            type = detector.guessTypeByFilename(f.getAbsolutePath());
        } catch (final FileNotFoundException e) {
            LOG.debug("loadFile", e);
            throw new LoadingException("File not found: " + f.getAbsolutePath());
        } catch (final IOException e) {
            LOG.debug("loadFile", e);
            throw new LoadingException("IO error when loading the file: "
                + e.getLocalizedMessage());
        } finally {
            iohelper.closeReader(fr);
            iohelper.closeReader(br);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final String getContent() {
        return content;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final SourceTypes getType() {
        return type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FileRootImpl(");
        sb.append("type=").append(type);
        sb.append(", file=").append(file);
        sb.append(')');
        return sb.toString();
    }
}
