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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.socofo.common.modules.CommonsInjectionPlan;
import com.googlecode.socofo.core.api.SourceTypeDetector;
import com.googlecode.socofo.core.api.SourceTypes;
import com.googlecode.socofo.core.api.TranslationJob;
import com.googlecode.socofo.core.impl.modules.CoreInjectionPlan;
import com.googlecode.socofo.rules.api.RulesLoader;
import com.googlecode.socofo.rules.modules.RulesInjectionPlan;

/**
 * @author kaeto23
 * 
 */
public class SchedulerImplTest {
	private SchedulerImpl to = null;
	private URL rulesUrl = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		to = new SchedulerImpl();
		Injector ij = Guice.createInjector(new CoreInjectionPlan(),
				new CommonsInjectionPlan(), new RulesInjectionPlan());
		to.setTestDetector(ij.getInstance(SourceTypeDetector.class));
		to.setTestRulesLoader(ij.getInstance(RulesLoader.class));
		File rulesFile = new File("src/test/resources/rules.xml");
		rulesUrl = rulesFile.toURI().toURL();
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
	 * Test method for
	 * {@link com.googlecode.socofo.core.impl.SchedulerImpl#createLocalJobs(java.io.File, java.io.File, com.googlecode.socofo.core.api.SourceTypes[])}
	 * .
	 */
	@Test
	public final void testCreateLocalJobs() {
		File baseDir = new File("src");
		File targetDir = new File("target/transformed");
		to.setRules(rulesUrl);
		List<TranslationJob> jobs = to.createLocalJobs(baseDir, targetDir,
				SourceTypes.XML);
		assertNotNull(jobs);
		assertTrue(jobs.size() > 0);
	}

	/**
	 * Test method for
	 * {@link com.googlecode.socofo.core.impl.SchedulerImpl#getFiles(java.io.File, com.googlecode.socofo.core.api.SourceTypes[])}
	 * .
	 */
	@Test
	public final void testGetFiles() {
		File baseDir = new File(".");
		List<File> l = to.getFiles(baseDir, null);
		assertEquals(0, l.size());
		l = to.getFiles(baseDir, SourceTypes.XML);
		assertTrue(l.size() > 1);
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
