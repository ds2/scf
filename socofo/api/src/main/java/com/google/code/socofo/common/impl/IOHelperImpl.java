/**
 * 
 */
package com.google.code.socofo.common.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import com.google.code.socofo.common.api.IOHelper;
import com.google.inject.Singleton;

/**
 * @author kaeto23
 * 
 */
@Singleton
public class IOHelperImpl implements IOHelper {
	/**
	 * A logger
	 * 
	 */
	private static final transient Logger log = Logger
			.getLogger(IOHelperImpl.class.getName());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeInputstream(InputStream is) {
		if (is == null) {
			log.warning("No inputstream given!");
			return;
		}
		try {
			is.close();
		} catch (IOException e) {
			log.throwing(IOHelperImpl.class.getName(), "closeInputstream", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeReader(Reader r) {
		log.entering(IOHelperImpl.class.getName(), "closeReader", r);
		if (r != null) {
			try {
				r.close();
			} catch (IOException e) {
				log.throwing(IOHelper.class.getName(), "closeReader", e);
			}
		}
		log.exiting(IOHelperImpl.class.getName(), "closeReader");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeOutputstream(OutputStream baos) {
		try {
			baos.close();
		} catch (IOException e) {
			log.throwing(IOHelperImpl.class.getName(), "closeOutputstream", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createString(byte[] byteArray, String encoding) {
		String rc = null;
		try {
			rc = new String(byteArray, encoding);
		} catch (UnsupportedEncodingException e) {
			log.throwing(IOHelperImpl.class.getName(), "createString", e);
		}
		return rc;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int read(InputStream bis, byte[] buffer) {
		int rc;
		try {
			rc = bis.read(buffer);
		} catch (IOException e) {
			log.throwing(IOHelperImpl.class.getName(), "read", e);
			rc = -1;
		}
		return rc;
	}

}
