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
package com.googlecode.socofo.core.impl;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.googlecode.socofo.common.modules.CommonsInjectionPlan;
import com.googlecode.socofo.core.api.FileDestination;
import com.googlecode.socofo.core.api.FileRoot;
import com.googlecode.socofo.core.api.Scheduler;
import com.googlecode.socofo.core.api.SourceTypeDetector;
import com.googlecode.socofo.core.api.SourceTypes;
import com.googlecode.socofo.core.api.TranslationJob;
import com.googlecode.socofo.core.exceptions.LoadingException;
import com.googlecode.socofo.core.impl.io.SourceFileFilter;
import com.googlecode.socofo.core.impl.modules.CoreInjectionPlan;
import com.googlecode.socofo.rules.api.RuleSet;
import com.googlecode.socofo.rules.api.RulesLoader;
import com.googlecode.socofo.rules.modules.RulesInjectionPlan;

/**
 * The scheduler impl.
 * 
 * @author kaeto23
 * 
 */
@Singleton
public class SchedulerImpl implements Scheduler {
	private static final Logger log = Logger.getLogger(SchedulerImpl.class
			.getName());
	private List<TranslationJob> jobs = null;
	private ThreadGroup threadGrp = null;
	@Inject
	SourceTypeDetector localDetector = null;
	@Inject
	private Injector ij = null;
	@Inject
	private RulesLoader rulesLoader = null;
	private RuleSet ruleSet = null;
	private List<String> errorMsgs;

	/**
	 * 
	 */
	public SchedulerImpl() {
		jobs = new ArrayList<TranslationJob>();
		threadGrp = new ThreadGroup("SchedulerTranslationJobs");
		errorMsgs = new ArrayList<String>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.googlecode.socofo.core.api.Scheduler#addJobs(java.util.List)
	 */
	@Override
	public void addJobs(List<TranslationJob> jobs) {
		if (jobs == null) {
			log.warning("No jobs given!");
			return;
		}
		this.jobs.addAll(jobs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.socofo.core.api.Scheduler#addWaiterThreads(java.lang.Thread
	 * )
	 */
	@Override
	public void addWaiterThreads(Thread currentThread) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.socofo.core.api.Scheduler#createLocalJobs(java.io.File,
	 * java.io.File, com.googlecode.socofo.core.api.SourceTypes[])
	 */
	@Override
	public List<TranslationJob> createLocalJobs(File baseDir, File targetDir,
			SourceTypes... types) {
		log.entering(SchedulerImpl.class.getName(), "createLocalJobs",
				new Object[] { baseDir, targetDir, types });
		List<TranslationJob> rc = new ArrayList<TranslationJob>();
		if (baseDir == null) {
			log.warning("No base directory given!");
			return rc;
		}
		if (targetDir == null) {
			log.info("Overwriting local files!");
			targetDir = baseDir;
		}
		if (types == null || types.length <= 0) {
			log.warning("No types given!");
			return rc;
		}
		if (ruleSet == null) {
			log.warning("No rules given!");
			return rc;
		}
		List<File> sourceFiles = getFiles(baseDir, types);
		log.finer("Got source files, counting " + sourceFiles.size());
		for (File sourceFile : sourceFiles) {
			log.finest("Got this source file: " + sourceFile.getAbsolutePath());
			TranslationJob job = getInjector()
					.getInstance(TranslationJob.class);
			FileRoot fr = ij.getInstance(FileRoot.class);
			try {
				fr.setFile(sourceFile);
				job.setSource(fr);
				FileDestination fd = ij.getInstance(FileDestination.class);
				fd.setFile(fd.parseDestination(baseDir, targetDir, sourceFile));
				job.setDestination(fd);
				job.setRule(ruleSet);
				rc.add(job);
			} catch (LoadingException e) {
				log.throwing(SchedulerImpl.class.getName(), "createLocalJobs",
						e);
			}

		}
		log
				.exiting(SchedulerImpl.class.getName(), "createLocalJobs", rc
						.size());
		return rc;
	}

	private synchronized Injector getInjector() {
		if (ij == null) {
			ij = Guice.createInjector(new CoreInjectionPlan(),
					new RulesInjectionPlan(), new CommonsInjectionPlan());
		}
		return ij;
	}

	/**
	 * Returns all allowed files starting from the current base directory.
	 * 
	 * @param baseDir
	 *            the base directory
	 * @param types
	 *            the list of allowed types
	 * @return a list of found files matching the source type criteria
	 */
	protected List<File> getFiles(final File baseDir,
			final SourceTypes... types) {
		log.entering(SchedulerImpl.class.getName(), "getFiles", new Object[] {
				baseDir, types });
		final List<File> rc = new ArrayList<File>();
		if (baseDir == null) {
			log.warning("No base directory given, returning empty list!");
			return rc;
		}
		if (types == null || types.length <= 0) {
			log.warning("No types given, returning empty list!");
			return rc;
		}
		final List<SourceTypes> allowedTypes = new ArrayList<SourceTypes>();
		for (SourceTypes type : types) {
			allowedTypes.add(type);
		}
		final FileFilter fileFilter = new SourceFileFilter(localDetector,
				allowedTypes);
		final File[] foundFiles = baseDir.listFiles(fileFilter);
		rc.addAll(scanFilesAndDirectories(foundFiles, types));
		log.exiting(SchedulerImpl.class.getName(), "getFiles", rc.size());
		return rc;
	}

	/**
	 * Scans the given list of files for the given source type and returns them.
	 * 
	 * @param foundFiles
	 *            the found files. If one of them is a directory, this method
	 *            checks the content of the directory.
	 * @param types
	 *            the list of allowed types.
	 * @return all source files matching the given source types
	 */
	private List<File> scanFilesAndDirectories(File[] foundFiles,
			SourceTypes... types) {
		List<File> rc = new ArrayList<File>();
		if (foundFiles != null && foundFiles.length > 0) {
			for (File foundFile : foundFiles) {
				log.finest("found " + foundFile);
				if (foundFile.isDirectory() && !rc.contains(foundFile)) {
					log.finer("Calling scan of child directory " + foundFile);
					rc.addAll(getFiles(foundFile, types));
					continue;
				}
				rc.add(foundFile);
			}
		}
		return rc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.googlecode.socofo.core.api.Scheduler#getErrorMessages()
	 */
	@Override
	public List<String> getErrorMessages() {
		return errorMsgs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.googlecode.socofo.core.api.Scheduler#setRules(java.net.URL)
	 */
	@Override
	public void setRules(URL formatterXml) {
		log.entering(SchedulerImpl.class.getName(), "setRules", formatterXml);
		if (formatterXml == null) {
			log.warning("No url given!");
			return;
		}
		ruleSet = rulesLoader.loadRulesFromUrl(formatterXml);
		log.exiting(SchedulerImpl.class.getName(), "setRules", ruleSet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.googlecode.socofo.core.api.Scheduler#startScheduler()
	 */
	@Override
	public void startScheduler() {
		log.entering(SchedulerImpl.class.getName(), "startScheduler");
		for (TranslationJob job : jobs) {
			log.finest("Starting job " + job);
			Thread t = new Thread(threadGrp, job);
			t.start();
		}
		log.exiting(SchedulerImpl.class.getName(), "startScheduler");
	}

	@Override
	public int getActiveJobsCount() {
		return threadGrp.activeCount();
	}

	protected void setTestDetector(SourceTypeDetector instance) {
		localDetector = instance;
	}

	public void setTestRulesLoader(RulesLoader instance) {
		// TODO Auto-generated method stub
		rulesLoader = instance;
	}

}
