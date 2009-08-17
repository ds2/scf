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

import java.io.Serializable;

/**
 * Rules for xml comments.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface XmlCommentsRules extends Serializable {
	/**
	 * Returns the flag for inserting a NEWLINE after a comment begin.
	 * 
	 * @return flag value
	 */
	Boolean getBreakAfterBegin();

	/**
	 * Returns the break format to use.
	 * 
	 * @return the break format
	 */
	BreakFormat getBreakType();

	/**
	 * Returns the flag for inserting a NEWLINE before the end of a comment.
	 * 
	 * @return flag value
	 */
	Boolean getBreakBeforeEnd();

	/**
	 * Returns the parsing instruction before dealing with comment content.
	 * 
	 * @return the parsing instructions
	 */
	CommentParsing getParsing();

	/**
	 * Returns a possible comment indent spacer.
	 * 
	 * @return a comment indent spacer, or null if not used
	 */
	String getCommentIndentSpacer();
}
