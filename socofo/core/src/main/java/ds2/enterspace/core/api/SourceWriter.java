/**
 * 
 */
package ds2.enterspace.core.api;

import ds2.enterspace.rules.api.CommonAttributes;

/**
 * Source writer interface for writing source file content. Implementations of
 * this class usually write to a stream which becomes the new source file
 * content.
 * 
 * @author kaeto23
 * 
 */
public interface SourceWriter {
	/**
	 * Adds source file content
	 * 
	 * @param s
	 *            the content to write in one single line.
	 * @return TRUE if successful, otherwise FALSE
	 */
	public boolean writeLine(String s);

	/**
	 * Adds a new line to the writer, first adding some indents, and then adding
	 * the given sequence of data
	 * 
	 * @param indents
	 *            the count of indents to write first
	 * @param s
	 *            the source code to add to this line, right after the indents
	 * @return TRUE if successful, FALSE in case of the line becoming too long
	 */
	public boolean addLine(int indents, String s);

	/**
	 * Adds a sequence of data to the current line
	 * 
	 * @param s
	 *            a sequence of data to add to the current line
	 * @return TRUE if successful, otherwise FALSE in case of the line becoming
	 *         too long
	 */
	public boolean addToLine(String s);

	/**
	 * Returns the current result of the source writer.
	 * 
	 * @return an empty string, or the current result of the transformation
	 */
	public String getResult();

	/**
	 * Prepares the writer. Usually this will clear the cache.
	 */
	public void prepare();

	/**
	 * Finalizes the cache. Usually, this will end the source writer, clears the
	 * cache and prepares the result.
	 */
	public void finish();

	/**
	 * Sets the common attributes (line width, indent sequence etc.) to use
	 * 
	 * @param c
	 *            the common attributes
	 */
	public void setCommonAttributes(CommonAttributes c);

	/**
	 * Adds to the current line the number of indents, plus the given text. No
	 * newline is added after that.
	 * 
	 * @param currentIndent
	 *            the count of indents to add to the current line
	 * @param s
	 *            the string to add
	 */
	public void addToLine(int currentIndent, String s);

	/**
	 * Commits the current buffered line to the final source code. And clears
	 * the line buffer right after.
	 * 
	 * @param ignoreLinelength
	 *            flag to indicate that the line length check can be ignored.
	 *            Set TRUE to ignore line length, default is FALSE
	 */
	public void commitLine(boolean ignoreLinelength);
}
