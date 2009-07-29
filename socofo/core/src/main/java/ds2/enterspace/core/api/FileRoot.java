/**
 * 
 */
package ds2.enterspace.core.api;

import java.io.File;

import ds2.enterspace.core.exceptions.LoadingException;

/**
 * @author kaeto23
 * 
 */
public interface FileRoot extends SourceRoot {
	/**
	 * Loads the given file
	 * 
	 * @param f
	 *            the file to load
	 * @throws LoadingException
	 *             if an error occurred
	 */
	public void loadFile(File f) throws LoadingException;
}
