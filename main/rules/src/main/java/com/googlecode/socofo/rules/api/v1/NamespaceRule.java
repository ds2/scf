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
package com.googlecode.socofo.rules.api.v1;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlSeeAlso;

import com.googlecode.socofo.rules.impl.v1.NamespaceRuleXml;

/**
 * A namespace rule.
 * 
 * @author Dirk Strauss
 * @version 1.0
 * 
 */
@XmlSeeAlso(NamespaceRuleXml.class)
public interface NamespaceRule extends Serializable {
	/**
	 * Returns the prefix to use for this namespace.
	 * 
	 * @return the prefix to use. May return null in case of NO prefix to be
	 *         used.
	 */
	public String getPrefix();

	/**
	 * Returns the namespace uri.
	 * 
	 * @return the namespace uri
	 */
	public String getNamespace();

	/**
	 * Returns the url to the XSD for this namespace.
	 * 
	 * @return null, or the url for this namespace
	 */
	public String getNamespaceUrl();
}
