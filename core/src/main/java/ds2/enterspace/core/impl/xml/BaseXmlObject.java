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

import java.util.HashMap;
import java.util.Map;

/**
 * @author kaeto23
 * 
 */
public class BaseXmlObject implements XmlObject {
	private String elName = null;
	private Map<String, String> attributes = null;
	private String currentAttributeName = null;
	protected String startSeq = "<";
	protected String endSeq = ">";
	private String innerContent;

	/**
	 * 
	 */
	public BaseXmlObject() {
		attributes = new HashMap<String, String>();
	}

	@Override
	public void setElementName(String s) {
		elName = s;
	}

	@Override
	public Map<String, String> getAttributes() {
		return attributes;
	}

	@Override
	public String getElementName() {
		return elName;
	}

	@Override
	public boolean hasAttributes() {
		return !attributes.isEmpty();
	}

	@Override
	public void setAttributName(String s) {
		currentAttributeName = s;
	}

	@Override
	public void setAttributValue(String s) {
		attributes.put(currentAttributeName, s);
	}

	@Override
	public String getEndSequence() {
		return endSeq;
	}

	@Override
	public String getStartSequence() {
		return startSeq;
	}

	@Override
	public void setEndSequence(String s) {
		endSeq = s;
	}

	@Override
	public String getInnerContent() {
		return innerContent;
	}

	@Override
	public void setInnerContent(String s) {
		innerContent = s;
	}

}
