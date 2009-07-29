/**
 * 
 */
package ds2.enterspace.rules.impl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import ds2.enterspace.rules.api.CommonAttributes;

/**
 * @author kaeto23
 * 
 */
@XmlType(name = "CommonAttributesType", namespace = "http://www.ds2/ns/socofo")
public class CommonAttributesXml implements CommonAttributes {
	@XmlElement(required = true)
	private int maxLineWidth = 0;
	@XmlElement(required = true)
	private String indentSequence = null;

	@Override
	public String getIndentSequence() {
		return indentSequence;
	}

	@Override
	public int getMaxLinewidth() {
		return maxLineWidth;
	}

}
