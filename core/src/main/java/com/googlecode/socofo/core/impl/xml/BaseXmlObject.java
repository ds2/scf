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

import java.util.HashMap;
import java.util.Map;

/**
 * The base class for XML elements.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class BaseXmlObject implements XmlObject {
	private String elName = null;
	private Map<String, String> attributes = null;
	private String currentAttributeName = null;
	protected String startSeq = "<";
	protected String endSeq = ">";
	private String innerContent;
	protected boolean endTag = false;

	/**
	 * Inits the base object.
	 */
	public BaseXmlObject() {
		attributes = new HashMap<String, String>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setElementName(String s) {
		elName = s;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getElementName() {
		return elName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasAttributes() {
		return !attributes.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAttributName(String s) {
		currentAttributeName = s;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAttributValue(String s) {
		attributes.put(currentAttributeName, s);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getEndSequence() {
		return endSeq;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getStartSequence() {
		return startSeq;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEndSequence(String s) {
		endSeq = s;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getInnerContent() {
		return innerContent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setInnerContent(String s) {
		innerContent = s;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEndTag() {
		return endTag;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append(getClass().getName());
		sb.append("(");
		sb.append("elName=").append(elName);
		sb.append(",startSeq=").append(startSeq);
		sb.append(",endSeq=").append(endSeq);
		sb.append(",endTag=").append(endTag);
		sb.append(")");
		return sb.toString();
	}

}
