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
/**
 * 
 */
package com.googlecode.socofo.grammar;

import java.io.IOException;
import java.io.InputStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test client for testing the token detection.
 * 
 * @author Dirk Strauss
 * @version 1.0
 * 
 */
public class XmlTest {
    /**
     * a logger
     */
    private static final transient Logger LOG = LoggerFactory
        .getLogger(XmlTest.class);
    
    /**
     * Runs the test
     */
    @Test
    public final void testXml() {
        try {
            LOG.info("Loading XML sample");
            final InputStream is = getClass().getResourceAsStream("/test.xml");
            Assert.assertTrue(is != null);
            final CharStream input = new ANTLRInputStream(is);
            LOG.info("Lexering the sample");
            final XmlGrammar lexer = new XmlGrammar(input);
            Token token;
            LOG.info("Entering token loop");
            while ((token = lexer.nextToken()) != null) {
                LOG.info("Token (" + token.getType() + "): " + token.getText());
                if (token.getType() == Token.EOF) {
                    break;
                }
            }
            LOG.info("finished");
            is.close();
            Assert.assertTrue(true);
        } catch (final IOException e) {
          LOG.error("Error when loading the test xml class!", e);
            Assert.fail(e.getLocalizedMessage());
        }
    }
    
}
