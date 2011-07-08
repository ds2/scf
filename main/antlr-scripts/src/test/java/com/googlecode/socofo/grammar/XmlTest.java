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
package com.googlecode.socofo.grammar;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import junit.framework.TestCase;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.Token;
import org.junit.Test;

/**
 * Test client for testing the token detection.
 * 
 * @author Dirk Strauss
 * @version 1.0
 * 
 */
public class XmlTest extends TestCase {
    /**
     * a logger
     */
    private static final transient Logger LOG = Logger.getLogger(XmlTest.class
        .getName());
    
    /**
     * Runs the test
     */
    @Test
    public final void testXml() {
        try {
            LOG.finer("Loading XML sample");
            final InputStream is = getClass().getResourceAsStream("/test.xml");
            assertTrue(is != null);
            final CharStream input = new ANTLRInputStream(is, "utf-8");
            LOG.finer("Lexering the sample");
            final XmlGrammar lexer = new XmlGrammar(input);
            Token token;
            LOG.finer("Entering token loop");
            while ((token = lexer.nextToken()) != Token.EOF_TOKEN) {
                LOG.info("Token (" + token.getType() + "): " + token.getText());
                if (token.getType() == Token.EOF) {
                    break;
                }
            }
            LOG.finer("finished");
            is.close();
            assertTrue(true);
        } catch (final IOException e) {
            e.printStackTrace();
            fail(e.getLocalizedMessage());
        }
    }
    
}
