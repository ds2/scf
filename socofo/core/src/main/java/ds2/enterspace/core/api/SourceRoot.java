/**
 * 
 */
package ds2.enterspace.core.api;

/**
 * The definition of the origin of a source code.
 * 
 * @author kaeto23
 * 
 */
public interface SourceRoot {
	/**
	 * Returns the content of the file. This usually invokes loading if a
	 * resource is not yet available.
	 * 
	 * @return the content of the file, or null if an error occurred
	 */
	public String getContent();

	/**
	 * Returns the possible type of the source code.
	 * 
	 * @return the possible type. May return null in case of no detection.
	 */
	public SourceTypes getType();
}
