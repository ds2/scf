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
package com.googlecode.socofo.runtime.impl;

import java.io.Console;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.socofo.common.api.MainRuntime;
import com.googlecode.socofo.core.api.Scheduler;
import com.googlecode.socofo.core.api.SourceTypes;
import com.googlecode.socofo.core.api.TranslationJob;
import com.googlecode.socofo.runtime.Main;

/**
 * @author kaeto23
 * 
 */
@Singleton
public class DefRuntime implements MainRuntime {

	/**
	 * A logger.
	 */
	private static final Logger log = Logger.getLogger(DefRuntime.class
			.getName());

	/**
	 * the console to write log information to.
	 */
	private PrintWriter console = null;
	@Inject
	private Scheduler scheduler = null;

	/**
	 * 
	 */
	public DefRuntime() {
		Console c = System.console();
		if (c == null) {
			console = new PrintWriter(System.out);
		} else {
			console = c.writer();
		}
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
	protected boolean wantsHelp(String... params) {
		log.entering(Main.class.getName(), "wantsHelp", params);
		boolean rc = false;
		if ((params == null) || (params.length <= 0)) {
			rc = true;
		} else {
			for (String param : params) {
				if (param.equalsIgnoreCase("help")
						|| param.equalsIgnoreCase("--help")) {
					rc = true;
				} else if (param.length() <= 0) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.socofo.common.api.MainRuntime#execute(java.lang.String[])
	 */
	@Override
	public int execute(String... args) {
		int rc = 0;
		if (wantsHelp(args)) {
			printHelp();
			return 1;
		}
		if (scheduler == null) {
			log.severe("No scheduler set!");
			return 2;
		}
		File baseDir = new File(".");
		Thread currentThread = Thread.currentThread();
		scheduler.addWaiterThreads(currentThread);
		List<TranslationJob> jobs = null;
		File targetDir = null;
		jobs = scheduler.createLocalJobs(baseDir, targetDir, SourceTypes.XML);
		scheduler.addJobs(jobs);
		scheduler.startScheduler();
		try {
			wait();
		} catch (InterruptedException e) {
			e.getLocalizedMessage();
		}
		List<String> errorMessages = scheduler.getErrorMessages();
		if (!errorMessages.isEmpty()) {
			rc = 3;
			console.println("Some errors occurred:");
			for (String msg : errorMessages) {
				console.println("*) " + msg);
			}
		}
		console.println("finished");
		log.exiting(DefRuntime.class.getName(), "execute", rc);
		return rc;
	}

}
