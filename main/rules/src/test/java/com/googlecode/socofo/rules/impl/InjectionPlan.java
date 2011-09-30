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
