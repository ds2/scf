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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.googlecode.socofo.common.api.IOHelper;
import com.googlecode.socofo.core.api.SourceTypes;
import com.googlecode.socofo.core.api.StreamRoot;


/**
 * @author kaeto23
 * 
 */
public class StreamRootImpl implements StreamRoot {
	/**
	 * The content
	 */
	private String content = null;
	/**
	 * The iohelper
	 */
	@Inject
	private IOHelper iohelper = null;
	/**
	 * The default encoding to use
	 */
	@Inject
	@Named("defaultEncoding")
	private String encoding = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void loadStream(InputStream is, String enc) {
		if (is == null) {
			return;
		}
		if (enc != null) {
			this.encoding = enc;
		}
		BufferedInputStream bis = new BufferedInputStream(is);
		byte[] buffer = new byte[6000];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while (true) {
			int numRead = iohelper.read(bis, buffer);
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
		// TODO Auto-generated method stub
		return null;
	}

}
