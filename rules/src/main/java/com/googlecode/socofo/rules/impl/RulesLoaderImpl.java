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
package com.googlecode.socofo.rules.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.googlecode.socofo.common.api.IOHelper;
import com.googlecode.socofo.rules.api.RuleSet;
import com.googlecode.socofo.rules.api.RulesLoader;
import com.googlecode.socofo.rules.api.XmlFormatRules;

/**
 * @author kaeto23
 * 
 */
@Singleton
public class RulesLoaderImpl implements RulesLoader {
	/**
	 * The JAXB context
	 */
	private JAXBContext jb = null;
	/**
	 * A logger
	 */
	private static final transient Logger log = Logger
			.getLogger(RulesLoaderImpl.class.getName());
	/**
	 * the io helper
	 */
	@Inject
	private IOHelper iohelper = null;
	@Named("connectTimeout")
	private int connectTimeout = 20000;
	@Named("readTimeout")
	private int readTimeout = 20000;

	/**
	 * 
	 */
	public RulesLoaderImpl() {
		try {
			jb = JAXBContext.newInstance(RuleSetXml.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XmlFormatRules loadFormatRules(InputStream is) {
		if (is == null) {
			log.severe("No inputstream given -> returning null");
			return null;
		}
		BufferedInputStream bis = new BufferedInputStream(is);
		XmlFormatRules rc = null;
		try {
			Unmarshaller um = jb.createUnmarshaller();
			Object o = um.unmarshal(is);
			rc = XmlFormatRules.class.cast(o);
		} catch (JAXBException e) {
			log.throwing(RulesLoaderImpl.class.getName(), "loadFormatRules", e);
		} finally {
			iohelper.closeInputstream(bis);
			iohelper.closeInputstream(is);
		}
		return rc;
	}

	@Override
	public RuleSet loadRules(InputStream is) {
		if (is == null) {
			log.severe("No inputstream given -> returning null");
			return null;
		}
		BufferedInputStream bis = new BufferedInputStream(is);
		RuleSet rc = null;
		try {
			Unmarshaller um = jb.createUnmarshaller();
			Object o = um.unmarshal(is);
			if (!(o instanceof RuleSetXml)) {
				log
						.severe("The given stream does not contain a ruleset definition!");
			} else {
				rc = RuleSetXml.class.cast(o);
			}
		} catch (JAXBException e) {
			log.throwing(RulesLoaderImpl.class.getName(), "loadRules", e);
		} finally {
			iohelper.closeInputstream(bis);
			iohelper.closeInputstream(is);
		}
		return rc;
	}

	@Override
	public RuleSet loadRulesFromUrl(URL formatterXml) {
		try {
			URLConnection urlConn = formatterXml.openConnection();
			urlConn.setConnectTimeout(connectTimeout);
			urlConn.setReadTimeout(readTimeout);
			urlConn.connect();
			InputStream is = urlConn.getInputStream();
			return loadRules(is);
		} catch (IOException e) {
			log
					.throwing(RulesLoaderImpl.class.getName(),
							"loadRulesFromUrl", e);
		}
		return null;
	}

}
