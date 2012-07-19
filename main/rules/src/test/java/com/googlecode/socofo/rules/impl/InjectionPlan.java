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
package com.googlecode.socofo.rules.impl;

import com.google.inject.AbstractModule;
import com.googlecode.socofo.common.api.IOHelper;
import com.googlecode.socofo.common.impl.IOHelperImpl;
import com.googlecode.socofo.rules.api.RulesConfig;
import com.googlecode.socofo.rules.api.v1.RulesLoader;

/**
 * The guice injection plan.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class InjectionPlan extends AbstractModule {
    
    /**
     * Inits the plan.
     */
    public InjectionPlan() {
        // TODO Auto-generated constructor stub
    }
    
    /*
     * (non-Javadoc)
     * @see com.google.inject.AbstractModule#configure()
     */
    @Override
    protected void configure() {
        binder().bind(RulesLoader.class).to(RulesLoaderImpl.class);
        binder().bind(RulesConfig.class).to(RulesConfigImpl.class);
        binder().bind(IOHelper.class).to(IOHelperImpl.class);
    }
    
}
