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
import java.util.ArrayList;
import java.util.List;

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
	 * URL to the formatter xml.
	 * 
	 * @parameter expression="${formatterUrl}"
	 * @required
	 */
	private String formatterUrl;
	/**
	 * The source file filter. Compared to {@link #types}, this includes
	 * patterns to exclude any file that does not match this pattern. So, while
	 * the types param sets XML, you can set filters here to pom.xml to only
	 * parse pom.xml files, and no other xml file.
	 * 
	 * @parameter
	 */
	private List<String> filters;
	/**
	 * The list of types to scan for.
	 * 
	 * @parameter expression="${types}" property="scanTypes"
	 * @required
	 */
	private List<String> types;
	/**
	 * The runtime that performs the actions.
	 */
	private MainRuntime socofo = null;
	/**
	 * The base directory to scan for files.
	 * 
	 * @parameter expression="${baseDir}" default-value="${project.build.sourceDirectory}"
	 */
	private File baseDir;
	/**
	 * The directory to write the results to.
	 * 
	 * @parameter expression="${project.build.outputDirectory}/../results"
	 */
	private File resultDir;

	/**
	 * 
	 */
	public FormatMojo() {
		Injector ij = Guice.createInjector(new RuntimeInjectionPlan());
		socofo = ij.getInstance(MainRuntime.class);
		types = new ArrayList<String>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().debug(
				"This goal will format all source code in "
						+ currentPrj.getArtifactId());
		getLog().debug("Searching for source directory...");
		if (!baseDir.exists()) {
			getLog().error(
					"Source directory " + baseDir.getAbsolutePath()
							+ " does not exist. Ignoring.");
			return;
		}
		if (!baseDir.canRead()) {
			getLog().error(
					"Cannot read sources in " + baseDir.getAbsolutePath());
			return;
		}
		socofo.setBaseDirectory(baseDir);
		getLog().debug("Searching target directory..");
		getLog().debug("target dir is " + resultDir.getAbsolutePath());
		socofo.setTargetDirectory(resultDir);
		String rulesUrlStr = formatterUrl;
		URL rulesUrl;
		try {
			rulesUrl = new URL(rulesUrlStr);
		} catch (MalformedURLException e) {
			throw new MojoFailureException("Bad rules url: " + rulesUrlStr);
		}
		socofo.setRules(rulesUrl);
		socofo.setTypes(types);
		socofo.applySourceFilters(filters);
		getLog().info("Starting transformation");
		int runtimeRc = socofo.execute();
		getLog().info("Execution was " + runtimeRc);
	}

	public void setScanTypes(String s) {
		if (s == null || s.length() <= 0) {
			return;
		}
		String[] types = s.split(",");
		for (String type : types) {
			this.types.add(type);
		}
	}
}
