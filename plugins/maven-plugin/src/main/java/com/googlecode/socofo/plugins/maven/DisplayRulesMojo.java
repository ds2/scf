/**
 * 
 */
package com.googlecode.socofo.plugins.maven;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.socofo.common.modules.CommonsInjectionPlan;
import com.googlecode.socofo.rules.api.RuleSet;
import com.googlecode.socofo.rules.api.RulesLoader;
import com.googlecode.socofo.rules.modules.RulesInjectionPlan;

/**
 * A mojo to display the rules.
 * 
 * @author dstrauss
 * @goal displayRules
 */
public class DisplayRulesMojo extends AbstractMojo {
	/**
	 * URL to the formatter xml.
	 * 
	 * @parameter expression="${formatterUrl}"
	 * @required
	 */
	private String formatterUrl;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		Injector ij = Guice.createInjector(new RulesInjectionPlan(),
				new CommonsInjectionPlan());
		RulesLoader rulesLoader = ij.getInstance(RulesLoader.class);
		try {
			URL u = new URL(formatterUrl);
			RuleSet ruleSet = rulesLoader.loadRulesFromUrl(u);
			if(ruleSet==null) {
				throw new MojoExecutionException("No ruleset found using URL "+u+"!");
			}
			getLog().info("Ruleset from " + u + " is:\n" + ruleSet);
		} catch (MalformedURLException e) {
			throw new MojoExecutionException("Error when loading the url", e);
		}
	}

}
