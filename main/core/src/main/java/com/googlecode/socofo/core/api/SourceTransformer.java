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
package com.googlecode.socofo.core.api;

import com.googlecode.socofo.core.exceptions.TranslationException;
import com.googlecode.socofo.rules.api.v1.RuleSet;

/**
 * The base run sequence for performing a source code translation for a specific
 * source language. Implementations of this interface parse the content for XML,
 * Java or whatsoever.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface SourceTransformer {
    /**
     * The qualifier name for the xml transformer.
     */
    String XML = "xml";
    
    /**
     * Parses the given String of data into tokens and prepares the translation.
     * 
     * @param s
     *            the content to parse
     * @throws TranslationException
     *             if the content could not be parsed successfully.
     */
    void parseContent(String s) throws TranslationException;
    
    /**
     * Loads the rules for the formation result.
     * 
     * @param r
     *            the ruleset
     */
    void setRules(RuleSet r);
    
    /**
     * Performs the translation. This method should be run in a separate thread.
     * 
     * @throws TranslationException
     *             if a translation error occurred
     */
    void performTranslation() throws TranslationException;
    
    /**
     * Returns the formatted result.
     * 
     * @return the formatted result
     */
    String getResult();
    
    /**
     * Returns the loaded rules.
     * 
     * @return the loaded rules, or null if not yet loaded
     */
    RuleSet getRules();
}
