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
package com.googlecode.socofo.plugins.maven;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.socofo.common.api.MainRuntime;
import com.googlecode.socofo.runtime.RuntimeInjectionPlan;

/**
 * Mojo to format all source codes.
 * 
 * @author Dirk Strauss
 * @version 1.0
 * @goal format
 * @phase pre-compile
 */
public class FormatMojo extends AbstractMojo {
	/**
	 * The current project to scan.
	 * 
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject currentPrj = null;
	/**
	 * The runtime that performs the actions.
	 */
	private MainRuntime socofo = null;

	/**
	 * 
	 */
	public FormatMojo() {
		Injector ij = Guice.createInjector(new RuntimeInjectionPlan());
		socofo = ij.getInstance(MainRuntime.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info(
				"This goal will format all source code in "
						+ currentPrj.getArtifactId());
		getLog().debug("Searching for source directory...");
		String srcDirStr = currentPrj.getBuild().getSourceDirectory();
		if (srcDirStr == null) {
			getLog().info("No sources found!");
			return;
		}
		File srcDir = new File(srcDirStr);
		if (!srcDir.exists()) {
			getLog().info(
					"Source directory " + srcDir.getAbsolutePath()
							+ " does not exist. Ignoring.");
			return;
		}
		if (!srcDir.canRead()) {
			getLog().info("Cannot read sources in " + srcDir.getAbsolutePath());
			return;
		}
		socofo.setBaseDirectory(srcDir);
		getLog().debug("Searching target directory..");
		String targetDirStr = currentPrj.getBuild().getOutputDirectory();
		File targetDir = new File(targetDirStr, "../results");
		getLog().info("target dir is " + targetDir.getAbsolutePath());
		socofo.setTargetDirectory(targetDir);
		String rulesUrlStr = "";
		URL rulesUrl;
		try {
			rulesUrl = new URL(rulesUrlStr);
		} catch (MalformedURLException e) {
			throw new MojoFailureException("Bad rules url: " + rulesUrlStr);
		}
		socofo.setRules(rulesUrl);
		int runtimeRc = socofo.execute();
		getLog().info("Execution was " + runtimeRc);
	}
}
