/**
 * 
 */
package ds2.enterspace.core.api;

import java.io.InputStream;

import ds2.enterspace.core.exceptions.LoadingException;

/**
 * @author kaeto23
 * 
 */
public interface StreamRoot extends SourceRoot {
	/**
	 * Sets the stream to read the content from.
	 * 
	 * @param is
	 *            the input stream that contains the content
	 * @param enc
	 *            the possible encoding. Set null if you want to use the default
	 *            encoding (set by the injector)
	 * @throws LoadingException
	 *             if loading the stream did not succeed
	 */
	public void loadStream(InputStream is, String enc) throws LoadingException;
}
