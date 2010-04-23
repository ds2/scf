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
package com.googlecode.socofo.rules.api;

import java.io.InputStream;
import java.net.URL;

/**
 * Contract for a loader for rules. Implementations of this contract will load
 * xml configuration files and interprete them for socofo.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface RulesLoader {
	/**
	 * Loads the XML formatter rules from the given input stream
	 * 
	 * @param is
	 *            the input stream containing the configuration
	 * @return null, or the configuration
	 */
	XmlFormatRules loadFormatRules(InputStream is);

	/**
	 * Convenience method for loading the rules via a URL.
	 * 
	 * @param formatterXml
	 *            the url to the rules
	 * @return the rule set, or null
	 */
	RuleSet loadRulesFromUrl(URL formatterXml);

	/**
	 * Loads the rules from the given input stream.
	 * 
	 * @param is
	 *            the input stream
	 * @return null, or the rules
	 */
	RuleSet loadRules(InputStream is);
}
