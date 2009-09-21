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
package com.googlecode.socofo.common.api;

import java.io.File;
import java.net.URL;

/**
 * The contract for a possible runtime scanner.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface MainRuntime {
	/**
	 * parameter for the rules url.
	 */
	public static final String PARAM_RULESURL = "--rulesUrl";
	/**
	 * Parameter for defining the base directory to scan for source files.
	 */
	public static final String PARAM_BASEDIR = "--base";
	/**
	 * parameter for defining the target directory to write the transformed
	 * source code to.
	 */
	public static final String PARAM_TARGETDIR = "--target";
	/**
	 * The help parameter.
	 */
	public static final String PARAM_HELP = "--help";
	/**
	 * the types parameter to filter the source code files.
	 */
	public static final String PARAM_TYPES = "--types";
	/**
	 * The return code for success.
	 */
	public static final int RC_SUCCESS = 0;
	/**
	 * the return code for showing the help screen. This is not really an error
	 * but indicating that no transformation was done as possibly intended.
	 */
	public static final int RC_SHOWHELP = 1;
	/**
	 * the return code for having no source base.
	 */
	public static final int RC_NOSOURCEBASE = 2;
	/**
	 * the return code for having found no source files.
	 */
	public static final int RC_NOSOURCES = 3;
	/**
	 * the return code for having no rules.
	 */
	public static final int RC_NORULES = 4;
	/**
	 * the return code for having no scheduler. This should not happen in
	 * production but in developer mode.
	 */
	public static final int RC_NOSCHEDULER = 5;
	/**
	 * the return code for having a set of transformation errors.
	 */
	public static final int RC_TRANSFORM = 6;

	/**
	 * Executes the scanner, starts the translation jobs etc.
	 * 
	 * @return 0 for success, otherwise an error code.
	 */
	int execute();

	/**
	 * Parses the given parameters and prepares the execution.
	 * 
	 * @param args
	 *            the arguments
	 */
	void parseParams(String... args);

	/**
	 * Returns the detected base directory.
	 * 
	 * @return the base directory
	 */
	File getBaseDirectory();

	/**
	 * Returns the detected target directory.
	 * 
	 * @return the target directory
	 */
	File getTargetDirectory();

	/**
	 * Returns the flag value for showing the help screen.
	 * 
	 * @return the flag value
	 */
	boolean showHelpScreen();

	/**
	 * Resets the detected values.
	 */
	void resetSettings();

	/**
	 * Returns the detected rules url.
	 * 
	 * @return the rules url
	 */
	URL getRulesUrl();

}
