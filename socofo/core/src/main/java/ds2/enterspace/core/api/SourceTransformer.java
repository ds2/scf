/**
 * 
 */
package ds2.enterspace.core.api;

import ds2.enterspace.rules.api.RuleSet;

/**
 * The base run sequence for performing a source code translation for a specific
 * source language. Implementations of this interface parse the content for XML,
 * Java or whatsoever.
 * 
 * @author kaeto23
 * 
 */
public interface SourceTransformer {
	/**
	 * Parses the given String of data into tokens and prepares the translation.
	 * 
	 * @param s
	 */
	public void parseContent(String s);

	/**
	 * Loads the rules for the formation result.
	 * 
	 * @param r
	 *            the ruleset
	 */
	public void loadRules(RuleSet r);

	/**
	 * Performs the translation. This method should be run in a separate thread.
	 */
	public void performTranslation();

	/**
	 * Returns the formatted result.
	 * 
	 * @return the formatted result
	 */
	public String getResult();
}
