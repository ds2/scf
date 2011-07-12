/**
 * 
 */
package com.googlecode.socofo.core.api;

/**
 * A basic contract for type providers.
 * 
 * @author Kaeto23
 * @param <E>
 *            the type to create
 * 
 */
public interface AbstractProvider<E> {
    /**
     * Returns a new instance of the given type.
     * 
     * @return a new instance
     */
    E getNewInstance();
}
