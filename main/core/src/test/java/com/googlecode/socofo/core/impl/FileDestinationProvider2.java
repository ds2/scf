/**
 * 
 */
package com.googlecode.socofo.core.impl;

import javax.inject.Provider;

import com.googlecode.socofo.common.api.IOHelper;
import com.googlecode.socofo.core.api.FileDestination;
import com.googlecode.socofo.core.provider.FileDestinationImpl;

/**
 * @author Kaeto23
 * 
 */
public class FileDestinationProvider2 implements Provider<FileDestination> {
    private IOHelper io;
    
    /**
     * 
     */
    public FileDestinationProvider2() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public FileDestination get() {
        FileDestinationImpl rc = new FileDestinationImpl();
        return rc;
    }
    
}
