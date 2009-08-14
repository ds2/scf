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

/**
 * Some rules for inserting a NEWLINE.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface NewlineRules {
	/**
	 * Flag for NEWLINE after an XML closing tag.
	 * 
	 * @return flag value
	 */
	Boolean getAfterXmlEndTag();

	/**
	 * Flag for NEWLINE after each xml attribute.
	 * 
	 * @return flag value
	 */
	Boolean getAfterEachXmlAttribute();

	/**
	 * Flag for NEWLINE before a xml comment.
	 * 
	 * @return flag value
	 */
	Boolean getBeforeComment();

	/**
	 * Flag for NEWLINE before an if statement.
	 * 
	 * @return flag value
	 */
	Boolean getBeforeIfStatements();
}