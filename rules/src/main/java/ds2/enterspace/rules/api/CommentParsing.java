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
package ds2.enterspace.rules.api;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author kaeto23
 * 
 */
@XmlType(name = "CommentParsingType", namespace = "http://www.ds2/ns/socofo")
@XmlEnum
public enum CommentParsing {
	/**
	 * The comment is untouched. No preparsing is done (Default)
	 */
	NoParsing,
	/**
	 * Replaces any NEWLINE chars with a single SPACE
	 */
	ReplaceNewlinesWithSpace;
}
