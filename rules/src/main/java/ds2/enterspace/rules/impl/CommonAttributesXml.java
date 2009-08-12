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
package ds2.enterspace.rules.impl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import ds2.enterspace.rules.api.CommonAttributes;

/**
 * @author kaeto23
 * 
 */
@XmlType(name = "CommonAttributesType", namespace = "http://www.ds2/ns/socofo")
public class CommonAttributesXml implements CommonAttributes {
	@XmlElement(required = true)
	private int maxLineWidth = 0;
	@XmlElement(required = true)
	private String indentSequence = null;
	@XmlElement(required = false, defaultValue = "4")
	private int tabSize = 4;

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

}
