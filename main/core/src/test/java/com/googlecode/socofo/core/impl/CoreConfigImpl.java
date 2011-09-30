/**
 * 
 */
package com.googlecode.socofo.core.impl;

import java.nio.charset.Charset;

import com.googlecode.socofo.core.api.CoreConfig;

/**
 * @author Kaeto23
 * 
 */
public class CoreConfigImpl implements CoreConfig {
    
    /**
     * 
     */
    public CoreConfigImpl() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public Charset getDefaultEncoding() {
        return Charset.forName("utf-8");
    }
    
}
