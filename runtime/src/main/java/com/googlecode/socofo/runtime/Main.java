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
package com.googlecode.socofo.runtime;

import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * Main program to let the formatter run on in a terminal.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class Main {

	/**
	 * The rules url parameter.
	 */
	public static final String PARAM_RULESURL = "--rulesUrl";

	/**
	 * A logger.
	 */
	private static final Logger log = Logger.getLogger(Main.class.getName());

	/**
	 * the console to write log information to.
	 */
	private PrintWriter console = null;

	/**
     */
	public Main() {
		console = System.console().writer();
	}

	/**
	 * Checks if the params request some help from the user.
	 * 
	 * @param params
	 *            the parameters
	 * 
	 * @return TRUE if the user wants to display the help information, otherwise
	 *         FALSE
	 */
	private boolean wantsHelp(String[] params) {
		log.entering(Main.class.getName(), "wantsHelp", params);
		boolean rc = false;
		if ((params == null) || (params.length <= 0)) {
			rc = true;
		} else {
			for (String param : params) {
				if (param.equalsIgnoreCase("help")
						|| param.equalsIgnoreCase("--help")) {
					rc = true;
				}
			}
		}
		log.exiting(Main.class.getName(), "wantsHelp", rc);
		return rc;
	}

	/**
	 * Prints the help information.
	 */
	private void printHelp() {
		console.println("SoCoFo Source Code Formatter");
		console.println("Copyright (C) 2009 Dirk Strauss <lexxy23@gmail.com>");

		console.println();
		console.println("Parameters:");
		console.println(PARAM_RULESURL
				+ " = sets the url where the formatter rules can be found ("
				+ PARAM_RULESURL
				+ "=http://test.local/test/formatterRules.xml)");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main m = new Main();
		m.execute(args);
	}

	/**
	 * Executes the main program with the given parameters.
	 * 
	 * @param args
	 *            the parameters
	 */
	public void execute(String[] args) {
		if (wantsHelp(args)) {
			printHelp();
			return;
		}
	}
}
