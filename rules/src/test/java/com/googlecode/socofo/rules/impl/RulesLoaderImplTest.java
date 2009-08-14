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
package com.googlecode.socofo.rules.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.socofo.common.modules.CommonsInjectionPlan;
import com.googlecode.socofo.rules.api.RulesLoader;
import com.googlecode.socofo.rules.api.XmlFormatRules;
import com.googlecode.socofo.rules.modules.RulesInjectionPlan;


/**
 * @author kaeto23
 * 
 */
public class RulesLoaderImplTest {
	/**
	 * the test object
	 */
	private RulesLoader to = null;

	/**
	 * Sets up a test run
	 * 
	 * @throws java.lang.Exception
	 *             if an error occurred
	 */
	@Before
	public void setUp() throws Exception {
		Injector oj = Guice.createInjector(new CommonsInjectionPlan(),
				new RulesInjectionPlan());
		to = oj.getInstance(RulesLoader.class);
	}

	/**
	 * Test method for
	 * {@link com.googlecode.socofo.rules.impl.RulesLoaderImpl#loadFormatRules(java.io.InputStream)}
	 * .
	 */
	@Test
	public final void testLoadFormatRules() {
		assertNotNull(to);
		assertNull(to.loadFormatRules(null));
		InputStream is = getClass().getResourceAsStream("/xmllayout.xml");
		assertNotNull(is);
		XmlFormatRules rules = to.loadFormatRules(is);
		assertNotNull(rules);
	}

}
