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
import java.util.Scanner;
import java.util.logging.Logger;

import ds2.enterspace.core.api.LineHandler;
import ds2.enterspace.rules.api.BreakFormat;

/**
 * @author kaeto23
 * 
 */
public class LineHandlerImpl implements LineHandler {
	/**
	 * A logger
	 */
	private static final Logger log = Logger.getLogger(LineHandlerImpl.class
			.getName());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> breakContent(int lineWidth, String content,
			int firstIndent, BreakFormat breakType) {
		List<String> rc = new ArrayList<String>();
		return rc;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int calculateLineWidth(int maxLineWidth, int ac) {
		if (maxLineWidth <= 0) {
			// no linewidth set
			return -1;
		}
		int rc = maxLineWidth;
		rc -= ac;
		if (rc < 0) {
			log.warning("too much additional chars: " + ac);
			rc = 0;
		} else if (rc == 0) {
			log.warning("Width and additional chars are the same!");
		}
		return rc;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String cleanComment(String lines) {
		log.entering(LineHandlerImpl.class.getName(), "cleanComment", lines);
		StringBuffer rc = new StringBuffer();
		if (lines == null) {
			return "";
		}
		Scanner scanner = new Scanner(lines);
		scanner.useDelimiter("\n");
		while (scanner.hasNext()) {
			String line = scanner.next();
			line = line.trim();
			if (line.startsWith("* ")) {
				line = line.substring(2);
			} else if (line.equalsIgnoreCase("*")) {
				line = "";
			}
			rc.append(line);
			rc.append("\n");
		}
		String rc2 = rc.toString();
		// remove the last \n char
		rc2 = rc2.substring(0, rc2.length() - 1);
		log.exiting(LineHandlerImpl.class.getName(), "cleanComment", rc2);
		return rc2;
	}

}
