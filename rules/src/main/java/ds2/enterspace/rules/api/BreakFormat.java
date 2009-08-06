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
 * The rules for breaking content sequences into several lines.
 * 
 * @author kaeto23
 * 
 */
@XmlType(name = "BreakFormat", namespace = "http://www.ds2/ns/socofo")
@XmlEnum()
public enum BreakFormat {
	/**
	 * basically a BeautyBreak, but when facing too long words, a break is
	 * forced, breaking the word into two words
	 */
	BeautyForcedBreak,
	/**
	 * breaks the line according to spaces. If a word exceeds the line, the word
	 * is printed, and a new line added
	 */
	BeautyBreak,
	/**
	 * No special breaks. The content is returned 1:1
	 */
	NoBreak;
}
