/**
 * 
 */
package ds2.enterspace.rules.modules;

import com.google.inject.Binder;
import com.google.inject.Module;

import ds2.enterspace.rules.api.RulesLoader;
import ds2.enterspace.rules.impl.RulesLoaderImpl;

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
	}

}
