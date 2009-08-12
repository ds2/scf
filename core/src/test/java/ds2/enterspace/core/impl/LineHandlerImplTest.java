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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.regex.Matcher;

import org.junit.Before;
import org.junit.Test;

import ds2.enterspace.rules.api.BreakFormat;

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
		to.setTabSize(4);
	}

	/**
	 * Test method for
	 * {@link ds2.enterspace.core.impl.LineHandlerImpl#breakContent(int, java.lang.String, int, ds2.enterspace.rules.api.BreakFormat)}
	 * .
	 */
	@Test
	public final void testBreakContent() {
		assertEqualList(to.breakContent(10, null, 0, null), "");
		assertEqualList(to.breakContent(10, "this is a test", 0,
				BreakFormat.BeautyBreak), "this is a ", "test");
		assertEqualList(to.breakContent(10, "this is a test", 5,
				BreakFormat.BeautyBreak), "this ", "is a test");
		assertEqualList(to.breakContent(5, "1234567890", 0,
				BreakFormat.BeautyBreak), "1234567890");
		assertEqualList(to.breakContent(5, "1234567890", 2,
				BreakFormat.BeautyBreak), "1234567890");
		assertEqualList(to.breakContent(5, "1234567890", 5,
				BreakFormat.BeautyBreak), "");
		assertEqualList(to.breakContent(5, "1234567890", 0,
				BreakFormat.BeautyForcedBreak), "12345", "67890");
		assertEqualList(to.breakContent(5, "1234567890", 2,
				BreakFormat.BeautyForcedBreak), "123", "45678", "90");
	}

	private void assertEqualList(List<String> l, String... elements) {
		if (l == null) {
			fail("List is null");
			return;
		}
		int index = 0;
		for (String item : l) {
			String mustItem = elements[index++];
			if (!mustItem.equals(item)) {
				fail("Item \"" + mustItem + "\" is not the same as \"" + item
						+ "\"");
				break;
			}
		}
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

	@Test
	public final void testWhitespacePatterns() {
		Matcher m = to.PATTERN_WS.matcher(" ");
		assertTrue(m.find());
		assertEquals(" ", m.group());

		m = to.PATTERN_WS.matcher("   ");
		assertTrue(m.find());
		assertEquals("   ", m.group());

		m = to.PATTERN_WS.matcher("\t");
		assertTrue(m.find());
		assertEquals("\t", m.group());
	}

	@Test
	public final void testWordPatterns() {
		Matcher m = to.wordPattern.matcher("a ");
		assertTrue(m.find());
		assertEquals("a ", m.group());

		m = to.wordPattern.matcher("a   ");
		assertTrue(m.find());
		assertEquals("a   ", m.group());

		m = to.wordPattern.matcher("a \t ");
		assertTrue(m.find());
		assertEquals("a \t ", m.group());

		m = to.wordPattern.matcher("abc \t ");
		assertTrue(m.find());
		assertEquals("abc \t ", m.group());

		m = to.wordPattern.matcher(" abc \t ");
		assertTrue(m.find());
		assertEquals("abc \t ", m.group());
	}

	@Test
	public final void testGetTokens() {
		assertEqualList(to.getTokens(null), "");

		assertEqualList(to.getTokens("this"), "this");

		assertEqualList(to.getTokens("this is "), "this ", "is ");

		assertEqualList(to.getTokens("this is a test"), "this ", "is ", "a ",
				"test");
	}

	@Test
	public final void testGetLineLength() {
		assertEquals(0, to.getLineLength(null));

		assertEquals(0, to.getLineLength(""));

		assertEquals(4, to.getLineLength("test"));

		assertEquals(6, to.getLineLength("  test"));

		assertEquals(8, to.getLineLength("\ttest"));
	}

}
