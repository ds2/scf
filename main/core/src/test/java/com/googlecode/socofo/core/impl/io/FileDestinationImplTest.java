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

import javax.inject.Inject;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.googlecode.socofo.core.api.FileDestination;
import com.googlecode.socofo.core.impl.TestInjectionPlan;

/**
 * @author kaeto23
 * 
 */
@Guice(modules = { TestInjectionPlan.class })
public class FileDestinationImplTest {
    @Inject
    private FileDestination to = null;
    
    /**
     * Test method for
     * {@link com.googlecode.socofo.core.provider.FileDestinationImpl#writeContent(java.lang.String, java.lang.String)}
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
     * {@link com.googlecode.socofo.core.provider.FileDestinationImpl#setFile(java.io.File)}
     * .
     */
    @Test
    public final void testSetFileNull() {
        to.setFile(null);
    }
    
    @Test
    public final void testSetFileCorrect() {
        to.setFile(new File("target/pom_delme.xml"));
    }
    
    @Test
    public final void testParseDestination() {
        assertNull(to.parseDestination(null, null, null));
    }
    
    public final void testParseDestination3() {
        // assertNull(to.parseDestination("backup", null, null));
        // assertNull(to.parseDestination("backup", null, ""));
    }
    
    public final void testParseDestination4() {
        // assertNull(to.parseDestination("backup", null, "backup3/Test.java"));
    }
    
}
