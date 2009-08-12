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
package ds2.enterspace.core.impl.xml;

import java.util.Map;

/**
 * @author kaeto23
 * 
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

	boolean isEndTag();
}
