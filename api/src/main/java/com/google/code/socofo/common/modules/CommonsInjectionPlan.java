/**
 * 
 */
package com.google.code.socofo.common.modules;

import com.google.code.socofo.common.api.IOHelper;
import com.google.code.socofo.common.impl.IOHelperImpl;
import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * @author kaeto23
 * 
 */
public class CommonsInjectionPlan implements Module {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(Binder binder) {
		binder.bind(IOHelper.class).to(IOHelperImpl.class);
	}

}
