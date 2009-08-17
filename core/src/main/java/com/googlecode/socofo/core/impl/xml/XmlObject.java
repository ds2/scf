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
package com.googlecode.socofo.core.impl.xml;

import java.util.Map;

import com.googlecode.socofo.core.api.LineHandler;
import com.googlecode.socofo.core.api.SourceWriter;
import com.googlecode.socofo.rules.api.XmlFormatRules;

/**
 * contract for an XML object
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface XmlObject {
	void setElementName(String s);

	String getElementName();

	void setAttributName(String s);

	void setAttributValue(String s);

	boolean hasAttributes();

	Map<String, String> getAttributes();

	String getStartSequence();

	String getEndSequence();

	void setEndSequence(String s);

	/**
	 * Sets the inner content of an xml object. This can be a text comment
	 * usually.
	 * 
	 * @param s
	 *            the inner content
	 */
	void setInnerContent(String s);

	/**
	 * Returns the inner content.
	 * 
	 * @return the inner content, or null
	 */
	String getInnerContent();

	/**
	 * Returns the isEnd flag.
	 * 
	 * @return TRUE if the current element is an end tag, otherwise FALSE
	 */
	boolean isEndTag();

	/**
	 * Writes this element to the source writer.
	 * 
	 * @param indent
	 *            the possible indent to use
	 * @param sw
	 *            the source writer
	 * @param rules
	 *            the xml format rules
	 * @param lh
	 *            the line handler
	 */
	void writeElement(final int indent, SourceWriter sw, XmlFormatRules rules,
			LineHandler lh);
}
