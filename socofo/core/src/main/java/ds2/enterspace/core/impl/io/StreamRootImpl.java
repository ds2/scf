/**
 * 
 */
package ds2.enterspace.core.impl.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.google.code.socofo.common.api.IOHelper;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import ds2.enterspace.core.api.SourceTypes;
import ds2.enterspace.core.api.StreamRoot;

/**
 * @author kaeto23
 * 
 */
public class StreamRootImpl implements StreamRoot {
	/**
	 * The content
	 */
	private String content = null;
	/**
	 * The iohelper
	 */
	@Inject
	private IOHelper iohelper = null;
	/**
	 * The default encoding to use
	 */
	@Inject
	@Named("defaultEncoding")
	private String encoding = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void loadStream(InputStream is, String enc) {
		if (is == null) {
			return;
		}
		if (enc != null) {
			this.encoding = enc;
		}
		BufferedInputStream bis = new BufferedInputStream(is);
		byte[] buffer = new byte[6000];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while (true) {
			int numRead = iohelper.read(bis, buffer);
			if (numRead < 0) {
				break;
			}
			baos.write(buffer, 0, numRead);
		}
		iohelper.closeInputstream(bis);
		iohelper.closeInputstream(is);
		iohelper.closeOutputstream(baos);
		content = iohelper.createString(baos.toByteArray(), encoding);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getContent() {
		return content;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SourceTypes getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
