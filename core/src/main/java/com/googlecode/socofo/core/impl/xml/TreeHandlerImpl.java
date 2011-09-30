/**
 * 
 */
package com.googlecode.socofo.core.impl.xml;

import java.util.Stack;
import java.util.logging.Logger;

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
	private static final Logger LOG = Logger.getLogger(TreeHandlerImpl.class
			.getName());

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
		LOG.entering(TreeHandlerImpl.class.getName(), "setLevel", xo);
		if (xo == null) {
			LOG.warning("No xml object given!");
			LOG.exiting(TreeHandlerImpl.class.getName(), "setLevel");
			return;
		}
		int currentLevel = xmlStack.size();
		boolean isVirtual = (xo instanceof Text);
		isVirtual |= (xo instanceof ProcessingInstruction);
		LOG.finest("isVirtual=" + isVirtual);
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
		} else if (!isVirtual) {
			xmlStack.add(xo);
			currentLevel = xmlStack.size();
		}
		xo.setLevel(currentLevel);
		LOG.exiting(TreeHandlerImpl.class.getName(), "setLevel", currentLevel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		xmlStack.removeAllElements();
	}

}
