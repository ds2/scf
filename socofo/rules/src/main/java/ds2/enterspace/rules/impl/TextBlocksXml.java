/**
 * 
 */
package ds2.enterspace.rules.impl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import ds2.enterspace.rules.api.TextBlocks;

/**
 * @author kaeto23
 * 
 */
@XmlType(name = "TextBlocksType", namespace = "http://www.ds2/ns/socofo")
public class TextBlocksXml implements TextBlocks {
	@XmlElement
	private String header;
	@XmlElement
	private String footer;

	@Override
	public String getFooter() {
		return footer;
	}

	@Override
	public String getHeader() {
		return header;
	}

}
