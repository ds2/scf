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

import com.googlecode.socofo.rules.api.NamespaceRule;


/**
 * @author kaeto23
 * 
 */
@XmlType(name = "NamespaceRuleType", namespace = "http://www.ds2/ns/socofo")
public class NamespaceRuleXml implements NamespaceRule {
	@XmlElement(required = true)
	private String namespace;
	@XmlElement(required = true)
	private String prefix;
	@XmlElement(required = false, nillable = true)
	private String namespaceUrl;

	/**
	 * 
	 */
	public NamespaceRuleXml() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getNamespace() {
		return namespace;
	}

	@Override
	public String getNamespaceUrl() {
		return namespaceUrl;
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

}