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

import java.util.List;
import java.util.regex.Matcher;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.googlecode.socofo.rules.api.v1.BreakFormat;

/**
 * The linehandler tests.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class LineHandlerImplTest {
    /**
     * The test object.
     */
    private LineHandlerImpl to = null;
    
    /**
     * actions on setup.
     * 
     * @throws java.lang.Exception
     *             if an error occurred
     */
    @BeforeClass
    public void setUp() throws Exception {
        to = new LineHandlerImpl();
        to.setTabSize(4);
    }
    
    /**
     * Test method for
     * {@link com.googlecode.socofo.core.impl.LineHandlerImpl#breakContent(int, java.lang.String, int, com.googlecode.socofo.rules.api.v1.BreakFormat)}
     * .
     */
    @Test
    public final void testBreakContent() {
        assertEqualList(to.breakContent(10, null, 0, null), "");
        assertEqualList(
            to.breakContent(10, "this is a test", 0, BreakFormat.BeautyBreak),
            "this is a ", "test");
        assertEqualList(
            to.breakContent(10, "this is a test", 5, BreakFormat.BeautyBreak),
            "this", "is a test");
        assertEqualList(
            to.breakContent(5, "1234567890", 0, BreakFormat.BeautyBreak),
            "1234567890");
        assertEqualList(
            to.breakContent(5, "1234567890", 2, BreakFormat.BeautyBreak),
            "1234567890");
        assertEqualList(
            to.breakContent(5, "1234567890", 5, BreakFormat.BeautyBreak), "");
        assertEqualList(
            to.breakContent(5, "1234567890", 0, BreakFormat.BeautyForcedBreak),
            "12345", "67890");
        assertEqualList(
            to.breakContent(5, "1234567890", 2, BreakFormat.BeautyForcedBreak),
            "123", "45678", "90");
    }
    
    private void assertEqualList(List<String> l, String... elements) {
        if (l == null) {
            Assert.fail("List is null");
            return;
        }
        int index = 0;
        for (String item : l) {
            String mustItem = elements[index++];
            if (!mustItem.equals(item)) {
                Assert.fail("Item \"" + mustItem + "\" is not the same as \""
                    + item + "\"");
                break;
            }
        }
    }
    
    @Test
    public final void testCalculateLineWidth() {
        Assert.assertEquals(to.calculateContentLineWidth(-1, 0), -1);
        Assert.assertEquals(to.calculateContentLineWidth(0, 0), -1);
        Assert.assertEquals(to.calculateContentLineWidth(0, 2), -1);
        Assert.assertEquals(to.calculateContentLineWidth(10, 0), 10);
        Assert.assertEquals(to.calculateContentLineWidth(10, 3), 7);
        Assert.assertEquals(to.calculateContentLineWidth(10, 10), 0);
        Assert.assertEquals(to.calculateContentLineWidth(10, 20), 0);
    }
    
    /**
     * Test method for
     * {@link com.googlecode.socofo.core.impl.LineHandlerImpl#cleanComment(java.lang.String)}
     * .
     */
    @Test
    public final void testCleanComment() {
        Assert.assertEquals("", to.cleanComment(null));
        Assert.assertEquals("", to.cleanComment("*"));
        Assert.assertEquals("word", to.cleanComment("word"));
        Assert.assertEquals("word\nword2", to.cleanComment("word\nword2"));
        Assert.assertEquals("word\nword2", to.cleanComment("word\n* word2"));
        Assert
            .assertEquals("word\nword2", to.cleanComment("word\n    * word2"));
        Assert.assertEquals("word\nword2",
            to.cleanComment("word\n  \t  * word2"));
    }
    
    @Test
    public final void testWhitespacePatterns() {
        Matcher m = to.PATTERN_WS.matcher(" ");
        Assert.assertTrue(m.find());
        Assert.assertEquals(" ", m.group());
        
        m = to.PATTERN_WS.matcher("   ");
        Assert.assertTrue(m.find());
        Assert.assertEquals("   ", m.group());
        
        m = to.PATTERN_WS.matcher("\t");
        Assert.assertTrue(m.find());
        Assert.assertEquals("\t", m.group());
    }
    
    @Test
    public final void testWordPatterns() {
        Matcher m = to.WORDPATTERN.matcher("a ");
        Assert.assertTrue(m.find());
        Assert.assertEquals("a ", m.group());
        
        m = to.WORDPATTERN.matcher("a   ");
        Assert.assertTrue(m.find());
        Assert.assertEquals("a   ", m.group());
        
        m = to.WORDPATTERN.matcher("a \t ");
        Assert.assertTrue(m.find());
        Assert.assertEquals("a \t ", m.group());
        
        m = to.WORDPATTERN.matcher("abc \t ");
        Assert.assertTrue(m.find());
        Assert.assertEquals("abc \t ", m.group());
        
        m = to.WORDPATTERN.matcher(" abc \t ");
        Assert.assertTrue(m.find());
        Assert.assertEquals("abc \t ", m.group());
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
        Assert.assertEquals(0, to.getLineWidth(4, null));
        Assert.assertEquals(0, to.getLineWidth(4, ""));
        Assert.assertEquals(4, to.getLineWidth(4, "test"));
        Assert.assertEquals(6, to.getLineWidth(4, "  test"));
        Assert.assertEquals(8, to.getLineWidth(4, "\ttest"));
    }
    
}
