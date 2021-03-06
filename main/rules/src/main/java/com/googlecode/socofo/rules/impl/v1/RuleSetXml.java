/*
 * SoCoFo - Another source code formatter
 * Copyright (C) 2013  Dirk Strauss
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.googlecode.socofo.rules.api.v1.RuleSetUpdater;
import com.googlecode.socofo.rules.api.v1.XmlFormatRules;
import com.googlecode.socofo.rules.api.v1.XmlFormatRulesUpdater;

/**
 * The root implementation for formatter rules.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
@XmlRootElement(name = "formatter-rules")
public class RuleSetXml implements RuleSetUpdater {
	/**
	 * the svuid.
	 */
	private static final long serialVersionUID = -1139524431473006111L;
	/**
	 * the xml rules.
	 */
	@XmlElement(name = "xml-rules", type = XmlFormatRulesXml.class)
	private XmlFormatRules xmlRules = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XmlFormatRules getXmlFormatRules() {
		return xmlRules;
	}

	/**
	 * {@inheritDoc}
	 */
	@XmlTransient
	@Override
	public XmlFormatRulesUpdater getXmlFormatRulesUpdater() {
		return (XmlFormatRulesUpdater) xmlRules;
	}
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append(RuleSetXml.class.getName());
		sb.append("(");
		sb.append("xmlRules=").append(xmlRules);
		sb.append(")");
		return sb.toString();
	}

}
