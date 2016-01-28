/*
 * SoCoFo - Another source code formatter
 * Copyright (C) 2013  Dirk Strauss
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
package com.googlecode.socofo.common.api;

import java.io.File;
import java.net.URL;
import java.util.List;

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
    String PARAM_RULESURL = "--rulesUrl";
    /**
     * Parameter for defining the base directory to scan for source files.
     */
    String PARAM_BASEDIR = "--base";
    /**
     * parameter for defining the target directory to write the transformed
     * source code to.
     */
    String PARAM_TARGETDIR = "--target";
    /**
     * The help parameter.
     */
    String PARAM_HELP = "--help";
    /**
     * the types parameter to filter the source code files.
     */
    String PARAM_TYPES = "--types";
    /**
     * the types parameter to filter the source code files.
     */
    String PARAM_ADDFILE = "--addFile";
    /**
     * The return code for success.
     */
    int RC_SUCCESS = 0;
    /**
     * the return code for showing the help screen. This is not really an error
     * but indicating that no transformation was done as possibly intended.
     */
    int RC_SHOWHELP = 1;
    /**
     * the return code for having no source base.
     */
    int RC_NOSOURCEBASE = 2;
    /**
     * the return code for having found no source files.
     */
    int RC_NOSOURCES = 3;
    /**
     * the return code for having no rules.
     */
    int RC_NORULES = 4;
    /**
     * the return code for having no scheduler. This should not happen in
     * production but in developer mode.
     */
    int RC_NOSCHEDULER = 5;
    /**
     * the return code for having a set of transformation errors.
     */
    int RC_TRANSFORM = 6;
    /**
     * the return code for having no source types.
     */
    int RC_NOTYPES = 7;
    
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
    
    /**
     * Sets the base directory to start scanning for source files.
     * 
     * @param srcDir
     *            the source directory.
     */
    void setBaseDirectory(File srcDir);
    
    /**
     * Sets the target directory to write the results to.
     * 
     * @param targetDir
     *            the target directory
     */
    void setTargetDirectory(File targetDir);
    
    /**
     * Sets the rules url.
     * 
     * @param rulesUrl
     *            the rules url
     */
    void setRules(URL rulesUrl);
    
    /**
     * Sets the source types to support when scanning for files.
     * 
     * @param types
     *            a list of types to scan for.
     */
    void setTypes(List<String> types);
    
    /**
     * Applies additional filters to the source scan. This list contains regular
     * expressions that are applied to the source file found via the types scan.
     * If applyable, the source file is added finally. If not, the source file
     * is dropped.
     * 
     * @param filters
     *            the list of regular expressions
     */
    void applySourceFilters(List<String> filters);
    
}
