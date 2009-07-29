/**
 * 
 */
package ds2.enterspace.core.api;

/**
 * The destination of a source code transformation.
 * 
 * @author kaeto23
 * 
 */
public interface SourceDestination {
	/**
	 * Writes the content of the transformation to the stream.
	 * 
	 * @param s
	 *            the content to write
	 */
	public void writeContent(String s);
}
