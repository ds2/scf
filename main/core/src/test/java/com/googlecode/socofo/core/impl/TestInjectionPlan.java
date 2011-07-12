/**
 * 
 */
package com.googlecode.socofo.core.impl;

import javax.inject.Provider;

import com.google.inject.AbstractModule;
import com.google.inject.MembersInjector;
import com.google.inject.Provides;
import com.googlecode.socofo.common.api.IOHelper;
import com.googlecode.socofo.common.impl.IOHelperImpl;
import com.googlecode.socofo.core.api.FileDestination;
import com.googlecode.socofo.core.api.FileRoot;
import com.googlecode.socofo.core.api.LineHandler;
import com.googlecode.socofo.core.api.Scheduler;
import com.googlecode.socofo.core.api.SourceTypeDetector;
import com.googlecode.socofo.core.api.SourceWriter;
import com.googlecode.socofo.core.impl.io.FileDestinationImpl;
import com.googlecode.socofo.core.impl.io.FileRootImpl;

/**
 * @author Kaeto23
 * 
 */
public class TestInjectionPlan extends AbstractModule {
    
    /**
     * 
     */
    public TestInjectionPlan() {
        // TODO Auto-generated constructor stub
    }
    
    /*
     * (non-Javadoc)
     * @see com.google.inject.AbstractModule#configure()
     */
    @Override
    protected void configure() {
        binder().bind(Scheduler.class).to(SchedulerImpl.class);
        binder().bind(SourceWriter.class).to(SourceWriterImpl.class);
        binder().bind(LineHandler.class).to(LineHandlerImpl.class);
        binder().bind(IOHelper.class).to(IOHelperImpl.class);
        binder().bind(FileDestination.class).to(FileDestinationImpl.class);
        binder().bind(SourceTypeDetector.class).to(TypeDetectorImpl.class);
    }
    
    @Provides
    public Provider<FileDestination> createDestination() {
        final MembersInjector<FileDestination> mi =
            getMembersInjector(FileDestination.class);
        Provider<FileDestination> rc = new Provider<FileDestination>() {
            
            @Override
            public FileDestination get() {
                FileDestinationImpl rc = new FileDestinationImpl();
                mi.injectMembers(rc);
                return rc;
            }
        };
        return rc;
    }
    
    @Provides
    public Provider<FileRoot> createRoot() {
        final MembersInjector<IOHelper> mi = getMembersInjector(IOHelper.class);
        Provider<FileRoot> rc = new Provider<FileRoot>() {
            
            @Override
            public FileRoot get() {
                FileRootImpl rc = new FileRootImpl();
                mi.injectMembers(rc);
                return rc;
            }
        };
        return rc;
    }
}
