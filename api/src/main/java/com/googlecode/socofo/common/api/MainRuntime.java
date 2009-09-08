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
 * @author kaeto23
 * 
 */
public interface MainRuntime {
	public static final String PARAM_RULESURL = "--rulesUrl";
	public static final String PARAM_BASEDIR = "--base";
	public static final String PARAM_TARGETDIR = "--target";
	public static final String PARAM_HELP = "--help";
	public static final String PARAM_TYPES = "--types";

	public static final int ERROR_SHOWHELP = 1;
	public static final int ERROR_NOSOURCEBASE = 2;
	public static final int ERROR_NOSOURCES = 3;
	public static final int ERROR_NORULES = 4;
	public static final int ERROR_NOSCHEDULER = 5;
	public static final int ERROR_TRANSFORM = 6;

	int execute();

	void parseParams(String... args);

	File getBaseDirectory();

	File getTargetDirectory();

	boolean showHelpScreen();

	void resetSettings();

	URL getRulesUrl();

}
