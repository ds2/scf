/**
 * 
 */
package ds2.enterspace.rules.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.InputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.socofo.common.modules.CommonsInjectionPlan;
import com.google.inject.Guice;
import com.google.inject.Injector;

import ds2.enterspace.rules.api.RulesLoader;
import ds2.enterspace.rules.api.XmlFormatRules;
import ds2.enterspace.rules.modules.RulesInjectionPlan;

/**
 * @author kaeto23
 * 
 */
public class RulesLoaderImplTest {
	private RulesLoader to = null;

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
		Injector oj = Guice.createInjector(new CommonsInjectionPlan(),
				new RulesInjectionPlan());
		to = oj.getInstance(RulesLoader.class);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link ds2.enterspace.rules.impl.RulesLoaderImpl#loadFormatRules(java.io.InputStream)}
	 * .
	 */
	@Test
	public final void testLoadFormatRules() {
		assertNotNull(to);
		assertNull(to.loadFormatRules(null));
		InputStream is = getClass().getResourceAsStream("/xmllayout.xml");
		assertNotNull(is);
		XmlFormatRules rules = to.loadFormatRules(is);
		assertNotNull(rules);
	}

}
