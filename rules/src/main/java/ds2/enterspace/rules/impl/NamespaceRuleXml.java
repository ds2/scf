/**
 * 
 */
package ds2.enterspace.rules.impl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import ds2.enterspace.rules.api.NamespaceRule;

/**
 * @author kaeto23
 * 
 */
@XmlType(name = "NamespaceRuleType", namespace = "http://www.ds2/ns/socofo")
public class NamespaceRuleXml implements NamespaceRule {
	@XmlElement(required = true)
	private String namespace;
	@XmlElement(required = true)
	private String prefix;
	@XmlElement(required = false, nillable = true)
	private String namespaceUrl;

	/**
	 * 
	 */
	public NamespaceRuleXml() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getNamespace() {
		return namespace;
	}

	@Override
	public String getNamespaceUrl() {
		return namespaceUrl;
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

}
