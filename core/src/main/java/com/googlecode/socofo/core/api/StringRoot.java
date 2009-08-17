/**
 * 
 */
package com.googlecode.socofo.core.api;

/**
 * A string source root.
 * @author dstrauss
 *
 */
public interface StringRoot extends SourceRoot{
	/**
	 * Sets the content.
	 * @param s the content
	 */
	public void setContent(String s);
	/**
	 * Sets the type of the content.
	 * @param t the type
	 */
	public void setType(SourceTypes t);
}
