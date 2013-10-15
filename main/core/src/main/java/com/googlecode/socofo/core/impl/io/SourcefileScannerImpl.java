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
package com.googlecode.socofo.core.impl.io;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.socofo.core.api.SourceTypeDetector;
import com.googlecode.socofo.core.api.SourceTypes;
import com.googlecode.socofo.core.api.SourcefileScanner;

/**
 * The base implementation of the source file scanner.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class SourcefileScannerImpl implements SourcefileScanner {
    /**
     * A logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SourcefileScannerImpl.class);
    /**
     * The source type detector.
     */
    @Inject
    private SourceTypeDetector localDetector;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<File> scan(final File baseDirectory, final List<SourceTypes> types) {
        final List<File> rc = new ArrayList<File>();
        if (baseDirectory == null) {
            LOG.warn("No base directory given!");
            return rc;
        }
        rc.addAll(getFiles(baseDirectory, types.toArray(new SourceTypes[0])));
        return rc;
    }
    
    /**
     * Returns all allowed files starting from the current base directory.
     * 
     * @param baseDir
     *            the base directory
     * @param types
     *            the list of allowed types
     * @return a list of found files matching the source type criteria
     */
    protected List<File> getFiles(final File baseDir, final SourceTypes... types) {
        final List<File> rc = new ArrayList<File>();
        if (baseDir == null) {
            LOG.warn("No base directory given, returning empty list!");
            return rc;
        }
        if ((types == null) || (types.length <= 0)) {
            LOG.warn("No types given, returning empty list!");
            return rc;
        }
        final List<SourceTypes> allowedTypes = new ArrayList<SourceTypes>();
        for (SourceTypes type : types) {
            allowedTypes.add(type);
        }
        final FileFilter fileFilter = new SourceFileFilter(localDetector, allowedTypes);
        LOG.debug("Scanning files in directory {}", baseDir);
        final File[] foundFiles = baseDir.listFiles(fileFilter);
        LOG.debug("Following files were found in step 1: {}", new Object[] { foundFiles });
        rc.addAll(scanFilesAndDirectories(foundFiles, types));
        return rc;
    }
    
    /**
     * Scans the given list of files for the given source type and returns them.
     * 
     * @param foundFiles
     *            the found files. If one of them is a directory, this method checks the content of
     *            the directory.
     * @param types
     *            the list of allowed types.
     * @return all source files matching the given source types
     */
    private List<File> scanFilesAndDirectories(final File[] foundFiles, final SourceTypes... types) {
        final List<File> rc = new ArrayList<File>();
        if ((foundFiles != null) && (foundFiles.length > 0)) {
            for (File foundFile : foundFiles) {
                LOG.debug("found {}", foundFile);
                if (foundFile.isDirectory() && !rc.contains(foundFile)) {
                    LOG.debug("Calling scan of child directory {}", foundFile);
                    rc.addAll(getFiles(foundFile, types));
                    continue;
                }
                rc.add(foundFile);
            }
        }
        LOG.debug("Returning found files: {}", rc);
        return rc;
    }
    
}
