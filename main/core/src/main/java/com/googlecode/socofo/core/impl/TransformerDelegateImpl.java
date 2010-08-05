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
