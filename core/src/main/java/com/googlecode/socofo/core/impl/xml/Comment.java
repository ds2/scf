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
package com.googlecode.socofo.core.impl.xml;

/**
 * @author kaeto23
 * 
 */
public class Comment extends BaseXmlObject {

	/**
	 * 
	 */
	public Comment() {
		startSeq = "<!--";
		endSeq = "-->";
	}

	@Override
	public void setInnerContent(String s) {
		if (s == null) {
			super.setInnerContent(s);
			return;
		}
		String comment = s.trim();
		if (comment.startsWith(startSeq)) {
			comment = comment.substring(4);
		}
		if (comment.endsWith(endSeq)) {
			comment = comment.substring(0, comment.length() - 4);
		}
		comment = comment.trim();
		super.setInnerContent(comment);
	}

}
