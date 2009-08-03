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
package ds2.enterspace.core.impl.xml;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.socofo.common.modules.CommonsInjectionPlan;
import com.google.inject.Guice;
import com.google.inject.Injector;

import ds2.enterspace.core.api.StreamRoot;
import ds2.enterspace.core.api.TransformerDelegate;
import ds2.enterspace.core.exceptions.LoadingException;
import ds2.enterspace.core.impl.SourceWriterImpl;
import ds2.enterspace.core.modules.CoreInjectionPlan;
import ds2.enterspace.rules.api.RuleSet;
import ds2.enterspace.rules.api.RulesLoader;
import ds2.enterspace.rules.api.XmlFormatRules;
import ds2.enterspace.rules.modules.RulesInjectionPlan;

/**
 * @author kaeto23
 * 
 */
public class XmlTransformerTest {
	private static final transient Logger log = Logger
			.getLogger(XmlTransformerTest.class.getName());
	private XmlTransformer to = null;
	private RulesLoader rulesLoader = null;
	private XmlFormatRules formatRules = null;
	private TransformerDelegate transformerDelegate = null;
	private StreamRoot sr = null;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

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
		to.loadRules(new RuleSet() {

			@Override
			public XmlFormatRules getXmlFormatRules() {
				return formatRules;
			}
		});
		sr = ij.getInstance(StreamRoot.class);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link ds2.enterspace.core.impl.xml.XmlTransformer#loadRules(ds2.enterspace.rules.api.RuleSet)}
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
	 * {@link ds2.enterspace.core.impl.xml.XmlTransformer#parseContent(java.lang.String)}
	 * .
	 */
	@Test
	public final void testParseContent() {
		to.parseContent(null);
		to.parseContent("");
	}

	/**
	 * Test method for
	 * {@link ds2.enterspace.core.impl.xml.XmlTransformer#performTranslation()}.
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
	}

}
