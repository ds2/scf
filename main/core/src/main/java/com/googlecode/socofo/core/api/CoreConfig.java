/**
 * 
 */
package com.googlecode.socofo.core.api;

import java.nio.charset.Charset;

/**
 * The config for the core artifact.
 * 
 * @author Dirk Strauss
 * @version 1.0
 * 
 */
public interface CoreConfig {
    /**
     * Returns the default charset to use in case no charset has been given.
     * 
     * @return the default charset
     */
    Charset getDefaultEncoding();
}
