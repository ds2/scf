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
package com.googlecode.socofo.runtime.impl;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.googlecode.socofo.common.api.MainRuntime;

/**
 * The injection plan for the runtime.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class RuntimeInjectionPlan implements Module {
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(final Binder binder) {
        binder.bind(MainRuntime.class).to(DefRuntime.class);
    }
    
}
