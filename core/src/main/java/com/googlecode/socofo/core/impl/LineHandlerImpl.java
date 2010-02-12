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
package com.googlecode.socofo.core.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.googlecode.socofo.core.api.LineHandler;
import com.googlecode.socofo.rules.api.BreakFormat;

/**
 * @author kaeto23
 * 
 */
public class LineHandlerImpl implements LineHandler {
	/**
	 * A logger
	 */
	private static final Logger log = LoggerFactory
			.getLogger(LineHandlerImpl.class);
	/**
	 * Whitespace pattern
	 */
	protected static final Pattern PATTERN_WS = Pattern.compile("[\\s]+");
	/**
	 * word pattern with whitespaces
	 */
	protected static final Pattern wordPattern = Pattern.compile("[\\S]+"
			+ PATTERN_WS.pattern());
	/**
	 * the count of spaces chars to represent a single tab
	 */
	private int tabCharSize = 4;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> breakContent(int lineWidth, String content,
			int firstIndent, BreakFormat breakType) {
		log.debug("entering", new Object[] { lineWidth, content, firstIndent,
				breakType });
		List<String> rc = new ArrayList<String>();
		if (content == null || content.length() <= 0) {
			return rc;
		}
		if (firstIndent >= lineWidth) {
			log.error("The indent is too big to handle!");
			return rc;
		}
		log.debug("breaking content at default NEWLINE sequences");
		Scanner scanner = new Scanner(content);
		scanner.useDelimiter("\n");
		log.debug("Entering line loop");
		boolean isFirstLine = true;
		while (scanner.hasNext()) {
			String line = scanner.next();
			log.debug("current line is: {}", line);
			if (line == null) {
				log.debug("line has no content, continuing");
				continue;
			}
			if (line.length() <= 0 || breakType.equals(BreakFormat.NoBreak)) {
				log
						.debug("line is empty or has NOBREAK flag -> adding and continuing");
				rc.add(line);
				continue;
			}
			log.debug("Creating currentLine buffer, adding indent");
			StringBuffer currentLine = new StringBuffer();
			if (isFirstLine) {
				for (int i = 0; i < firstIndent; i++) {
					currentLine.append(" ");
				}
				isFirstLine = false;
			}
			line = line.trim();
			log.debug("Tokenizing line...");
			List<String> tokens = getTokens(line);
			log.debug("found {} tokens on the current line", tokens.size());
			log.debug("entering token loop");
			for (int tokenIndex = 0; tokenIndex < tokens.size(); tokenIndex++) {
				String token = tokens.get(tokenIndex);
				log.debug("Working with token({}): {}", tokenIndex, token);
				int tokenInsertOffset = currentLine.length();
				currentLine.append(token);
				if (getLengthOfBuffer(currentLine) > lineWidth) {
					log.debug("currentLine is too long, shortening");
					String beforeToken = "";
					switch (breakType) {
					case BeautyBreak:
						beforeToken = currentLine.substring(0,
								tokenInsertOffset);
						rc.add(beforeToken);
						currentLine.delete(0, tokenInsertOffset);
						break;
					case BeautyForcedBreak:
						while (getLengthOfBuffer(currentLine) > lineWidth) {
							beforeToken = currentLine.substring(0, lineWidth);
							rc.add(beforeToken);
							currentLine.delete(0, lineWidth);
						}
						break;
					}

				}
			}
			log.debug("Checking last buffer");
			if (currentLine.length() > 0) {
				log.debug("adding last content of currentLineBuffer");
				rc.add(currentLine.toString());
			}
			if (firstIndent > 0) {
				log.debug("Clearing first indent template");
				String firstLine = rc.get(0);
				firstLine = firstLine.trim();
				rc.remove(0);
				rc.add(0, firstLine);
			}
			log.debug("line finished");
		}
		log.debug("Removing leading empty lines");
		for (int i = 0; i < rc.size(); i++) {
			String line = rc.get(0);
			if (line.trim().length() <= 0) {
				rc.remove(0);
			} else {
				break;
			}
		}
		log.debug("exiting {}", rc);
		return rc;
	}

	/**
	 * Separates the given line into a list of tokens. The term token referres
	 * to the string sequence defined by the regular expression pattern.
	 * 
	 * @param line
	 *            the line to separate
	 * @return a list of tokens to iterate through
	 * @see #wordPattern
	 */
	protected List<String> getTokens(final String line) {
		log.debug("entering with: {}", line);
		List<String> tokenList = new ArrayList<String>();
		if (line == null || line.length() <= 0) {
			return tokenList;
		}
		Matcher wordMatcher = wordPattern.matcher(line);
		int lastOffset = 0;
		while (wordMatcher.find(lastOffset)) {
			String wordSeq = wordMatcher.group();
			tokenList.add(wordSeq);
			lastOffset = wordMatcher.end();
			log.debug("found a seq between {} and {}", wordMatcher.start(),
					lastOffset);
		}
		if (lastOffset < line.length()) {
			// there is something missing
			tokenList.add(line.substring(lastOffset));
		}
		log.debug("exiting: {}", tokenList);
		return tokenList;
	}

	/**
	 * Returns the length of the buffer
	 * 
	 * @param sb
	 *            the buffer to analyze
	 * @return the length of the buffer
	 */
	protected int getLengthOfBuffer(StringBuffer sb) {
		String s = sb.toString();
		return getLineWidth(tabCharSize, s);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int calculateContentLineWidth(int maxLineWidth, int ac) {
		if (maxLineWidth <= 0) {
			// no linewidth set
			return -1;
		}
		int rc = maxLineWidth;
		rc -= ac;
		if (rc < 0) {
			log.warn("too much additional chars: {}", ac);
			rc = 0;
		} else if (rc == 0) {
			log.warn("Width and additional chars are the same!");
		}
		return rc;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String cleanComment(String lines) {
		log.warn("entering: {}", lines);
		StringBuffer rc = new StringBuffer();
		if (lines == null) {
			return "";
		}
		Scanner scanner = new Scanner(lines);
		scanner.useDelimiter("\n");
		while (scanner.hasNext()) {
			String line = scanner.next();
			line = line.trim();
			if (line.startsWith("* ")) {
				line = line.substring(2);
			} else if (line.equalsIgnoreCase("*")) {
				line = "";
			}
			rc.append(line);
			rc.append("\n");
		}
		String rc2 = rc.toString();
		// remove the last \n char
		if (rc2.length() > 0) {
			rc2 = rc2.substring(0, rc2.length() - 1);
		}
		log.debug("exiting", rc2);
		return rc2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String removeEnters(String s) {
		String rc = s;
		if (rc == null || rc.length() <= 0) {
			return "";
		}
		rc = rc.replaceAll("\n", " ");
		return rc;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTabSize(int v) {
		if (v <= 0) {
			return;
		}
		tabCharSize = v;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getLineWidth(int tabSize, String s) {
		log.debug("entering: {}", s);
		int rc = 0;
		if (s == null || s.length() <= 0) {
			return rc;
		}
		rc = s.length();
		log.debug("actual length is " + rc);
		// count all tab chars
		int startOffset = 0;
		while (s.indexOf("\t", startOffset++) >= 0) {
			rc += (tabCharSize - 1);
		}
		log.debug("exiting: {}", rc);
		return rc;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSequence(final int level, final String indentSequence) {
		if (level <= 0 || indentSequence == null) {
			return "";
		}
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < level; i++) {
			sb.append(indentSequence);
		}
		return sb.toString();
	}

}
