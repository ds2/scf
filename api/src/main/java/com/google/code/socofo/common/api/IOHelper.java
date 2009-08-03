/**
 * 
 */
package com.google.code.socofo.common.api;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

/**
 * A helper to support operation with input/output.
 * 
 * @author kaeto23
 * 
 */
public interface IOHelper {
	/**
	 * Closes the given input stream
	 * 
	 * @param is
	 *            the input stream to close
	 */
	void closeInputstream(InputStream is);

	/**
	 * Closes the given reader
	 * 
	 * @param r
	 *            the reader to close
	 */
	void closeReader(Reader r);

	/**
	 * Reads from the given inputstream until the buffer is full
	 * 
	 * @param bis
	 *            the input stream to read from
	 * @param buffer
	 *            the buffer to fill the read bytes into
	 * @return the count of read bytes. A negative number indicates an end or a
	 *         IO exception.
	 */
	int read(InputStream bis, byte[] buffer);

	/**
	 * Closes the given output stream
	 * 
	 * @param baos
	 *            the outputstream to close
	 */
	void closeOutputstream(OutputStream baos);

	/**
	 * Creates a string from the given sequence of bytes
	 * 
	 * @param byteArray
	 *            the bytes
	 * @param encoding
	 *            the encoding
	 * @return null, or the string
	 */
	String createString(byte[] byteArray, String encoding);
}
