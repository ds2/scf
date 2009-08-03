/**
 * 
 */
package ds2.enterspace.core.impl;

import java.util.logging.Logger;

import com.google.inject.Singleton;

import ds2.enterspace.core.api.SourceTypes;
import ds2.enterspace.core.api.SourceTypeDetector;

/**
 * The implementation of a source type detector.
 * 
 * @author kaeto23
 * 
 */
@Singleton
public class TypeDetectorImpl implements SourceTypeDetector {
	/**
	 * A logger
	 */
	private static final transient Logger log = Logger
			.getLogger(TypeDetectorImpl.class.getName());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SourceTypes guessTypeByFilename(String fn) {
		if (fn == null) {
			log.warning("No filename given!");
			return null;
		}
		String filename = fn.trim();
		if (filename.length() <= 0) {
			log.warning("filename to short to guess type from!");
			return null;
		}
		filename = filename.toLowerCase();
		if (filename.endsWith(".xml")) {
			return SourceTypes.XML;
		}
		log.warning("Unknown type: " + fn);
		return null;
	}
}
