/**
 * 
 */
package com.googlecode.socofo.core.impl.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for the treeHandlerImpl.
 * 
 * @author Dirk Strauss
 * @version 1.0
 */
public class TreeHandlerImplTest {
	/**
	 * The test object.
	 */
	private TreeHandlerImpl to = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		to = new TreeHandlerImpl();
	}

	/**
	 * Test method for
	 * {@link com.googlecode.socofo.core.impl.xml.TreeHandlerImpl#setLevel(com.googlecode.socofo.core.impl.xml.XmlObject)}
	 * .
	 */
	@Test
	public final void testSetLevel() {
		assertNotNull(to);
		to.setLevel(null);
		StartElement xo = new StartElement("a");
		to.setLevel(xo);
		assertEquals(1, xo.getLevel());
		EmptyElement empty1 = new EmptyElement("b");
		to.setLevel(empty1);
		assertEquals(2, empty1.getLevel());
		EndElement endEl = new EndElement("a");
		to.setLevel(endEl);
		assertEquals(1, endEl.getLevel());
	}

	@Test
	public final void testSetLevel2() {
		assertNotNull(to);
		to.setLevel(null);
		StartElement xo = new StartElement("a");
		to.setLevel(xo);
		assertEquals(1, xo.getLevel());
		StartElement empty1 = new StartElement("b");
		to.setLevel(empty1);
		assertEquals(2, empty1.getLevel());
		EndElement empty2 = new EndElement("b");
		to.setLevel(empty2);
		assertEquals(2, empty2.getLevel());
		EndElement endEl = new EndElement("a");
		to.setLevel(endEl);
		assertEquals(1, endEl.getLevel());
	}

	@Test
	public final void testSetLevel3() {
		assertNotNull(to);
		to.setLevel(null);
		StartElement xo = new StartElement("a");
		to.setLevel(xo);
		assertEquals(1, xo.getLevel());
		StartElement empty1 = new StartElement("b");
		to.setLevel(empty1);
		assertEquals(2, empty1.getLevel());
		Text txt = new Text();
		to.setLevel(txt);
		assertEquals(2, txt.getLevel());
		EndElement empty2 = new EndElement("b");
		to.setLevel(empty2);
		assertEquals(2, empty2.getLevel());
		EndElement endEl = new EndElement("a");
		to.setLevel(endEl);
		assertEquals(1, endEl.getLevel());
	}

	@Test
	public final void testSetLevel4() {
		assertNotNull(to);
		to.setLevel(null);
		StartElement xo = new StartElement("a");
		to.setLevel(xo);
		assertEquals(1, xo.getLevel());
		StartElement empty1 = new StartElement("b");
		to.setLevel(empty1);
		assertEquals(2, empty1.getLevel());
		Text txt = new Text();
		to.setLevel(txt);
		assertEquals(2, txt.getLevel());
		EndElement empty2 = new EndElement("b");
		to.setLevel(empty2);
		assertEquals(2, empty2.getLevel());
		Comment c = new Comment();
		to.setLevel(c);
		assertEquals(2, c.getLevel());
		EndElement endEl = new EndElement("a");
		to.setLevel(endEl);
		assertEquals(1, endEl.getLevel());
	}

	@Test
	public final void testSetLevel5() {
		assertNotNull(to);
		to.setLevel(null);
		StartElement xo = new StartElement("a");
		to.setLevel(xo);
		assertEquals(1, xo.getLevel());
		Comment c = new Comment();
		to.setLevel(c);
		assertEquals(2, c.getLevel());
		EndElement endEl = new EndElement("a");
		to.setLevel(endEl);
		assertEquals(1, endEl.getLevel());
	}

	@Test
	public final void testSetLevel6() {
		assertNotNull(to);
		to.setLevel(null);
		StartElement xo = new StartElement("a");
		to.setLevel(xo);
		assertEquals(1, xo.getLevel());
		StartElement empty1 = new StartElement("b");
		to.setLevel(empty1);
		assertEquals(2, empty1.getLevel());
		Text txt = new Text();
		to.setLevel(txt);
		assertEquals(2, txt.getLevel());
		EndElement empty2 = new EndElement("b");
		to.setLevel(empty2);
		assertEquals(2, empty2.getLevel());
		Comment c = new Comment();
		to.setLevel(c);
		assertEquals(2, c.getLevel());
		ProcessingInstruction pi = new ProcessingInstruction();
		to.setLevel(pi);
		assertEquals(2, pi.getLevel());
		EndElement endEl = new EndElement("a");
		to.setLevel(endEl);
		assertEquals(1, endEl.getLevel());
	}

}
