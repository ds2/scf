/*
 * SoCoFo - Another source code formatter
 * Copyright (C) 2013  Dirk Strauss
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
package com.googlecode.socofo.core.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.socofo.core.api.LineHandler;
import com.googlecode.socofo.core.api.SourceWriter;
import com.googlecode.socofo.core.exceptions.TranslationException;
import com.googlecode.socofo.rules.api.v1.CommonAttributes;

/**
 * The basic impl of the SourceWriter.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class SourceWriterImpl implements SourceWriter {
    /**
     * The string buffer to store content in.
     */
    private StringBuffer sb = null;
    /**
     * the line buffer.
     */
    private StringBuffer currentLine = null;
    /**
     * The common attributes for NEWLINE, maxLineLength and indentSequence.
     */
    private CommonAttributes ca = null;
    /**
     * the NewLine terminator.
     */
    private String newline = "\n";
    /**
     * A logger.
     */
    private static final transient Logger LOG = LoggerFactory
        .getLogger(SourceWriterImpl.class);
    /**
     * The line handler.
     */
    @Inject
    private LineHandler lh = null;
    
    /**
     * Inits the source buffer.
     */
    public SourceWriterImpl() {
        sb = new StringBuffer();
        currentLine = new StringBuffer();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addLine(final int indents, final String s)
        throws TranslationException {
        commitLine(false);
        if (indents < 0) {
            LOG.error("Indents of {} are impossible!", indents);
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
    public boolean addToLine(final String s) {
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
    public boolean addToLine(final int currentIndent, final String s) {
        if (s == null) {
            LOG.info("No content given!");
            return false;
        }
        final StringBuffer tmpBuffer = new StringBuffer();
        addIndents(tmpBuffer, currentIndent);
        tmpBuffer.append(s);
        final String insertStr = tmpBuffer.toString();
        tmpBuffer.insert(0, currentLine);
        final int lineLength = getLineLength(tmpBuffer.toString());
        boolean rc = true;
        if (lineLength <= ca.getMaxLinewidth()) {
            // ok
            currentLine.append(insertStr);
        } else {
            LOG.warn("Line becomes too long: {}{}", currentLine, insertStr);
            rc = false;
        }
        return rc;
    }
    
    /**
     * Adds a number of indents to the given string buffer.
     * 
     * @param s
     *            the string buffer to use
     * @param count
     *            the count of indents to add
     */
    private void addIndents(final StringBuffer s, final int count) {
        if (count < 0) {
            LOG.warn("Count is too low: {}", count);
            return;
        }
        if (s == null) {
            LOG.warn("No buffer given!");
            return;
        }
        synchronized (s) {
            s.append(lh.getSequence(count, ca.getIndentSequence()));
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void finish() throws TranslationException {
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
    public void setCommonAttributes(final CommonAttributes c) {
        if (c == null) {
            LOG.warn("No common attributes given!");
            return;
        }
        ca = c;
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public boolean commitLine(final boolean ignoreLineLength)
        throws TranslationException {
        if (!ignoreLineLength && currentLine.length() > ca.getMaxLinewidth()) {
            LOG.warn("line too long to commit: {}", currentLine);
            if (ca.getStopOnLongline()) {
                throw new TranslationException("Line too long to commit: "
                    + currentLine);
            }
            return false;
        }
        if (currentLine.length() <= 0) {
            LOG.debug("nothing to commit: line is empty already");
            return true;
        }
        sb.append(currentLine.toString());
        LOG.debug("commiting this content: {}", currentLine.toString());
        sb.append(newline);
        clearBuffer(currentLine);
        return true;
    }
    
    /**
     * Clears the given string buffer.
     * 
     * @param s
     *            the buffer to clear
     */
    private void clearBuffer(final StringBuffer s) {
        if (s == null) {
            LOG.warn("No buffer given!");
            return;
        }
        if (s.length() > 0) {
            LOG.debug("This content will be cleared now: {}", s.toString());
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
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getLineLength(final String line) {
        if (lh == null) {
            throw new IllegalStateException("No line handler has been setup!");
        }
        final int tabSize = ca.getTabSize();
        final int rc = lh.getLineWidth(tabSize, line);
        return rc;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentLineLength() {
        int rc = 0;
        final String currentLineStr = currentLine.toString();
        rc = getLineLength(currentLineStr);
        return rc;
    }
    
    /**
     * Sets the test line handler.
     * 
     * @param lineHandlerImpl
     *            the line handler used for tests
     */
    public void setTestLineHandler(final LineHandlerImpl lineHandlerImpl) {
        lh = lineHandlerImpl;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void clearCurrentLine() {
        clearBuffer(currentLine);
    }
    
}
