/**
 * 
 */
package ds2.enterspace.core.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ds2.enterspace.rules.api.CommonAttributes;

/**
 * @author kaeto23
 * 
 */
public class SourceWriterImplTest {
	/**
	 * The test object
	 */
	private SourceWriterImpl to = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		to = new SourceWriterImpl();
		to.setCommonAttributes(getAttributes());
		to.prepare();
	}

	/**
	 * Returns some attributes to use
	 * 
	 * @return the common attributes
	 */
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
	 * Test method for
	 * {@link ds2.enterspace.core.impl.SourceWriterImpl#addLine(int, java.lang.String)}
	 * 
	 */
	@Test
	public final void testAddLine() {
		to.addLine(-1, null);
		assertEquals("", to.getResult());
		to.addLine(0, "hello");
		to.finish();
		assertEquals("hello\n", to.getResult());
	}

	/**
	 * Test method for
	 * {@link ds2.enterspace.core.impl.SourceWriterImpl#addToLine(java.lang.String)}
	 * .
	 */
	@Test
	public final void testAddToLineString() {
		assertFalse(to.addToLine(null));
		to.finish();
		assertEquals("", to.getResult());
		assertTrue(to.addToLine("hi"));
		assertEquals("hi", to.getCurrentLine());
		to.prepare();
		assertTrue(to.addToLine("hello, "));
		assertTrue(to.addToLine("world"));
		to.commitLine(true);
		assertEquals("hello, world\n", to.getResult());
	}

	/**
	 * Test method for
	 * {@link ds2.enterspace.core.impl.SourceWriterImpl#addToLine(int, java.lang.String)}
	 * .
	 */
	@Test
	public final void testAddToLineIntString() {
		to.addToLine(-1, null);
		to.finish();
		assertEquals("", to.getResult());
	}

	/**
	 * Test method for
	 * {@link ds2.enterspace.core.impl.SourceWriterImpl#setCommonAttributes(ds2.enterspace.rules.api.CommonAttributes)}
	 * .
	 */
	@Test
	public final void testSetCommonAttributes() {
		to.setCommonAttributes(null);
	}

	/**
	 * Test method for
	 * {@link ds2.enterspace.core.impl.SourceWriterImpl#commitLine(boolean)}.
	 */
	@Test
	public final void testCommitLine() {
		to.commitLine(false);
		assertEquals("", to.getResult());
		to.addLine(0, "hello this is a longer line");
		assertFalse(to.commitLine(false));
	}

}
