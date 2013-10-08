/*
 * SoCoFo - Another source code formatter
 * Copyright (C) 2013  Dirk Strauss
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

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.socofo.common.api.IOHelper;
import com.googlecode.socofo.rules.api.RulesConfig;
import com.googlecode.socofo.rules.api.v1.RuleSet;
import com.googlecode.socofo.rules.api.v1.RulesLoader;
import com.googlecode.socofo.rules.api.v1.XmlFormatRules;
import com.googlecode.socofo.rules.impl.v1.RuleSetXml;

/**
 * The rules loader implementation.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
@Singleton
public class RulesLoaderImpl implements RulesLoader {
    /**
     * The JAXB context.
     */
    private JAXBContext jb = null;
    /**
     * A logger.
     */
    private static final transient Logger LOG = LoggerFactory.getLogger(RulesLoaderImpl.class);
    /**
     * the io helper.
     */
    @Inject
    private IOHelper iohelper = null;
    /**
     * The rules config to use.
     */
    @Inject
    private RulesConfig config;
    
    /**
     * Inits the impl.
     */
    public RulesLoaderImpl() {
        try {
            jb = JAXBContext.newInstance(RuleSetXml.class);
        } catch (JAXBException e) {
            LOG.error("Error when loading the XML contracts!", e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public XmlFormatRules loadFormatRules(final InputStream is) {
        if (is == null) {
            LOG.error("No inputstream given -> returning null");
            return null;
        }
        BufferedInputStream bis = new BufferedInputStream(is);
        XmlFormatRules rc = null;
        try {
            Unmarshaller um = jb.createUnmarshaller();
            Object o = um.unmarshal(is);
            rc = XmlFormatRules.class.cast(o);
        } catch (JAXBException e) {
            LOG.info("XML parser error", e);
        } finally {
            iohelper.closeInputstream(bis);
            iohelper.closeInputstream(is);
        }
        return rc;
    }
    
    @Override
    public RuleSet loadRules(final InputStream is) {
        if (is == null) {
            LOG.error("No inputstream given -> returning null");
            return null;
        }
        BufferedInputStream bis = new BufferedInputStream(is);
        RuleSet rc = null;
        try {
            Unmarshaller um = jb.createUnmarshaller();
            Object o = um.unmarshal(is);
            if (!(o instanceof RuleSetXml)) {
                LOG.error("The given stream does not contain a ruleset definition!");
            } else {
                rc = RuleSetXml.class.cast(o);
            }
        } catch (JAXBException e) {
            LOG.info("XML parser error", e);
        } finally {
            iohelper.closeInputstream(bis);
            iohelper.closeInputstream(is);
        }
        return rc;
    }
    
    @Override
    public RuleSet loadRulesFromUrl(final URL formatterXml) {
        InputStream is = null;
        RuleSet rc = null;
        try {
            URLConnection urlConn = formatterXml.openConnection();
            urlConn.setConnectTimeout(config.getConnectTimeout());
            urlConn.setReadTimeout(config.getReadTimeout());
            urlConn.connect();
            is = urlConn.getInputStream();
            rc = loadRules(is);
        } catch (IOException e) {
            LOG.error("loadRulesFromUrl", e);
        } finally {
            iohelper.closeInputstream(is);
        }
        return rc;
    }
    
}
