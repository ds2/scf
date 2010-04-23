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
package com.googlecode.socofo.core.impl.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.googlecode.socofo.common.api.IOHelper;
import com.googlecode.socofo.core.api.FileDestination;

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
	private File dest = null;
	/**
	 * A logger.
	 */
	private static final Logger LOG = Logger
			.getLogger(FileDestinationImpl.class.getName());
	/**
	 * The iohelper.
	 */
	@Inject
	private IOHelper iohelper = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeContent(final String s, final String enc) {
		LOG.entering(FileDestinationImpl.class.getName(), "writeContent");
		if (dest == null) {
			LOG.severe("No destination set!");
			return;
		}
		if (s == null) {
			LOG.severe("Nothing to write!");
			return;
		}
		OutputStreamWriter fw = null;
		FileOutputStream fos = null;
		try {
			final File parentDir = dest.getParentFile();
			parentDir.mkdirs();
			final boolean fileCreated = dest.createNewFile();
			if (!fileCreated) {
				LOG.info("File " + dest.getAbsolutePath()
						+ " could not be created! Trying again.");
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
			LOG
					.throwing(FileDestinationImpl.class.getName(),
							"writeContent", e);
		} finally {
			iohelper.closeWriter(fw);
			iohelper.closeOutputstream(fos);
		}
		LOG.exiting(FileDestinationImpl.class.getName(), "writeContent");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFile(final File f) {
		dest = f;
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
	protected String parseDestination2(final String baseDirStr,
			final String targetDirStr, final String sourceFileStr) {
		LOG.entering(FileDestinationImpl.class.getName(), "parseDestination2",
				new Object[] { baseDirStr, targetDirStr, sourceFileStr });
		if (baseDirStr == null) {
			LOG.warning("No base directory given!");
			return null;
		}
		if (sourceFileStr == null || sourceFileStr.length() <= 0) {
			LOG.warning("No source file given!");
			return null;
		}
		if (!sourceFileStr.startsWith(baseDirStr + File.separatorChar)) {
			LOG.warning("The source file (" + sourceFileStr
					+ ") is NOT a part of the base directory " + baseDirStr
					+ "!");
			return null;
		}
		String rc = sourceFileStr;
		if (targetDirStr != null) {
			final String base = sourceFileStr.substring(baseDirStr.length());
			rc = targetDirStr + base;
		}
		LOG.exiting(FileDestinationImpl.class.getName(), "parseDestination2",
				rc);
		return rc;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public File parseDestination(final File baseDir, final File targetDir,
			final File sourceFile) {
		LOG.entering(FileDestinationImpl.class.getName(), "parseDestination",
				new Object[] { baseDir, targetDir, sourceFile });
		if (baseDir == null) {
			LOG.severe("No base directory given!");
			return null;
		}
		if (sourceFile == null) {
			LOG.severe("No source file given!");
			return null;
		}
		final String baseDirStr = baseDir.getAbsolutePath();
		final String targetDirStr = targetDir == null ? baseDirStr : targetDir
				.getAbsolutePath();
		final String sourceFileStr = sourceFile.getAbsolutePath();
		final String fileDestStr = parseDestination2(baseDirStr, targetDirStr,
				sourceFileStr);
		final File rc = new File(fileDestStr);
		LOG
				.exiting(FileDestinationImpl.class.getName(),
						"parseDestination", rc);
		return rc;
	}

	/**
	 * Sets the test io helper.
	 * 
	 * @param i
	 *            the iohelper for test runs
	 */
	public void setTestIohelper(final IOHelper i) {
		iohelper = i;
	}

}
