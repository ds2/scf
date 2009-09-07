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
package com.googlecode.socofo.runtime;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.googlecode.socofo.common.modules.CommonsInjectionPlan;
import com.googlecode.socofo.core.impl.modules.CoreInjectionPlan;
import com.googlecode.socofo.rules.modules.RulesInjectionPlan;

/**
 * @author kaeto23
 * 
 */
public class RuntimeInjectionPlan implements Module {

	/**
	 * 
	 */
	public RuntimeInjectionPlan() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.Module#configure(com.google.inject.Binder)
	 */
	@Override
	public void configure(Binder binder) {
		binder.install(new CommonsInjectionPlan());
		binder.install(new RulesInjectionPlan());
		binder.install(new CoreInjectionPlan());
	}

}
