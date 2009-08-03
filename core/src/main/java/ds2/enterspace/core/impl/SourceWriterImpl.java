/**
 * SoCoFo Source Code Formatter
 * Copyright (C) 2009 Dirk Strauss <lexxy23@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
	/**
	 * the line buffer
	 */
	private StringBuffer currentLine = null;
	/**
	 * The common attributes for NEWLINE, maxLineLength and indentSequence
	 */
	private CommonAttributes ca = null;
	/**
	 * the NewLine terminator
	 */
	private String NEWLINE = "\n";
	/**
	 * A logger
	 */
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
		commitLine(false);
		if (indents < 0) {
			log.severe("Indents of " + indents + " are impossible!");
			return false;
		}
		addIndents(currentLine, indents);
		currentLine.append(s);
		return commitLine(false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addToLine(String s) {
		if (s == null) {
			return false;
		}
		currentLine.append(s);
		return true;
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
		if (s == null) {
			log.warning("No content given!");
			return;
		}
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
		if (count < 0) {
			log.warning("Count is too low: " + count);
			return;
		}
		if (s == null) {
			log.warning("No buffer given!");
			return;
		}
		for (int i = 0; i < count; i++) {
			s.append(ca.getIndentSequence());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void finish() {
		commitLine(false);
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
		if (c == null) {
			log.warning("No common attributes given!");
			return;
		}
		ca = c;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean commitLine(boolean ignoreLineLength) {
		if (!ignoreLineLength && currentLine.length() > ca.getMaxLinewidth()) {
			log.warning("line too long to commit: " + currentLine);
			return false;
		}
		if (currentLine.length() <= 0) {
			log.finer("nothing to commit: line is empty already");
			return true;
		}
		sb.append(currentLine.toString());
		sb.append(NEWLINE);
		clearBuffer(currentLine);
		return true;
	}

	/**
	 * Clears the given string buffer
	 * 
	 * @param s
	 *            the buffer to clear
	 */
	private void clearBuffer(StringBuffer s) {
		if (s == null) {
			log.warning("No buffer given!");
			return;
		}
		if (s.length() > 0) {
			log.finest("This content will be cleared now: " + s.toString());
		}
		s.delete(0, s.length());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCurrentLine() {
		return currentLine.toString();
	}

}
