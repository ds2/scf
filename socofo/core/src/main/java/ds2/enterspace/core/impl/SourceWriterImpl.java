/**
 * 
 */
package ds2.enterspace.core.impl;

import java.util.logging.Logger;

import ds2.enterspace.core.api.SourceWriter;
import ds2.enterspace.rules.api.CommonAttributes;

/**
 * @author kaeto23
 * 
 */
public class SourceWriterImpl implements SourceWriter {
	/**
	 * The string buffer to store content in
	 */
	private StringBuffer sb = null;
	private StringBuffer currentLine = null;
	private CommonAttributes ca = null;
	private String NEWLINE = "\n";
	private static final transient Logger log = Logger
			.getLogger(SourceWriterImpl.class.getName());

	/**
	 * Inits the source buffer
	 */
	public SourceWriterImpl() {
		sb = new StringBuffer();
		currentLine = new StringBuffer();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addLine(int indents, String s) {
		clearBuffer(currentLine);
		if (indents < 0) {
			log.severe("Indents of " + indents + " are impossible!");
			return false;
		}
		addIndents(sb, indents);
		sb.append(s);
		sb.append(NEWLINE);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addToLine(String s) {
		currentLine.append(s);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean writeLine(String s) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getResult() {
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addToLine(int currentIndent, String s) {
		addIndents(currentLine, currentIndent);
		currentLine.append(s);
	}

	/**
	 * Adds a number of indents to the given string buffer
	 * 
	 * @param s
	 *            the string buffer to use
	 * @param count
	 *            the count of indents to add
	 */
	private void addIndents(StringBuffer s, int count) {
		for (int i = 0; i < count; i++) {
			s.append(ca.getIndentSequence());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void finish() {
		sb.append(currentLine);
		clearBuffer(currentLine);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void prepare() {
		clearBuffer(sb);
		clearBuffer(currentLine);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCommonAttributes(CommonAttributes c) {
		ca = c;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void commitLine(boolean ignoreLineLength) {
		sb.append(currentLine.toString());
		sb.append(NEWLINE);
		clearBuffer(currentLine);
	}

	/**
	 * Clears the given string buffer
	 * 
	 * @param s
	 *            the buffer to clear
	 */
	private void clearBuffer(StringBuffer s) {
		s.delete(0, s.length());
	}

}
