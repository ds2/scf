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
import com.googlecode.socofo.core.api.SourceTypes;
import com.googlecode.socofo.core.api.SourcefileScanner;
import com.googlecode.socofo.core.api.TranslationJob;
import com.googlecode.socofo.core.exceptions.LoadingException;
import com.googlecode.socofo.core.exceptions.TranslationException;
import com.googlecode.socofo.core.impl.modules.CoreInjectionPlan;
import com.googlecode.socofo.rules.api.RuleSet;
import com.googlecode.socofo.rules.api.RulesLoader;
import com.googlecode.socofo.rules.modules.RulesInjectionPlan;

/**
 * The scheduler impl.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
@Singleton
public class SchedulerImpl implements Scheduler {
	/**
	 * a logger.
	 */
	private static final Logger log = Logger.getLogger(SchedulerImpl.class
			.getName());
	/**
	 * A list of translation jobs to do.
	 */
	private List<TranslationJob> jobs = null;
	/**
	 * The thread group to add the translation jobs to.
	 */
	private ThreadGroup threadGrp = null;

	/**
	 * The injector for generating on-demand instances of translation jobs.
	 */
	@Inject
	private Injector ij = null;
	/**
	 * The rules loader.
	 */
	@Inject
	private RulesLoader rulesLoader = null;
	/**
	 * The rule set.
	 */
	private RuleSet ruleSet = null;
	/**
	 * A list of error messages.
	 */
	private List<String> errorMsgs;
	/**
	 * The scanner for source files.
	 */
	@Inject
	private SourcefileScanner scanner = null;

	/**
	 * Inits the scheduler.
	 */
	public SchedulerImpl() {
		jobs = new ArrayList<TranslationJob>();
		threadGrp = new ThreadGroup("SchedulerTranslationJobs");
		errorMsgs = new ArrayList<String>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addJobs(final List<TranslationJob> j) {
		if (j == null) {
			log.warning("No jobs given!");
			return;
		}
		this.jobs.addAll(j);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addWaiterThreads(final Thread currentThread) {
		// nothing to do yet
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TranslationJob> createLocalJobs(final File baseDir,
			final File td, final List<SourceTypes> types) {
		log.entering(SchedulerImpl.class.getName(), "createLocalJobs",
				new Object[] { baseDir, td, types });
		final List<TranslationJob> rc = new ArrayList<TranslationJob>();
		if (baseDir == null) {
			log.warning("No base directory given!");
			return rc;
		}
		File targetDir = td;
		if (targetDir == null) {
			log.info("Overwriting local files!");
			targetDir = baseDir;
		}
		if (types == null || types.size() <= 0) {
			log.warning("No types given!");
			return rc;
		}
		if (ruleSet == null) {
			log.warning("No rules given!");
			return rc;
		}
		final List<File> sourceFiles = scanner.scan(baseDir, types);
		log.finer("Got source files, counting " + sourceFiles.size());
		for (File sourceFile : sourceFiles) {
			log.finest("Got this source file: " + sourceFile.getAbsolutePath());
			final TranslationJob job = getInjector().getInstance(
					TranslationJob.class);
			final FileRoot fr = ij.getInstance(FileRoot.class);
			try {
				fr.setFile(sourceFile);
				job.setSource(fr);
				final FileDestination fd = ij
						.getInstance(FileDestination.class);
				fd.setFile(fd.parseDestination(baseDir, targetDir, sourceFile));
				job.setDestination(fd);
				job.setRule(ruleSet);
				rc.add(job);
			} catch (final LoadingException e) {
				log.throwing(SchedulerImpl.class.getName(), "createLocalJobs",
						e);
			}

		}
		log
				.exiting(SchedulerImpl.class.getName(), "createLocalJobs", rc
						.size());
		return rc;
	}

	/**
	 * Returns and loads the injector.
	 * 
	 * @return the injector
	 */
	private synchronized Injector getInjector() {
		if (ij == null) {
			ij = Guice.createInjector(new CoreInjectionPlan(),
					new RulesInjectionPlan(), new CommonsInjectionPlan());
		}
		return ij;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getErrorMessages() {
		for (TranslationJob job : jobs) {
			final List<TranslationException> translationErrors = job
					.getErrors();
			for (TranslationException e : translationErrors) {
				log.throwing(SchedulerImpl.class.getName(), "getErrorMessages",
						e);
				errorMsgs.add(e.getLocalizedMessage());
			}
		}
		return errorMsgs;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRules(final URL formatterXml) {
		log.entering(SchedulerImpl.class.getName(), "setRules", formatterXml);
		if (formatterXml == null) {
			log.warning("No url given!");
			return;
		}
		ruleSet = rulesLoader.loadRulesFromUrl(formatterXml);
		log.exiting(SchedulerImpl.class.getName(), "setRules", ruleSet);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void startScheduler() {
		log.entering(SchedulerImpl.class.getName(), "startScheduler");
		for (TranslationJob job : jobs) {
			log.finest("Starting job " + job);
			final Thread t = new Thread(threadGrp, job);
			t.start();
		}
		log.finest("Active threads now: " + threadGrp.activeCount());
		log.exiting(SchedulerImpl.class.getName(), "startScheduler");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getActiveJobsCount() {
		return threadGrp.activeCount();
	}

	/**
	 * Sets the test rules loader.
	 * 
	 * @param instance
	 *            the rules loader used for tests
	 */
	public void setTestRulesLoader(final RulesLoader instance) {
		rulesLoader = instance;
	}

	/**
	 * Sets the test source file scanner.
	 * 
	 * @param instance
	 *            the impl for testing purpose
	 */
	public void setTestSourcefileScanner(SourcefileScanner instance) {
		scanner = instance;
	}

}
