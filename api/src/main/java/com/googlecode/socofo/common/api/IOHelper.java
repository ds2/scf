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
package com.googlecode.socofo.common.api;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * A helper to support operation with input/output.
 * 
 * @author Dirk Strauss
 * @version 1.0
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

	/**
	 * Closes a writer.
	 * 
	 * @param w
	 *            the writer to close
	 */
	void closeWriter(Writer w);
}
