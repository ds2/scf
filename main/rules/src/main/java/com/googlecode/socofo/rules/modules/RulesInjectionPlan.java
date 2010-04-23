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
package com.googlecode.socofo.rules.modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.googlecode.socofo.rules.api.RulesLoader;
import com.googlecode.socofo.rules.impl.RulesLoaderImpl;

/**
 * @author kaeto23
 * 
 */
public class RulesInjectionPlan implements Module {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(Binder binder) {
		binder.bind(RulesLoader.class).to(RulesLoaderImpl.class);
		binder.bindConstant().annotatedWith(Names.named("connectTimeout")).to(
				45000);
		binder.bindConstant().annotatedWith(Names.named("readTimeout")).to(
				30000);
	}

}
