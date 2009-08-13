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
import java.util.logging.Logger;

import com.google.inject.Singleton;
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
	private static final transient Logger log = Logger
			.getLogger(IOHelperImpl.class.getName());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeInputstream(final InputStream is) {
		if (is == null) {
			log.warning("No inputstream given!");
			return;
		}
		try {
			is.close();
		} catch (final IOException e) {
			log.throwing(IOHelperImpl.class.getName(), "closeInputstream", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeReader(final Reader r) {
		log.entering(IOHelperImpl.class.getName(), "closeReader", r);
		if (r != null) {
			try {
				r.close();
			} catch (final IOException e) {
				log.throwing(IOHelper.class.getName(), "closeReader", e);
			}
		}
		log.exiting(IOHelperImpl.class.getName(), "closeReader");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeOutputstream(final OutputStream baos) {
		if (baos == null) {
			log.warning("No output stream given!");
			return;
		}
		try {
			baos.close();
		} catch (final IOException e) {
			log.throwing(IOHelperImpl.class.getName(), "closeOutputstream", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createString(final byte[] byteArray, final String encoding) {
		String rc = null;
		try {
			rc = new String(byteArray, encoding);
		} catch (final UnsupportedEncodingException e) {
			log.throwing(IOHelperImpl.class.getName(), "createString", e);
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
			log.throwing(IOHelperImpl.class.getName(), "read", e);
			rc = -1;
		}
		return rc;
	}

}
