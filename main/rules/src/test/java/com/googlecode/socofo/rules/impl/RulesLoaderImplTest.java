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

import java.io.InputStream;

import javax.inject.Inject;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.googlecode.socofo.rules.api.v1.RulesLoader;
import com.googlecode.socofo.rules.api.v1.XmlFormatRules;

/**
 * The rulesloader tests.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
@Guice(modules = { InjectionPlan.class })
public class RulesLoaderImplTest {
    /**
     * the test object.
     */
    @Inject
    private RulesLoader to = null;
    
    /**
     * Test method for
     * {@link com.googlecode.socofo.rules.impl.RulesLoaderImpl#loadFormatRules(java.io.InputStream)}
     * .
     */
    @Test
    public final void testLoadFormatRules() {
        Assert.assertNotNull(to);
        Assert.assertNull(to.loadFormatRules(null));
        InputStream is = getClass().getResourceAsStream("/xmllayout.xml");
        Assert.assertNotNull(is);
        XmlFormatRules rules = to.loadFormatRules(is);
        Assert.assertNotNull(rules);
    }
    
}
