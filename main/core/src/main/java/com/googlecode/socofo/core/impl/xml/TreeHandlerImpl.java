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
package com.googlecode.socofo.core.impl.xml;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base implementation for the tree handler.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class TreeHandlerImpl implements TreeHandler {
    /**
     * The xml stack to calculate the level.
     */
    private Stack<XmlObject> xmlStack = null;
    /**
     * The logger.
     */
    private static final Logger LOG = LoggerFactory
        .getLogger(TreeHandlerImpl.class);
    
    /**
     * Inits the handler.
     */
    public TreeHandlerImpl() {
        xmlStack = new Stack<XmlObject>();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setLevel(final XmlObject xo) {
        if (xo == null) {
            LOG.warn("No xml object given!");
            return;
        }
        int currentLevel = xmlStack.size();
        boolean isVirtual = (xo instanceof Text);
        isVirtual |= (xo instanceof ProcessingInstruction);
        boolean isSingle = (xo instanceof Comment);
        isSingle |= xo instanceof EmptyElement;
        LOG.debug("isVirtual={}", isVirtual);
        if (xo.isEndTag()) {
            XmlObject lastEl = xmlStack.lastElement();
            String lastElName = lastEl.getElementName();
            if (lastElName == null) {
                lastElName = "";
            }
            if (lastElName.equals(xo.getElementName())) {
                // same element -> same level
                currentLevel = xmlStack.size();
                xmlStack.remove(xmlStack.size() - 1);
            } else {
                xmlStack.remove(xmlStack.size() - 1);
                currentLevel = xmlStack.size();
            }
        } else if (isSingle) {
            currentLevel = currentLevel + 1;
        } else if (!isVirtual) {
            xmlStack.add(xo);
            currentLevel = xmlStack.size();
        }
        if (currentLevel == 0) {
            currentLevel = 1;
        }
        xo.setLevel(currentLevel);
        LOG.debug("xml stack size is now {}", xmlStack.size());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        xmlStack.removeAllElements();
    }
    
}
