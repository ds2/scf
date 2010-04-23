/**
 * 
 */
package com.googlecode.socofo.core.impl;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.googlecode.socofo.core.api.SourceTransformer;
import com.googlecode.socofo.core.api.SourceTypes;
import com.googlecode.socofo.core.api.TransformerDelegate;
import com.googlecode.socofo.core.impl.xml.XmlTransformer;

/**
 * The base impl for the delegate.
 * 
 * @author dstrauss
 * 
 */
public class TransformerDelegateImpl implements TransformerDelegate {
	/**
	 * The injector to use.
	 */
	@Inject
	private Injector ij;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SourceTransformer getTransformerOfType(SourceTypes t) {
		SourceTransformer rc = null;
		switch (t) {
		case XML:
			rc = new XmlTransformer();
			break;
		default:
			break;
		}
		if (rc != null) {
			ij.injectMembers(rc);
		}
		return rc;
	}

}
