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
import com.googlecode.socofo.core.api.FileDestination;
import org.slf4j.*;

import javax.inject.Inject;
import java.io.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Impl of a FileDestination.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class FileDestinationImpl implements FileDestination {
    /**
     * The destination file to overwrite.
     */
    private File dest;
    /**
     * A logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(FileDestinationImpl.class);
    /**
     * The iohelper.
     */
    @Inject
    private IOHelper iohelper;
    /**
     * A reentrant lock.
     */
    private final ReentrantLock lock = new ReentrantLock();
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void writeContent(final String s, final String enc) {
        lock.lock();
        OutputStreamWriter fw = null;
        FileOutputStream fos = null;
        try {
            if (dest == null) {
                LOG.warn("No destination set!");
                return;
            }
            if (s == null) {
                LOG.warn("Nothing to write!");
                return;
            }
            final File parentDir = dest.getParentFile();
            parentDir.mkdirs();
            final boolean fileCreated = dest.createNewFile();
            if (!fileCreated) {
                LOG.info("File " + dest.getAbsolutePath() + " could not be created! Trying again.");
            }
            fos = new FileOutputStream(dest);
            if (enc == null) {
                fw = new OutputStreamWriter(fos);
            } else {
                fw = new OutputStreamWriter(fos, enc);
            }
            fw.write(s);
            fw.flush();
        } catch (final IOException e) {
            LOG.error("writeContent", e);
        } finally {
            iohelper.closeWriter(fw);
            iohelper.closeOutputstream(fos);
            lock.unlock();
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setFile(final File f) {
        lock.lock();
        try {
            dest = f;
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * Calculates the source file destination.
     * 
     * @param baseDirStr
     *            the base directory
     * @param targetDirStr
     *            the target directory
     * @param sourceFileStr
     *            the source file absolute path
     * @return the target file absolute path
     */
    protected static String parseDestination2(final String baseDirStr, final String targetDirStr,
        final String sourceFileStr) {
        if (baseDirStr == null) {
            LOG.warn("No base directory given!");
            return null;
        }
        if ((sourceFileStr == null) || (sourceFileStr.length() <= 0)) {
            LOG.warn("No source file given!");
            return null;
        }
        if (!sourceFileStr.startsWith(baseDirStr + File.separatorChar)) {
            LOG.warn("The source file (" + sourceFileStr + ") is NOT a part of the base directory " + baseDirStr + "!");
            return null;
        }
        String rc = sourceFileStr;
        if (targetDirStr != null) {
            final String base = sourceFileStr.substring(baseDirStr.length());
            rc = targetDirStr + base;
        }
        return rc;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public File parseDestination(final File baseDir, final File targetDir, final File sourceFile) {
        if (baseDir == null) {
            LOG.warn("No base directory given!");
            return null;
        }
        if (sourceFile == null) {
            LOG.warn("No source file given!");
            return null;
        }
        final String baseDirStr = baseDir.getAbsolutePath();
        final String targetDirStr = targetDir == null ? baseDirStr : targetDir.getAbsolutePath();
        final String sourceFileStr = sourceFile.getAbsolutePath();
        final String fileDestStr = parseDestination2(baseDirStr, targetDirStr, sourceFileStr);
        final File rc = new File(fileDestStr);
        return rc;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FileDestinationImpl(");
        sb.append("dest=").append(dest);
        sb.append(')');
        return sb.toString();
    }
}
