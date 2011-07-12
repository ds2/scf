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

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.socofo.core.api.FileDestination;
import com.googlecode.socofo.core.api.FileRoot;
import com.googlecode.socofo.core.api.Scheduler;
import com.googlecode.socofo.core.api.SourceTypes;
import com.googlecode.socofo.core.api.SourcefileScanner;
import com.googlecode.socofo.core.api.TranslationJob;
import com.googlecode.socofo.core.exceptions.LoadingException;
import com.googlecode.socofo.core.exceptions.TranslationException;
import com.googlecode.socofo.rules.api.v1.RuleSet;
import com.googlecode.socofo.rules.api.v1.RulesLoader;

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
    private static final Logger log = LoggerFactory
        .getLogger(SchedulerImpl.class);
    /**
     * A list of translation jobs to do.
     */
    private List<TranslationJob> jobs = null;
    /**
     * The thread group to add the translation jobs to.
     */
    private ThreadGroup threadGrp = null;
    
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
     * A provider for translation jobs.
     */
    @Inject
    private Provider<TranslationJob> translationJob;
    /**
     * A provider for file roots.
     */
    @Inject
    private Provider<FileRoot> fileProv;
    /**
     * A provider for file destinations.
     */
    @Inject
    private Provider<FileDestination> destProv;
    
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
            log.warn("No jobs given!");
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
        log.debug("entering: {} {} {}", new Object[] { baseDir, td, types });
        final List<TranslationJob> rc = new ArrayList<TranslationJob>();
        if (baseDir == null) {
            log.warn("No base directory given!");
            return rc;
        }
        File targetDir = td;
        if (targetDir == null) {
            log.info("Overwriting local files!");
            targetDir = baseDir;
        }
        if (types == null || types.size() <= 0) {
            log.warn("No types given!");
            return rc;
        }
        if (ruleSet == null) {
            log.warn("No rules given!");
            return rc;
        }
        final List<File> sourceFiles = scanner.scan(baseDir, types);
        log.debug("Got source files, counting {}", sourceFiles.size());
        for (File sourceFile : sourceFiles) {
            log.debug("Got this source file: {}", sourceFile.getAbsolutePath());
            final TranslationJob job = translationJob.get();
            final FileRoot fr = fileProv.get();
            try {
                fr.setFile(sourceFile);
                job.setSource(fr);
                final FileDestination fd = destProv.get();
                fd.setFile(fd.parseDestination(baseDir, targetDir, sourceFile));
                job.setDestination(fd);
                job.setRule(ruleSet);
                rc.add(job);
            } catch (final LoadingException e) {
                log.debug("Loader error", e);
            }
            
        }
        log.debug("exiting: {}", rc.size());
        return rc;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getErrorMessages() {
        for (TranslationJob job : jobs) {
            final List<TranslationException> translationErrors =
                job.getErrors();
            for (TranslationException e : translationErrors) {
                log.debug("Translation error", e);
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
        log.debug("entering: {}", formatterXml);
        if (formatterXml == null) {
            log.warn("No url given!");
            return;
        }
        ruleSet = rulesLoader.loadRulesFromUrl(formatterXml);
        if (ruleSet != null) {
            log.info("Rules loaded from " + formatterXml + ", successful.");
        }
        log.debug("exiting {}", ruleSet);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void startScheduler() {
        log.debug("entering");
        for (TranslationJob job : jobs) {
            log.debug("Starting job {}", job);
            final Thread t = new Thread(threadGrp, job);
            t.start();
        }
        log.debug("Active threads now: {}", threadGrp.activeCount());
        log.debug("exiting");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getActiveJobsCount() {
        return threadGrp.activeCount();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public RuleSet getRuleset() {
        return ruleSet;
    }
    
}
