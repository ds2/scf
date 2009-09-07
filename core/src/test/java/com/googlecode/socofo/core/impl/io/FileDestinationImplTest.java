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
package com.googlecode.socofo.core.impl.io;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.socofo.common.api.IOHelper;
import com.googlecode.socofo.common.modules.CommonsInjectionPlan;
import com.googlecode.socofo.core.impl.modules.CoreInjectionPlan;
import com.googlecode.socofo.rules.modules.RulesInjectionPlan;

/**
 * @author kaeto23
 * 
 */
public class FileDestinationImplTest {
	private FileDestinationImpl to = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		to = new FileDestinationImpl();
		Injector ij = Guice.createInjector(new CoreInjectionPlan(),
				new RulesInjectionPlan(), new CommonsInjectionPlan());
		to.setTestIohelper(ij.getInstance(IOHelper.class));
	}

	/**
	 * Test method for
	 * {@link com.googlecode.socofo.core.impl.io.FileDestinationImpl#writeContent(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public final void testWriteContent() {
		File targetFile = new File("target/delme.txt");
		targetFile.delete();
		targetFile.deleteOnExit();
		to.setFile(targetFile);
		to.writeContent(null, null);
		assertTrue(!targetFile.exists());
		to.writeContent("", null);
		assertTrue(targetFile.exists());
	}

	/**
	 * Test method for
	 * {@link com.googlecode.socofo.core.impl.io.FileDestinationImpl#setFile(java.io.File)}
	 * .
	 */
	@Test
	public final void testSetFile() {
		to.setFile(null);
		to.setFile(new File("target/pom_delme.xml"));
	}

	@Test
	public final void testParseDestination() {
		assertNull(to.parseDestination(null, null, null));
	}

	@Test
	public final void testParseDestination2() {
		assertNull(to.parseDestination2(null, null, null));
	}

	@Test
	public final void testParseDestination3() {
		assertNull(to.parseDestination2("backup", null, null));
		assertNull(to.parseDestination2("backup", null, ""));
	}

	@Test
	public final void testParseDestination4() {
		assertNull(to.parseDestination2("backup", null, "backup3/Test.java"));
	}

}
