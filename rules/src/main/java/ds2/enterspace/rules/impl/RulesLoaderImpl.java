/**
 * 
 */
package ds2.enterspace.rules.impl;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.google.code.socofo.common.api.IOHelper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import ds2.enterspace.rules.api.RulesLoader;
import ds2.enterspace.rules.api.XmlFormatRules;

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

	/**
	 * 
	 */
	public RulesLoaderImpl() {
		try {
			jb = JAXBContext.newInstance(XmlFormatRulesXml.class);
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

}
