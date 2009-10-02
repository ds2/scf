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
package com.googlecode.socofo.rules.impl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.googlecode.socofo.rules.api.CommonAttributes;

/**
 * @author kaeto23
 * 
 */
@XmlType(name = "CommonAttributesType")
public class CommonAttributesXml implements CommonAttributes {
	/**
	 * the svuid
	 */
	private static final long serialVersionUID = 8599603676435230001L;
	@XmlElement(required = true)
	private int maxLineWidth = 0;
	@XmlElement(required = true)
	private String indentSequence = null;
	@XmlElement(required = false, defaultValue = "4")
	private int tabSize = 4;
	@XmlElement(defaultValue = "false")
	private Boolean stopOnLongline = false;

	@Override
	public String getIndentSequence() {
		return indentSequence;
	}

	@Override
	public int getMaxLinewidth() {
		return maxLineWidth;
	}

	@Override
	public int getTabSize() {
		return tabSize;
	}

	@Override
	public Boolean getStopOnLongline() {
		return stopOnLongline;
	}

}
