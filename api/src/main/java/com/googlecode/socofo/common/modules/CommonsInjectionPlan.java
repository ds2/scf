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
package com.googlecode.socofo.common.modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.googlecode.socofo.common.api.IOHelper;
import com.googlecode.socofo.common.impl.IOHelperImpl;

/**
 * The injection plan for all common interfaces.
 * 
 * @author Dirk Stauss
 * @version 1.0
 */
public class CommonsInjectionPlan implements Module {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(final Binder binder) {
		binder.bind(IOHelper.class).to(IOHelperImpl.class);
	}

}
