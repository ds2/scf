/**
 * 
 */
package ds2.enterspace.core.impl.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import com.google.code.socofo.common.api.IOHelper;
import com.google.inject.Inject;

import ds2.enterspace.core.api.FileRoot;
import ds2.enterspace.core.api.SourceTypeDetector;
import ds2.enterspace.core.api.SourceTypes;
import ds2.enterspace.core.exceptions.LoadingException;

/**
 * Implementation for a file source root
 * 
 * @author kaeto23
 * 
 */
public class FileRootImpl implements FileRoot {
	/**
	 * A logger
	 */
	private static final transient Logger log = Logger
			.getLogger(FileRootImpl.class.getName());
	/**
	 * The content of the file
	 */
	private String content = null;
	/**
	 * The source type detector.
	 */
	@Inject
	private SourceTypeDetector detector;
	/**
	 * The type of the source code
	 */
	private SourceTypes type = null;
	/**
	 * The io helper
	 */
	@Inject
	private IOHelper iohelper = null;

	/**
	 * Loads the given file
	 * 
	 * @param f
	 *            the file to load
	 * @throws LoadingException
	 *             if an error occurred
	 */
	public void loadFile(File f) throws LoadingException {
		if (f == null) {
			log.warning("No file given!");
			return;
		}
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			StringBuffer sb = new StringBuffer();
			while (true) {
				String l = br.readLine();
				if (l == null) {
					break;
				}
				sb.append(l).append("\n");
			}
			content = sb.toString();
			type = detector.guessTypeByFilename(f.getAbsolutePath());
		} catch (FileNotFoundException e) {
			log.throwing(FileRootImpl.class.getName(), "loadFile", e);
			throw new LoadingException("File not found: " + f.getAbsolutePath());
		} catch (IOException e) {
			log.throwing(FileRootImpl.class.getName(), "loadFile", e);
			throw new LoadingException("IO error when loading the file: "
					+ e.getLocalizedMessage());
		} finally {
			iohelper.closeReader(fr);
			iohelper.closeReader(br);
		}
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
		return type;
	}

}
