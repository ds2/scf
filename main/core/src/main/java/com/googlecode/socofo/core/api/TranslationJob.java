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
package com.googlecode.socofo.core.api;

import java.util.List;

import com.googlecode.socofo.core.exceptions.TranslationException;
import com.googlecode.socofo.rules.api.v1.RuleSet;

/**
 * A job for transforming a source code into a transformed source code.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface TranslationJob extends Runnable {
    /**
     * Sets the source code.
     * 
     * @param sourceCode
     *            the source code to transform
     */
    public void setSource(SourceRoot sourceCode);
    
    /**
     * Sets the transformation rules for transforming the source code.
     * 
     * @param r
     *            the rules
     */
    public void setRule(RuleSet r);
    
    /**
     * Sets the destination of the transformed source code.
     * 
     * @param dest
     *            the destination of the transformed source code
     */
    public void setDestination(SourceDestination dest);
    
    /**
     * Returns a list of translation errors.
     * 
     * @return a list of translation errors
     */
    public List<TranslationException> getErrors();
}
