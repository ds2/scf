/**
 * 
 */
package ds2.enterspace.core.api;

import ds2.enterspace.rules.api.RuleSet;

/**
 * A job for transforming a source code into a transformed source code.
 * 
 * @author kaeto23
 * 
 */
public interface TranslationJob extends Runnable {
	/**
	 * Sets the source code
	 * 
	 * @param sourceCode
	 *            the source code to transform
	 */
	public void setSource(SourceRoot sourceCode);

	/**
	 * Sets the transformation rules for transforming the source code.
	 * 
	 * @param r
	 *            the rules
	 */
	public void setRule(RuleSet r);

	/**
	 * Sets the destination of the transformed source code.
	 * 
	 * @param dest
	 *            the destination of the transformed source code
	 */
	public void setDestination(SourceDestination dest);

	/**
	 * Starts the job. Usually, this method is executed in a separate thread.
	 * This method should not block the execution.
	 */
	public void startJob();
}
