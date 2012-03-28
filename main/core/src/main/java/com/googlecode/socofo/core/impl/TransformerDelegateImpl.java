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
package com.googlecode.socofo.core.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import com.googlecode.socofo.core.api.SourceTransformer;
import com.googlecode.socofo.core.api.SourceTypes;
import com.googlecode.socofo.core.api.TransformerDelegate;

/**
 * The base impl for the delegate.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class TransformerDelegateImpl implements TransformerDelegate {
    /**
     * The xml transformer. It is up to the JSR-299 implementation to define
     * one.
     */
    @Inject
    @Named(SourceTransformer.XML)
    private Provider<SourceTransformer> xml;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public SourceTransformer getTransformerOfType(final SourceTypes t) {
        SourceTransformer rc = null;
        switch (t) {
            case XML:
                rc = xml.get();
                break;
            default:
                break;
        }
        return rc;
    }
    
}
