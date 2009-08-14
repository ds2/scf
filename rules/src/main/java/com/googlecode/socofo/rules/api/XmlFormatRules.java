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

import java.util.List;

/**
 * All the formatter rules for XML files
 * 
 * @author kaeto23
 * 
 */
public interface XmlFormatRules {
	CommonAttributes getCommonAttributes();

	List<NamespaceRule> getNamespaceRules();

	FinalBracketPolicy getAlignFinalBracketOnNewline();

	Boolean getClearBlanklines();

	Boolean getInsertWsAtEmptyElement();

	Boolean getSortAttributes();

	TextBlocks getTextBlocks();

	XmlCommentsRules getCommentsRules();

	NewlineRules getNewlineRules();
}
