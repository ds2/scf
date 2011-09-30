/**
 * 
 */
package com.googlecode.socofo.core.impl.xml;

/**
 * Contract for setting the tree level for tree based sources.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public interface TreeHandler {
	/**
	 * Sets the tree level of the given xml object. The level is calculated
	 * using a history of given xml objects.
	 * 
	 * @param xo
	 *            the xml object to analyze
	 */
	void setLevel(XmlObject xo);

	/**
	 * Resets the xml object stack.
	 */
	void reset();
}
