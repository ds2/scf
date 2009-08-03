/**
 * 
 */
package ds2.enterspace.core.api;

/**
 * A source type detector
 * 
 * @author kaeto23
 * 
 */
public interface SourceTypeDetector {
	/**
	 * Guesses the file type by the file name suffix.
	 * 
	 * @param filename
	 *            the file name
	 * @return null, or the possible source type
	 */
	public SourceTypes guessTypeByFilename(String filename);
}
