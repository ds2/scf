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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import javax.inject.Inject;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.googlecode.socofo.core.api.SourceWriter;
import com.googlecode.socofo.core.exceptions.TranslationException;
import com.googlecode.socofo.rules.api.v1.CommonAttributes;

/**
 * The sourcewriter tests.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
@Guice(modules = { TestInjectionPlan.class })
@Test(singleThreaded = true)
public class SourceWriterImplTest {
    /**
     * The test object.
     */
    @Inject
    private SourceWriter to;
    
    /**
     * Actions to perform in start.
     * 
     * @throws java.lang.Exception
     *             any error
     */
    @BeforeMethod
    public final void setUp() throws Exception {
        to.setCommonAttributes(getAttributes());
        to.prepare();
    }
    
    /**
     * Returns some attributes to use.
     * 
     * @return the common attributes
     */
    private CommonAttributes getAttributes() {
        return new CommonAttributes() {
            
            /**
             * The svuid.
             */
            private static final long serialVersionUID = 5970259051595764059L;
            
            @Override
            public int getMaxLinewidth() {
                return 10;
            }
            
            @Override
            public String getIndentSequence() {
                return "  ";
            }
            
            @Override
            public int getTabSize() {
                return 4;
            }
            
            @Override
            public Boolean getStopOnLongline() {
                return false;
            }
        };
    }
    
    /**
     * Test method for
     * {@link com.googlecode.socofo.core.impl.SourceWriterImpl#addLine(int, java.lang.String)} .
     * 
     */
    @Test
    public final void testAddLine() {
        try {
            to.addLine(-1, null);
            assertEquals(to.getResult(), "");
            to.addLine(0, "hello");
            to.finish();
            assertEquals(to.getResult(), "hello\n");
        } catch (final TranslationException e) {
            fail(e.getLocalizedMessage());
        }
    }
    
    /**
     * Test method for
     * {@link com.googlecode.socofo.core.impl.SourceWriterImpl#addToLine(java.lang.String)} .
     */
    @Test
    public final void testAddToLineString() {
        to.prepare();
        assertFalse(to.addToLine(null));
        try {
            to.finish();
            assertEquals(to.getResult(), "");
            assertTrue(to.addToLine("hi"));
            assertEquals(to.getCurrentLine(), "hi");
            to.prepare();
            assertTrue(to.addToLine("hello, "));
            assertTrue(to.addToLine("world"));
            to.commitLine(true);
            assertEquals(to.getResult(), "hello, world\n");
        } catch (final TranslationException e) {
            fail(e.getLocalizedMessage());
        }
    }
    
    /**
     * Test method for
     * {@link com.googlecode.socofo.core.impl.SourceWriterImpl#addToLine(int, java.lang.String)} .
     */
    @Test
    public final void testAddToLineIntString() {
        to.addToLine(-1, null);
        try {
            to.finish();
        } catch (TranslationException e) {
            fail(e.getLocalizedMessage());
        }
        assertEquals(to.getResult(), "");
    }
    
    /**
     * Test method for
     * {@link com.googlecode.socofo.core.impl.SourceWriterImpl#setCommonAttributes(com.googlecode.socofo.rules.api.v1.CommonAttributes)}
     * .
     */
    @Test
    public final void testSetCommonAttributes() {
        to.setCommonAttributes(null);
    }
    
    /**
     * Test method for {@link com.googlecode.socofo.core.impl.SourceWriterImpl#commitLine(boolean)}
     * .
     */
    @Test
    public final void testCommitLine() {
        try {
            to.commitLine(false);
            assertEquals("", to.getResult());
            to.addLine(0, "hello this is a longer line");
            assertFalse(to.commitLine(false));
        } catch (TranslationException e) {
            fail(e.getLocalizedMessage());
        }
    }
    
}
