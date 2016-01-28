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
package com.googlecode.socofo.core.impl;

import com.googlecode.socofo.core.api.*;
import com.googlecode.socofo.core.exceptions.*;
import com.googlecode.socofo.rules.api.v1.*;
import org.slf4j.*;

import javax.inject.*;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;

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
    private static final Logger LOG = LoggerFactory.getLogger(SchedulerImpl.class);
    /**
     * A list of translation jobs to do.
     */
    private List<TranslationJob> jobs;
    /**
     * The thread group to add the translation jobs to.
     */
    private ThreadGroup threadGrp;
    
    /**
     * The rules loader.
     */
    @Inject
    private RulesLoader rulesLoader;
    /**
     * The rule set.
     */
    private RuleSet ruleSet;
    /**
     * A list of error messages.
     */
    private List<String> errorMsgs;
    /**
     * The scanner for source files.
     */
    @Inject
    private SourcefileScanner scanner;
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
            LOG.warn("No jobs given!");
            return;
        }
        jobs.addAll(j);
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
    public List<TranslationJob> createLocalJobs(final File baseDir, final File td, final List<SourceTypes> types) {
        LOG.debug("entering local job creator with base={}, target={} and types={}",
            new Object[] { baseDir, td, types });
        final List<TranslationJob> rc = new ArrayList<TranslationJob>();
        if (baseDir == null) {
            LOG.warn("No base directory given!");
            return rc;
        }
        File targetDir = td;
        if (targetDir == null) {
            LOG.info("Overwriting local files!");
            targetDir = baseDir;
        }
        if ((types == null) || (types.size() <= 0)) {
            LOG.warn("No types given!");
            return rc;
        }
        if (ruleSet == null) {
            LOG.warn("No rules given!");
            return rc;
        }
        final List<File> sourceFiles = scanner.scan(baseDir, types);
        LOG.debug("Got source files, counting {}", sourceFiles.size());
        for (File sourceFile : sourceFiles) {
            TranslationJob job=createLocalJob(sourceFile.toPath(), baseDir.toPath(), targetDir.toPath());
            if(job!=null){
                rc.add(job);
            }
        }
        LOG.debug("exiting: {}", rc.size());
        return rc;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getErrorMessages() {
        for (TranslationJob job : jobs) {
            final List<TranslationException> translationErrors = job.getErrors();
            for (TranslationException e : translationErrors) {
                LOG.debug("Translation error", e);
                errorMsgs.add(e.getLocalizedMessage());
            }
        }
        return errorMsgs;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setRules(final URL rulesUrl) throws LoadingException {
        LOG.debug("entering: {}", rulesUrl);
        if (rulesUrl == null) {
            LOG.warn("No url given!");
            return;
        }
        ruleSet = rulesLoader.loadRulesFromUrl(rulesUrl);
        if (ruleSet != null) {
            LOG.info("Rules loaded from " + rulesUrl + ", successful.");
            //check if valid
            if(ruleSet.getXmlFormatRules()==null){
                throw new LoadingException("The given rule set does not contain an xml ruleset!");
            }
        }
        LOG.debug("exiting {}", ruleSet);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void startScheduler() {
        LOG.debug("entering");
        for (TranslationJob job : jobs) {
            LOG.debug("Starting job {}", job);
            final Thread t = new Thread(threadGrp, job);
            t.start();
        }
        LOG.debug("Active threads now: {}", threadGrp.activeCount());
        LOG.debug("exiting");
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

    @Override
    public TranslationJob createLocalJob(Path sourceFile, Path baseDir, Path td) {
        if (baseDir == null) {
            LOG.warn("No base directory given!");
            return null;
        }
        Path targetDir = td;
        if (targetDir == null) {
            LOG.info("Overwriting local files!");
            targetDir = baseDir;
        }
        LOG.debug("Got this source file: {}", sourceFile);
        final TranslationJob job = translationJob.get();
        final FileRoot fr = fileProv.get();
        try {
            fr.loadFile(sourceFile.toFile(), "utf-8");
            job.setSource(fr);
            final FileDestination fd = destProv.get();
            fd.setFile(fd.parseDestination(baseDir.toFile(), targetDir.toFile(), sourceFile.toFile()));
            job.setDestination(fd);
            job.setRule(ruleSet);
            return job;
        } catch (final LoadingException e) {
            LOG.debug("Loader error", e);
        }
        return null;
    }

}
