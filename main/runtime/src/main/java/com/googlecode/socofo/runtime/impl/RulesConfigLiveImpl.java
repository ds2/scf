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
package com.googlecode.socofo.runtime.impl;

import javax.inject.Singleton;

import com.googlecode.socofo.rules.api.RulesConfig;

/**
 * @author dstrauss
 * 
 */
@Singleton
public class RulesConfigLiveImpl implements RulesConfig {
    
    /**
     * 
     */
    public RulesConfigLiveImpl() {
        // TODO Auto-generated constructor stub
    }
    
    /*
     * (non-Javadoc)
     * @see com.googlecode.socofo.rules.api.RulesConfig#getConnectTimeout()
     */
    @Override
    public int getConnectTimeout() {
        return 30000;
    }
    
    /*
     * (non-Javadoc)
     * @see com.googlecode.socofo.rules.api.RulesConfig#getReadTimeout()
     */
    @Override
    public int getReadTimeout() {
        return 30000;
    }
    
}
