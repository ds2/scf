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
package com.googlecode.socofo.core.provider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.googlecode.socofo.core.api.SourceDestination;
import com.googlecode.socofo.core.api.SourceRoot;
import com.googlecode.socofo.core.api.SourceTransformer;
import com.googlecode.socofo.core.api.TransformerDelegate;
import com.googlecode.socofo.core.api.TranslationJob;
import com.googlecode.socofo.core.exceptions.TranslationException;
import com.googlecode.socofo.rules.api.v1.RuleSet;

/**
 * The basic implementation of a translation job.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class TranslationJobImpl implements TranslationJob {
    /**
     * The destination of the transformation result.
     */
    private SourceDestination dest;
    /**
     * The rule set.
     */
    private RuleSet rules;
    /**
     * The source to transform.
     */
    private SourceRoot source;
    
    /**
     * A list of translation errors.
     */
    private List<TranslationException> errors;
    /**
     * The transformer delegate.
     */
    @Inject
    private TransformerDelegate transformDelegate;
    
    /**
     * Inits the job.
     */
    public TranslationJobImpl() {
        errors = new ArrayList<TranslationException>();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final void setDestination(final SourceDestination d) {
        this.dest = d;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final void setRule(final RuleSet r) {
        if (r == null) {
            throw new IllegalArgumentException("Rules not given!");
        }
        rules = r;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final void setSource(final SourceRoot sourceCode) {
        source = sourceCode;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final void run() {
        String result = "";
        final SourceTransformer tr =
            transformDelegate.getTransformerOfType(source.getType());
        try {
            if (tr == null) {
                throw new TranslationException(
                    "No transformer found for source " + source + "!");
            }
            if (rules == null) {
                throw new TranslationException("No rules found!");
            }
            tr.setRules(rules);
            tr.parseContent(source.getContent());
            tr.performTranslation();
            result = tr.getResult();
            dest.writeContent(result, "utf-8");
        } catch (final TranslationException e) {
            errors.add(e);
        } catch (final Exception e) {
            errors.add(new TranslationException(e.getLocalizedMessage(), e));
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final List<TranslationException> getErrors() {
        return errors;
    }
    
}
