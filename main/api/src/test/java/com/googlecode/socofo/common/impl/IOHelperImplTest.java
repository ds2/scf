/*
 * SoCoFo - Another source code formatter
 * Copyright (C) 2012  Dirk Strauss
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
package com.googlecode.socofo.common.impl;

import java.io.BufferedInputStream;
import java.io.UnsupportedEncodingException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Testcase for IOHelperImpl.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class IOHelperImplTest {
    /**
     * The test object.
     */
    private IOHelperImpl to = null;
    
    /**
     * Sets up a test run.
     * 
     * @throws java.lang.Exception
     *             if an error occurred
     */
    @BeforeClass
    public void setUp() throws Exception {
        to = new IOHelperImpl();
    }
    
    /**
     * Test method for
     * {@link com.googlecode.socofo.common.impl.IOHelperImpl#closeInputstream(java.io.InputStream)}
     * .
     */
    @Test
    public final void testCloseInputstream() {
        to.closeInputstream(null);
    }
    
    /**
     * Test method for
     * {@link com.googlecode.socofo.common.impl.IOHelperImpl#closeReader(java.io.Reader)}
     * .
     */
    @Test
    public final void testCloseReader() {
        to.closeReader(null);
    }
    
    /**
     * Test method for
     * {@link com.googlecode.socofo.common.impl.IOHelperImpl#closeOutputstream(java.io.OutputStream)}
     * .
     */
    @Test
    public final void testCloseOutputstream() {
        to.closeOutputstream(null);
    }
    
    /**
     * Test method for
     * {@link com.googlecode.socofo.common.impl.IOHelperImpl#createString(byte[], java.lang.String)}
     * .
     */
    @Test
    public final void testCreateString() {
        final String s = "test";
        byte[] b = null;
        try {
            b = s.getBytes("utf-8");
        } catch (final UnsupportedEncodingException e) {
            Assert.fail(e.getLocalizedMessage());
        }
        final String s2 = to.createString(b, "utf-8");
        Assert.assertEquals(s, s2);
    }
    
    /**
     * Test method for
     * {@link com.googlecode.socofo.common.impl.IOHelperImpl#read(java.io.InputStream, byte[])}
     * .
     */
    @Test
    public final void testRead() {
        final byte[] buffer = new byte[5000];
        final BufferedInputStream bis =
            new BufferedInputStream(getClass().getResourceAsStream(
                "/buffertest.txt"));
        final int read = to.read(bis, buffer);
        Assert.assertTrue(read > 0);
        to.closeInputstream(bis);
        String str = to.createString(buffer, "utf-8");
        Assert.assertNotNull(str);
        str = str.trim();
        Assert.assertEquals("test", str);
    }
    
}
