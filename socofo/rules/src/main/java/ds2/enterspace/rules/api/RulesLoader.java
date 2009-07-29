/**
 * 
 */
package ds2.enterspace.rules.api;

import java.io.InputStream;

/**
 * @author kaeto23
 * 
 */
public interface RulesLoader {
	/**
	 * Loads the XML formatter rules from the given input stream
	 * 
	 * @param is
	 *            the input stream containing the configuration
	 * @return null, or the configuration
	 */
	XmlFormatRules loadFormatRules(InputStream is);
}
