/**
 * 
 */
package com.googlecode.socofo.rules.api;

/**
 * @author Kaeto23
 * 
 */
public interface RulesConfig {
    /**
     * Returns the connect timeout, in milliseconds.
     * 
     * @return the connect timeout
     */
    int getConnectTimeout();
    
    /**
     * Returns the read timeout, in milliseconds.
     * 
     * @return the read timeout
     */
    int getReadTimeout();
}
