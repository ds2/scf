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
import java.util.List;

/**
 * All the formatter rules for XML files.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface XmlFormatRules extends Serializable {
	/**
	 * Returns the common attributes for xml files.
	 * 
	 * @return the common attributes
	 */
	CommonAttributes getCommonAttributes();

	/**
	 * Returns a list of namespace rules.
	 * 
	 * @return a list of namespace rules, or null
	 */
	List<NamespaceRule> getNamespaceRules();

	/**
	 * Returns the final bracket policy. This is usually the NEWLINE behaviour
	 * when a closing &gt; sign for the current tag is found. This rule here
	 * defines how to deal with this.
	 * 
	 * @return null, or the final bracket policy
	 */
	FinalBracketPolicy getAlignFinalBracketOnNewline();

	/**
	 * returns the flag if empty lines should be cleared or not.
	 * 
	 * @return TRUE or FALSE. Default is TRUE
	 */
	Boolean getClearBlanklines();

	/**
	 * Returns the flag for inserting a whitespace before a /&gt; sequence.
	 * 
	 * @return TRUE or FALSE
	 */
	Boolean getInsertWsAtEmptyElement();

	/**
	 * Returns the flag for sorting attributes by name.
	 * 
	 * @return TRUE if attributes should be sorted by name, otherwise FALSE
	 */
	Boolean getSortAttributes();

	/**
	 * Returns possible text blocks to insert into the transformed code.
	 * 
	 * @return some text blocks
	 */
	TextBlocks getTextBlocks();

	/**
	 * Returns some rules for dealing with comments.
	 * 
	 * @return some rules for comments
	 */
	XmlCommentsRules getCommentsRules();

	/**
	 * Returns some rules for inserting NEWLINE sequences.
	 * 
	 * @return rules for NEWLINE behaviour
	 */
	NewlineRules getNewlineRules();
}
