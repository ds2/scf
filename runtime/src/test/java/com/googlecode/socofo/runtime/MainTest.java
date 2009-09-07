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
package com.googlecode.socofo.runtime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

import org.junit.Before;
import org.junit.Test;

/**
 * @author kaeto23
 * 
 */
public class MainTest {
	private Main to = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		to = new Main();
	}

	/**
	 * Test method for
	 * {@link com.googlecode.socofo.runtime.Main#execute(java.lang.String[])}.
	 */
	@Test
	public final void testExecute() {
		to.execute(null);
		File formatFile = new File("src/test/resources/rules.xml");
		URI fileUri = formatFile.toURI();
		try {
			System.out.println("fileUri is " + fileUri.toURL().toString());
			to.execute("--rulesUrl=" + fileUri.toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public final void testHelp() {
		assertTrue(to.wantsHelp(null));
		assertTrue(to.wantsHelp(""));
		assertTrue(to.wantsHelp("help"));
		assertFalse(to.wantsHelp("test"));
	}

}
