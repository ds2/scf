/*
 * SoCoFo - Another source code formatter
 * Copyright (C) 2012  Dirk Strauss
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
package com.googlecode.socofo.core.impl.xml;

/**
 * An xml end tag.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class EndElement extends BaseXmlObject {
	/**
	 * the svuid.
	 */
	private static final long serialVersionUID = -6178877282710074729L;

	/**
	 * Inits an empty end tag.
	 */
	public EndElement() {
		this(null);
	}

	/**
	 * Inits an end tag with the given element name.
	 * 
	 * @param string
	 *            the element name
	 */
	public EndElement(final String string) {
		super(string);
		startSeq = "</";
		endTag = true;
	}
}
