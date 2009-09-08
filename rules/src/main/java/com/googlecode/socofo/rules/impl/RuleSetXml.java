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
import javax.xml.bind.annotation.XmlRootElement;

import com.googlecode.socofo.rules.api.RuleSet;
import com.googlecode.socofo.rules.api.XmlFormatRules;

/**
 * The root implementation for formatter rules.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
@XmlRootElement(name = "formatter-rules")
public class RuleSetXml implements RuleSet {
	@XmlElement(name = "xml-rules", type = XmlFormatRulesXml.class)
	private XmlFormatRules xmlRules = null;

	/**
	 * 
	 */
	public RuleSetXml() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.googlecode.socofo.rules.api.RuleSet#getXmlFormatRules()
	 */
	@Override
	public XmlFormatRules getXmlFormatRules() {
		return xmlRules;
	}

}
