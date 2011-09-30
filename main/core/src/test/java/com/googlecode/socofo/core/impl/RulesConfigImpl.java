/**
 * 
 */
package com.googlecode.socofo.core.impl;

import javax.inject.Singleton;

import com.googlecode.socofo.rules.api.RulesConfig;

/**
 * The dto for the rules config.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
@Singleton
public class RulesConfigImpl implements RulesConfig {
    
    /**
     * inits the impl.
     */
    public RulesConfigImpl() {
        // TODO Auto-generated constructor stub
    }
    
    /*
     * (non-Javadoc)
     * @see com.googlecode.socofo.rules.api.RulesConfig#getConnectTimeout()
     */
    @Override
    public int getConnectTimeout() {
        return 45000;
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
