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
package com.googlecode.socofo.rules.api.v1;

import java.io.Serializable;

/**
 * Some rules for inserting a NEWLINE.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface NewlineRules extends Serializable {
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

	/**
	 * Flag for NEWLINE when the level of any future element is different from
	 * the current one. Usually, this means that a NL will be done when you
	 * close a tag and a new tag will needed. There are some exceptions, like a
	 * following TEXT element which basically is a new element. But in this
	 * case, a level change will not occur.
	 * 
	 * @return TRUE if a NL should be done on level change, otherwise FALSE
	 */
	Boolean getOnLevelChange();
}
