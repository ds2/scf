/**
 * 
 */
package com.googlecode.socofo.core.exceptions;

/**
 * If the line buffer became too large.
 * 
 * @author dstrauss
 * @version 0.1
 */
public class LineBufferTooLargeException extends TranslationException {
    
    /**
     * The svuid.
     */
    private static final long serialVersionUID = -8292074160133767035L;
    
    public LineBufferTooLargeException(final String message) {
        super(message);
    }
    
    public LineBufferTooLargeException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
}
