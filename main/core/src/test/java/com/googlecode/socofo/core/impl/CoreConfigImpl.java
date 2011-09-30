/**
 * 
 */
package com.googlecode.socofo.core.impl;

import java.nio.charset.Charset;

import com.googlecode.socofo.core.api.CoreConfig;

/**
 * Basic config implementation.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class CoreConfigImpl implements CoreConfig {
    
    /**
     * Inits the dto.
     */
    public CoreConfigImpl() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public Charset getDefaultEncoding() {
        return Charset.forName("utf-8");
    }
    
}
