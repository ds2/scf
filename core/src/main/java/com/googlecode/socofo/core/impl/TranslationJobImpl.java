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

import com.google.inject.name.Named;
import com.googlecode.socofo.core.api.SourceDestination;
import com.googlecode.socofo.core.api.SourceRoot;
import com.googlecode.socofo.core.api.SourceTransformer;
import com.googlecode.socofo.core.api.TranslationJob;
import com.googlecode.socofo.rules.api.RuleSet;

/**
 * @author kaeto23
 * 
 */
public class TranslationJobImpl implements TranslationJob {
	private SourceDestination dest = null;
	private RuleSet rules = null;
	private SourceRoot source = null;
	@Named("xml")
	private SourceTransformer xmlTransformer = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ds2.enterspace.core.api.TranslationJob#setDestination(ds2.enterspace.
	 * core.api.SourceDestination)
	 */
	@Override
	public void setDestination(SourceDestination dest) {
		this.dest = dest;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ds2.enterspace.core.api.TranslationJob#setRule(ds2.enterspace.rules.api
	 * .RuleSet)
	 */
	@Override
	public void setRule(RuleSet r) {
		rules = r;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ds2.enterspace.core.api.TranslationJob#setSource(ds2.enterspace.core.
	 * api.SourceRoot)
	 */
	@Override
	public void setSource(SourceRoot sourceCode) {
		source = sourceCode;
	}

	@Override
	public void run() {
		xmlTransformer.parseContent(source.getContent());
		xmlTransformer.loadRules(rules);
		xmlTransformer.performTranslation();
		String result = xmlTransformer.getResult();
		dest.writeContent(result, "utf-8");
	}

}
