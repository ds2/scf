/**
 * 
 */
package ds2.enterspace.rules.api;

import javax.xml.bind.annotation.XmlSeeAlso;

import ds2.enterspace.rules.impl.NamespaceRuleXml;

/**
 * @author kaeto23
 * 
 */
@XmlSeeAlso( { NamespaceRuleXml.class })
public interface NamespaceRule {
	public String getPrefix();

	public String getNamespace();

	public String getNamespaceUrl();
}
