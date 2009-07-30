/**
 * 
 */
package ds2.enterspace.core.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ds2.enterspace.rules.api.CommonAttributes;

/**
 * @author kaeto23
 * 
 */
public class SourceWriterImplTest {
	private SourceWriterImpl to = null;

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
		to = new SourceWriterImpl();
		to.setCommonAttributes(getAttributes());
		to.prepare();
	}

	private CommonAttributes getAttributes() {
		return new CommonAttributes() {

			@Override
			public int getMaxLinewidth() {
				return 10;
			}

			@Override
			public String getIndentSequence() {
				return "  ";
			}
		};
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link ds2.enterspace.core.impl.SourceWriterImpl#addLine(int, java.lang.String)}
	 * 
	 */
	@Test
	public final void testAddLine() {
		to.addLine(-1, null);
		assertEquals("", to.getResult());
	}

	/**
	 * Test method for
	 * {@link ds2.enterspace.core.impl.SourceWriterImpl#addToLine(java.lang.String)}
	 * .
	 */
	@Test
	public final void testAddToLineString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link ds2.enterspace.core.impl.SourceWriterImpl#writeLine(java.lang.String)}
	 * .
	 */
	@Test
	public final void testWriteLine() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link ds2.enterspace.core.impl.SourceWriterImpl#addToLine(int, java.lang.String)}
	 * .
	 */
	@Test
	public final void testAddToLineIntString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link ds2.enterspace.core.impl.SourceWriterImpl#setCommonAttributes(ds2.enterspace.rules.api.CommonAttributes)}
	 * .
	 */
	@Test
	public final void testSetCommonAttributes() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link ds2.enterspace.core.impl.SourceWriterImpl#commitLine(boolean)}.
	 */
	@Test
	public final void testCommitLine() {
		fail("Not yet implemented"); // TODO
	}

}
