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
package ds2.enterspace.core.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author kaeto23
 * 
 */
public class LineHandlerImplTest {
	/**
	 * The test object
	 */
	private LineHandlerImpl to = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		to = new LineHandlerImpl();
	}

	/**
	 * Test method for
	 * {@link ds2.enterspace.core.impl.LineHandlerImpl#breakContent(int, java.lang.String, int, ds2.enterspace.rules.api.BreakFormat)}
	 * .
	 */
	@Test
	public final void testBreakContent() {
		// assertEquals(Arrays.asList(""), to.breakContent(10, null, 0, null));
	}

	/**
	 * Test method for
	 * {@link ds2.enterspace.core.impl.LineHandlerImpl#calculateLineWidth(ds2.enterspace.rules.api.CommonAttributes, int)}
	 * .
	 */
	@Test
	public final void testCalculateLineWidth() {
		assertEquals(-1, to.calculateLineWidth(-1, 0));
		assertEquals(-1, to.calculateLineWidth(0, 0));
		assertEquals(-1, to.calculateLineWidth(0, 2));
		assertEquals(10, to.calculateLineWidth(10, 0));
		assertEquals(7, to.calculateLineWidth(10, 3));
		assertEquals(0, to.calculateLineWidth(10, 10));
		assertEquals(0, to.calculateLineWidth(10, 20));
	}

	/**
	 * Test method for
	 * {@link ds2.enterspace.core.impl.LineHandlerImpl#cleanComment(java.lang.String)}
	 * .
	 */
	@Test
	public final void testCleanComment() {
		assertEquals("", to.cleanComment(null));
		assertEquals("", to.cleanComment("*"));
		assertEquals("word", to.cleanComment("word"));
		assertEquals("word\nword2", to.cleanComment("word\nword2"));
		assertEquals("word\nword2", to.cleanComment("word\n* word2"));
		assertEquals("word\nword2", to.cleanComment("word\n    * word2"));
		assertEquals("word\nword2", to.cleanComment("word\n  \t  * word2"));
	}

}
