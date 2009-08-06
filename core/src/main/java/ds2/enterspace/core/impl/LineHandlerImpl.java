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
package ds2.enterspace.core.impl;

import java.util.ArrayList;
import java.util.List;

import ds2.enterspace.core.api.LineHandler;
import ds2.enterspace.rules.api.BreakFormat;
import ds2.enterspace.rules.api.CommonAttributes;

/**
 * @author kaeto23
 * 
 */
public class LineHandlerImpl implements LineHandler {
	List<String> lines = null;

	/**
	 * 
	 */
	public LineHandlerImpl() {
		lines = new ArrayList<String>();
	}

	@Override
	public List<String> breakContent(int lineWidth, String content,
			int firstIndent, BreakFormat breakType) {
		List<String> rc = new ArrayList<String>();
		return rc;
	}

	@Override
	public int calculateLineWidth(CommonAttributes ca, int additionalChars) {
		// TODO Auto-generated method stub
		return 0;
	}

}
