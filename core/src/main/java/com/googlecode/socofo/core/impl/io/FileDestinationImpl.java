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
 * @author kaeto23
 * 
 */
public class FileDestinationImpl implements FileDestination {
	private File dest = null;
	private static final Logger LOG = Logger
			.getLogger(FileDestinationImpl.class.getName());
	@Inject
	private IOHelper iohelper = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeContent(String s, String enc) {
		if (dest == null) {
			LOG.severe("No destination set!");
			return;
		}
		OutputStreamWriter fw = null;
		try {
			FileOutputStream fos = new FileOutputStream(dest);
			fw = new OutputStreamWriter(fos, enc);
			fw.write(s);
		} catch (IOException e) {
			LOG
					.throwing(FileDestinationImpl.class.getName(),
							"writeContent", e);
		} finally {
			iohelper.closeWriter(fw);
		}

	}

	@Override
	public void setFile(File f) {
		dest = f;
	}

}
