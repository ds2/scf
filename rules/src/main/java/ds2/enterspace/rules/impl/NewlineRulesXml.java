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

import ds2.enterspace.rules.api.NewlineRules;

/**
 * @author kaeto23
 * 
 */
@XmlType(name = "NewlineRulesType", namespace = "http://www.ds2/ns/socofo")
public class NewlineRulesXml implements NewlineRules {
	@XmlElement
	private Boolean beforeIfStatements = false;
	@XmlElement
	private Boolean beforeComment = false;
	@XmlElement
	private Boolean afterXmlEndTag = false;
	@XmlElement
	private Boolean afterEachXmlAttribute = false;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean getAfterEachXmlAttribute() {
		return afterEachXmlAttribute;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean getAfterXmlEndTag() {
		return afterXmlEndTag;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean getBeforeComment() {
		return beforeComment;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean getBeforeIfStatements() {
		return beforeIfStatements;
	}

}
