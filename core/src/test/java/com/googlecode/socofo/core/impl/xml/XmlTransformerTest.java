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
package com.googlecode.socofo.core.impl.xml;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.socofo.common.modules.CommonsInjectionPlan;
import com.googlecode.socofo.core.api.FileDestination;
import com.googlecode.socofo.core.api.LineHandler;
import com.googlecode.socofo.core.api.SourceRoot;
import com.googlecode.socofo.core.api.StreamRoot;
import com.googlecode.socofo.core.api.StringRoot;
import com.googlecode.socofo.core.exceptions.LoadingException;
import com.googlecode.socofo.core.impl.SourceWriterImpl;
import com.googlecode.socofo.core.impl.modules.CoreInjectionPlan;
import com.googlecode.socofo.rules.api.RuleSet;
import com.googlecode.socofo.rules.api.RulesLoader;
import com.googlecode.socofo.rules.api.XmlFormatRules;
import com.googlecode.socofo.rules.modules.RulesInjectionPlan;

/**
 * class to test the xml transformer.
 * 
 * @author Dirk Strauss
 * @version 1.0
 * 
 */
public class XmlTransformerTest {
	/**
	 * a logger
	 */
	private static final transient Logger log = Logger
			.getLogger(XmlTransformerTest.class.getName());
	/**
	 * the transformer
	 */
	private XmlTransformer to = null;
	/**
	 * The rules loader
	 */
	private RulesLoader rulesLoader = null;
	/**
	 * the xml transformation rules
	 */
	private XmlFormatRules formatRules = null;
	/**
	 * The stream root
	 */
	private StreamRoot sr = null;
	/**
	 * the destination to write a result to
	 */
	private FileDestination dest = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		to = new XmlTransformer();
		to.setTestSw(new SourceWriterImpl());
		Injector ij = Guice.createInjector(new CommonsInjectionPlan(),
				new RulesInjectionPlan(), new CoreInjectionPlan());
		rulesLoader = ij.getInstance(RulesLoader.class);
		formatRules = rulesLoader.loadFormatRules(getClass()
				.getResourceAsStream("/xmlconfig.xml"));
		to.setTestLh(ij.getInstance(LineHandler.class));
		to.loadRules(new RuleSet() {

			@Override
			public XmlFormatRules getXmlFormatRules() {
				return formatRules;
			}
		});
		sr = ij.getInstance(StreamRoot.class);
		dest = ij.getInstance(FileDestination.class);
		dest.setFile(new File("target/sample1.result.xml"));
	}

	/**
	 * Test method for
	 * {@link com.googlecode.socofo.core.impl.xml.XmlTransformer#loadRules(com.googlecode.socofo.rules.api.RuleSet)}
	 * .
	 */
	@Test
	public final void testLoadRules() {
		to.loadRules(null);
		to.loadRules(new RuleSet() {

			@Override
			public XmlFormatRules getXmlFormatRules() {
				return formatRules;
			}
		});
	}

	/**
	 * Test method for
	 * {@link com.googlecode.socofo.core.impl.xml.XmlTransformer#parseContent(java.lang.String)}
	 * .
	 */
	@Test
	public final void testParseContent() {
		to.parseContent(null);
		to.parseContent("");
	}

	/**
	 * Test method for
	 * {@link com.googlecode.socofo.core.impl.xml.XmlTransformer#performTranslation()}
	 * .
	 */
	@Test
	public final void testPerformTranslation() {
		assertNotNull(sr);
		try {
			sr.loadStream(getClass().getResourceAsStream("/sample1.xml"),
					"utf-8");
		} catch (LoadingException e) {
			fail(e.getLocalizedMessage());
		}
		String xmlSample = sr.getContent();
		assertNotNull(xmlSample);
		to.parseContent(xmlSample);
		to.performTranslation();
		String result = to.getResult();
		assertNotNull(result);
		assertTrue(result.length() > 0);
		log.info("Result is\n" + result);
		dest.writeContent(result, "utf-8");
	}

	/**
	 * Simple translation test
	 */
	@Test
	public final void testTranslation1() {
		String xmlSample = "<a><b/></a>";
		assertNotNull(xmlSample);
		to.parseContent(xmlSample);
		to.performTranslation();
		String result = to.getResult();
		assertNotNull(result);
		assertEquals("<a>\n  <b/>\n</a>", result);
	}

	@Test
	public final void testTranslation2() {
		String xmlSample = "<a><b></b></a>";
		assertNotNull(xmlSample);
		to.parseContent(xmlSample);
		to.performTranslation();
		String result = to.getResult();
		assertNotNull(result);
		assertEquals("<a>\n  <b></b>\n</a>", result);
	}

	@Test
	public final void testTranslation3() {
		String xmlSample = "<a><b>test</b></a>";
		assertNotNull(xmlSample);
		to.parseContent(xmlSample);
		to.performTranslation();
		String result = to.getResult();
		assertNotNull(result);
		assertEquals("<a>\n  <b>test</b>\n</a>", result);
	}

}
