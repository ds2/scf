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
import com.googlecode.socofo.core.exceptions.LoadingException;
import org.testng.annotations.*;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;
import java.util.*;

import static org.testng.Assert.*;

/**
 * The scheduler impl tests.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
@Guice(modules = { TestInjectionPlan.class })
public class SchedulerImplTest {
    /**
     * The scheduler.
     */
    @Inject
    private Scheduler to = null;
    /**
     * The url to the rules.
     */
    private URL rulesUrl = null;
    
    /**
     * Actions to init the test class.
     * 
     * @throws Exception
     *             if an error occurred.
     */
    @BeforeClass
    public void setUp() throws Exception {
        final File rulesFile = new File("src/test/resources/rules.xml");
        rulesUrl = rulesFile.toURI().toURL();
        assertNotNull(rulesUrl);
    }
    
    /**
     * Test method for
     * {@link com.googlecode.socofo.core.impl.SchedulerImpl#addJobs(java.util.List)}
     * .
     */
    @Test
    public final void testAddJobs() {
        to.addJobs(null);
    }
    
    /**
     * Test for creating a set of jobs.
     */
    @Test
    public final void testCreateLocalJobs() throws LoadingException {
        final File baseDir = new File("src");
        final File targetDir = new File("target/transformed");
        to.setRules(rulesUrl);
        final List<TranslationJob> jobs =
            to.createLocalJobs(baseDir, targetDir,
                Arrays.asList(SourceTypes.XML));
        assertNotNull(jobs);
        assertTrue(jobs.size() > 0);
    }
    
    /**
     * Test method for
     * {@link com.googlecode.socofo.core.impl.SchedulerImpl#setRules(java.net.URL)}
     * .
     */
    @Test
    public final void testSetRules() throws LoadingException {
        to.setRules(null);
    }
    
}
