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

import java.io.Serializable;
import java.util.Map;

import com.googlecode.socofo.core.api.LineHandler;
import com.googlecode.socofo.core.api.SourceWriter;
import com.googlecode.socofo.core.exceptions.TranslationException;
import com.googlecode.socofo.rules.api.XmlFormatRules;

/**
 * contract for an XML object.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface XmlObject extends Serializable {
	/**
	 * Sets the element name of this xml object.
	 * 
	 * @param s
	 *            the element name
	 */
	void setElementName(String s);

	/**
	 * Returns the name of the xml element.
	 * 
	 * @return null, or the name of the xml element
	 */
	String getElementName();

	/**
	 * Sets the attribute name. This method is handy for adding attributes where
	 * you don't know the value yet.
	 * 
	 * @param s
	 *            the attribute name
	 */
	void setAttributName(String s);

	/**
	 * Sets the attribute value, of a attribute name is set. This will put the
	 * name and this value to the list of attributes of this xml element.
	 * 
	 * @param s
	 *            the attribute value
	 */
	void setAttributValue(String s);

	/**
	 * Checks if this xml object has attributes.
	 * 
	 * @return TRUE if it has, otherwise FALSE
	 */
	boolean hasAttributes();

	/**
	 * Returns the attributes.
	 * 
	 * @return an empty map, or all attributes.
	 */
	Map<String, String> getAttributes();

	/**
	 * Returns the start sequence for this element.
	 * 
	 * @return the start sequence. Usually &lt;
	 */
	String getStartSequence();

	/**
	 * Returns the end sequence of this xml element.
	 * 
	 * @return usually &gt;
	 */
	String getEndSequence();

	/**
	 * Sets the end sequence of this xml object.
	 * 
	 * @param s
	 *            the end sequence. Default is &gt;
	 */
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
	 * @param lastObject
	 *            the last xml object that has been processed
	 * 
	 * @param indent
	 *            the possible indent to use
	 * @param sw
	 *            the source writer
	 * @param rules
	 *            the xml format rules
	 * @param lh
	 *            the line handler
	 * @throws TranslationException
	 *             if the line became too long
	 */
	void writeElement(XmlObject lastObject, final int indent, SourceWriter sw,
			XmlFormatRules rules, LineHandler lh) throws TranslationException;

	/**
	 * Returns the tree level of this element.
	 * 
	 * @return the tree level of this xml object
	 */
	int getLevel();

	/**
	 * Sets the tree level for this object.
	 * 
	 * @param l
	 *            the tree level
	 */
	void setLevel(int l);

	/**
	 * A check if the inner content is basically empty.
	 * 
	 * @return TRUE if the inner content is empty, otherwise FALSE
	 */
	boolean hasEmptyInnerContent();
}
