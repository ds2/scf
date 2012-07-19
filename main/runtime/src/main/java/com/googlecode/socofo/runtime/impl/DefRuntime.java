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
package com.googlecode.socofo.runtime.impl;

import java.io.Console;
import java.io.File;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger log = LoggerFactory.getLogger(DefRuntime.class);
    
    /**
     * The base directory that contains the source code files.
     */
    private File baseDir;
    /**
     * the console to write log information to.
     */
    private PrintWriter console;
    /**
     * The url to the transformation rules.
     */
    private URL rulesUrl;
    /**
     * The scheduler.
     */
    @Inject
    private Scheduler scheduler;
    
    /**
     * Flag to indicate that the help screen should be displayed.
     */
    private boolean showHelp;
    /**
     * Some source filters.
     */
    private List<String> sourceFilters;
    /**
     * The target directory to write the transformed source code to.
     */
    private File targetDir;
    /**
     * The list of source types to scan for.
     */
    private List<SourceTypes> types;
    
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
        sourceFilters = new ArrayList<String>();
    }
    
    @Override
    public final void applySourceFilters(final List<String> filters) {
        if (filters == null || filters.size() <= 0) {
            return;
        }
        sourceFilters.addAll(filters);
    }
    
    /**
     * Checks a given param if it is a known parameter. This method fills the
     * internal fields.
     * 
     * @param arg
     *            the parameter to check
     */
    private final void checkParam(final String arg) {
        if (arg.startsWith(PARAM_BASEDIR) && baseDir == null) {
            final String argSeq = arg.substring(PARAM_BASEDIR.length() + 1);
            baseDir = new File(argSeq);
            if (!baseDir.exists()) {
                log.warn("Directory {} does not exist!", baseDir);
            }
        } else if (arg.startsWith(PARAM_RULESURL) && rulesUrl == null) {
            try {
                rulesUrl = new URL(arg.substring(PARAM_RULESURL.length() + 1));
            } catch (final MalformedURLException e) {
                log.debug(e.getLocalizedMessage(), e);
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
    public final int execute() {
        int rc = 0;
        if (showHelp) {
            printHelp();
            return RC_SHOWHELP;
        }
        if (scheduler == null) {
            log.error("No scheduler set!");
            return RC_NOSCHEDULER;
        }
        if (rulesUrl == null) {
            log.error("No rulesUrl given!");
            return RC_NORULES;
        }
        if (types.size() <= 0) {
            log.error("No types given to scan for!");
            return RC_NOTYPES;
        }
        final Thread currentThread = Thread.currentThread();
        scheduler.addWaiterThreads(currentThread);
        List<TranslationJob> jobs = null;
        scheduler.setRules(rulesUrl);
        if (scheduler.getRuleset() == null) {
            return RC_NORULES;
        }
        baseDir = getBaseDirectory();
        log.info("Using base directory " + baseDir);
        log.info("Using target directory " + targetDir);
        jobs = scheduler.createLocalJobs(baseDir, targetDir, types);
        if (jobs.size() <= 0) {
            log.warn("No source files found!");
            return RC_NOSOURCES;
        }
        scheduler.addJobs(jobs);
        log.debug("Starting scheduler");
        scheduler.startScheduler();
        while (scheduler.getActiveJobsCount() > 0) {
            try {
                Thread.sleep(250);
            } catch (final InterruptedException e) {
                log.debug(
                    "Interrupted while sleeping, but this is not really critical",
                    e);
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
        log.debug("exiting: {}", rc);
        return rc;
    }
    
    /**
     * Convenient method for parseParams and execute, in one method.
     * 
     * @param args
     *            the parameters.
     * @return the result of the execution.
     */
    public final int execute(final String... args) {
        parseParams(args);
        return execute();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final File getBaseDirectory() {
        if (baseDir == null) {
            baseDir = new File("");
        }
        return baseDir;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final URL getRulesUrl() {
        return rulesUrl;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final File getTargetDirectory() {
        return targetDir;
    }
    
    /**
     * Returns the source types to work with.
     * 
     * @return the source types
     */
    public final List<SourceTypes> getTypes() {
        return types;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final void parseParams(final String... args) {
        log.debug("entering: {}", args);
        if (args != null && args.length > 0) {
            for (String arg : args) {
                log.debug("current arg is {}", arg);
                if (arg == null || arg.length() <= 0) {
                    continue;
                }
                checkParam(arg);
            }
        } else {
            showHelp = true;
        }
        log.debug("exiting");
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
            + PARAM_RULESURL + "=http://test.local/test/formatterRules.xml)");
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
     * {@inheritDoc}
     */
    @Override
    public final void resetSettings() {
        baseDir = null;
        targetDir = null;
        showHelp = false;
        rulesUrl = null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final void setBaseDirectory(final File srcDir) {
        baseDir = srcDir;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final void setRules(final URL r) {
        this.rulesUrl = r;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final void setTargetDirectory(final File t) {
        this.targetDir = t;
    }
    
    /**
     * Sets the scheduler for the tests.
     * 
     * @param instance
     *            an instance of a scheduler
     */
    protected final void setTestScheduler(final Scheduler instance) {
        scheduler = instance;
    }
    
    @Override
    public final void setTypes(final List<String> types) {
        if (types == null || types.size() <= 0) {
            return;
        }
        for (String t : types) {
            t = t.trim().toLowerCase();
            SourceTypes detectedType = SourceTypes.findByName(t);
            if (detectedType == null) {
                log.warn(
                    "Could not find a type with the name {}! Continue types scan.",
                    t);
                continue;
            }
            this.types.add(detectedType);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean showHelpScreen() {
        return showHelp;
    }
    
}
