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
package com.googlecode.socofo.core.impl;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.googlecode.socofo.core.api.Scheduler;
import com.googlecode.socofo.core.api.SourceTypes;
import com.googlecode.socofo.core.api.TranslationJob;

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
    public final void testCreateLocalJobs() {
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
    public final void testSetRules() {
        to.setRules(null);
    }
    
}
