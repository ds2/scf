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
