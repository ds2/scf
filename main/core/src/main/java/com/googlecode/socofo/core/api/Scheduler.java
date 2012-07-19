/*
 * SoCoFo - Another source code formatter
 * Copyright (C) 2012  Dirk Strauss
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
package com.googlecode.socofo.core.api;

import java.io.File;
import java.net.URL;
import java.util.List;

import com.googlecode.socofo.rules.api.v1.RuleSet;

/**
 * The scheduler. Implementations of this class are scheduling and
 * starting/stopping translation jobs.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface Scheduler {
	/**
	 * Sets and loads the rules for the transformation.
	 * 
	 * @param formatterXml
	 *            the url to the rules
	 */
	public void setRules(URL formatterXml);

	/**
	 * Starts the scheduler. The scheduler will create a separate thread group
	 * and executes the jobs in there.
	 */
	public void startScheduler();

	/**
	 * Adds a thread that is notified when the transformation ends.
	 * 
	 * @param currentThread
	 *            the thread to add
	 */
	public void addWaiterThreads(Thread currentThread);

	/**
	 * Returns a list of error messages from some failed transformations.
	 * 
	 * @return a list of error messages
	 */
	public List<String> getErrorMessages();

	/**
	 * Creates a list of jobs from the local filesystem.
	 * 
	 * @param baseDir
	 *            the base directory
	 * @param targetDir
	 *            the target directory
	 * @param types
	 *            the types to scan for
	 * @return a list of jobs
	 */
	public List<TranslationJob> createLocalJobs(File baseDir, File targetDir,
			List<SourceTypes> types);

	/**
	 * Adds additional jobs to the scheduler.
	 * 
	 * @param jobs
	 *            some more jobs to perform
	 */
	public void addJobs(List<TranslationJob> jobs);

	/**
	 * Returns the POSSIBLE count of running translation jobs.
	 * 
	 * @return the possible count of running jobs. Please be aware that this
	 *         count may NOT reflect the real count of running jobs.
	 */
	public int getActiveJobsCount();

	/**
	 * Returns the ruleset for this scheduler.
	 * 
	 * @return the ruleset
	 */
	public RuleSet getRuleset();
}
