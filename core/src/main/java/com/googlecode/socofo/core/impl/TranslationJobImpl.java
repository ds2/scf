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

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.googlecode.socofo.core.api.SourceDestination;
import com.googlecode.socofo.core.api.SourceRoot;
import com.googlecode.socofo.core.api.SourceTransformer;
import com.googlecode.socofo.core.api.SourceTypes;
import com.googlecode.socofo.core.api.TranslationJob;
import com.googlecode.socofo.core.exceptions.TranslationException;
import com.googlecode.socofo.rules.api.RuleSet;

/**
 * The basic implementation of a translation job.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class TranslationJobImpl implements TranslationJob {
	private SourceDestination dest = null;
	private RuleSet rules = null;
	private SourceRoot source = null;
	@Named("xml")
	@Inject
	private SourceTransformer xmlTransformer = null;
	private List<TranslationException> errors = null;

	/**
	 * Inits the job.
	 */
	public TranslationJobImpl() {
		errors = new ArrayList<TranslationException>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDestination(SourceDestination dest) {
		this.dest = dest;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRule(RuleSet r) {
		rules = r;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSource(SourceRoot sourceCode) {
		source = sourceCode;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		String result = "";
		SourceTransformer tr = null;
		if (source.getType() == SourceTypes.XML) {
			tr = xmlTransformer;
		}
		try {
			tr.parseContent(source.getContent());
			tr.setRules(rules);
			tr.performTranslation();
			result = tr.getResult();
			dest.writeContent(result, "utf-8");
		} catch (TranslationException e) {
			errors.add(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TranslationException> getErrors() {
		return errors;
	}

}
