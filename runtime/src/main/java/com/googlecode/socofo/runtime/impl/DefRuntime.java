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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.socofo.common.api.MainRuntime;
import com.googlecode.socofo.core.api.Scheduler;
import com.googlecode.socofo.core.api.SourceTypes;
import com.googlecode.socofo.core.api.TranslationJob;

/**
 * The default implementation of the runtime.
 * 
 * @author Dirk Strauss
 * @version 1.0
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
	/**
	 * The scheduler.
	 */
	@Inject
	private Scheduler scheduler = null;
	/**
	 * The base directory that contains the source code files.
	 */
	private File baseDir = null;
	/**
	 * The target directory to write the transformed source code to.
	 */
	private File targetDir = null;

	/**
	 * Flag to indicate that the help screen should be displayed.
	 */
	private boolean showHelp = false;
	/**
	 * The url to the transformation rules.
	 */
	private URL rulesUrl;

	private List<SourceTypes> types = null;

	/**
	 * Inits the runtime.
	 */
	public DefRuntime() {
		final Console c = System.console();
		if (c == null) {
			console = new PrintWriter(System.out);
		} else {
			console = c.writer();
		}
		types = new ArrayList<SourceTypes>();
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
		console.println(PARAM_BASEDIR
				+ " = the base directory to scan for source files");
		console
				.println(PARAM_TARGETDIR
						+ " = the directory to write the results to; default is the base directory");
		console
				.println(PARAM_TYPES
						+ " = a list of types to transform; Supported types currently: xml");
	}

	/**
	 * Sets the scheduler for the tests.
	 * 
	 * @param instance
	 *            an instance of a scheduler
	 */
	protected void setTestScheduler(final Scheduler instance) {
		scheduler = instance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int execute() {
		int rc = 0;
		if (showHelp) {
			printHelp();
			return RC_SHOWHELP;
		}
		if (scheduler == null) {
			log.severe("No scheduler set!");
			return RC_NOSCHEDULER;
		}
		if (rulesUrl == null) {
			log.severe("No rulesUrl given!");
			return RC_NORULES;
		}
		if (types.size() <= 0) {
			log.severe("No types given to scan for!");
			return RC_NOTYPES;
		}
		final Thread currentThread = Thread.currentThread();
		scheduler.addWaiterThreads(currentThread);
		List<TranslationJob> jobs = null;
		scheduler.setRules(rulesUrl);
		baseDir = getBaseDirectory();
		log.info("Using base directory " + baseDir);
		log.info("Using target directory " + targetDir);
		jobs = scheduler.createLocalJobs(baseDir, targetDir, types);
		if (jobs.size() <= 0) {
			log.warning("No source files found!");
			return RC_NOSOURCES;
		}
		scheduler.addJobs(jobs);
		log.finer("Starting scheduler");
		scheduler.startScheduler();
		while (scheduler.getActiveJobsCount() > 0) {
			try {
				Thread.sleep(250);
			} catch (final InterruptedException e) {
				log.throwing(DefRuntime.class.getName(), "execute", e);
			}
		}
		final List<String> errorMessages = scheduler.getErrorMessages();
		if (!errorMessages.isEmpty()) {
			rc = RC_TRANSFORM;
			console.println("Some errors occurred:");
			for (String msg : errorMessages) {
				console.println("*) " + msg);
			}
		}
		console.println("finished");
		log.exiting(DefRuntime.class.getName(), "execute", rc);
		return rc;
	}

	/**
	 * Convenient method for parseParams and execute, in one method.
	 * 
	 * @param args
	 *            the parameters.
	 * @return the result of the execution.
	 */
	public int execute(final String... args) {
		parseParams(args);
		return execute();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public File getBaseDirectory() {
		if (baseDir == null) {
			baseDir = new File("");
		}
		return baseDir;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public File getTargetDirectory() {
		return targetDir;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void parseParams(final String... args) {
		log.entering(DefRuntime.class.getName(), "parseParams", args);
		if (args != null && args.length > 0) {
			for (String arg : args) {
				log.finest("current arg is " + arg);
				if (arg == null || arg.length() <= 0) {
					continue;
				}
				checkParam(arg);
			}
		} else {
			showHelp = true;
		}
		log.exiting(DefRuntime.class.getName(), "parseParams");
	}

	/**
	 * Checks a given param if it is a known parameter. This method fills the
	 * internal fields.
	 * 
	 * @param arg
	 *            the parameter to check
	 */
	private void checkParam(final String arg) {
		if (arg.startsWith(PARAM_BASEDIR) && baseDir == null) {
			final String argSeq = arg.substring(PARAM_BASEDIR.length() + 1);
			baseDir = new File(argSeq);
			if (!baseDir.exists()) {
				log.warning("Directory " + baseDir + " does not exist!");
			}
		} else if (arg.startsWith(PARAM_RULESURL) && rulesUrl == null) {
			try {
				rulesUrl = new URL(arg.substring(PARAM_RULESURL.length() + 1));
			} catch (final MalformedURLException e) {
				log.throwing(DefRuntime.class.getName(), "parseParams", e);
			}
		} else if (arg.startsWith(PARAM_TARGETDIR) && targetDir == null) {
			final String argSeq = arg.substring(PARAM_TARGETDIR.length() + 1);
			targetDir = new File(argSeq);
		} else if (arg.startsWith(PARAM_HELP)) {
			showHelp = true;
		} else if (arg.startsWith(PARAM_TYPES)) {
			final String argSeq = arg.substring(PARAM_TYPES.length() + 1);
			final String[] typesIds = argSeq.split(",");
			for (String typeId : typesIds) {
				if (typeId != null && typeId.equalsIgnoreCase("xml")) {
					types.add(SourceTypes.XML);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean showHelpScreen() {
		return showHelp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetSettings() {
		baseDir = null;
		targetDir = null;
		showHelp = false;
		rulesUrl = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public URL getRulesUrl() {
		return rulesUrl;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setBaseDirectory(final File srcDir) {
		baseDir = srcDir;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTargetDirectory(final File t) {
		this.targetDir = t;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRules(final URL r) {
		this.rulesUrl = r;
	}

	public List<SourceTypes> getTypes() {
		return types;
	}

}
