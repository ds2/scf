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
package com.googlecode.socofo.rules.impl.v1;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.googlecode.socofo.rules.api.v1.TextBlocks;

/**
 * @author kaeto23
 * 
 */
@XmlType(name = "TextBlocksType")
public class TextBlocksXml implements TextBlocks {
	/**
	 * the svuid
	 */
	private static final long serialVersionUID = 2815059537069771320L;
	@XmlElement
	private String header;
	@XmlElement
	private String footer;

	@Override
	public String getFooter() {
		return footer;
	}

	@Override
	public String getHeader() {
		return header;
	}

}
