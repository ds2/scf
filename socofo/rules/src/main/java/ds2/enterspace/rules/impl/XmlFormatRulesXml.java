/**
 * 
 */
package ds2.enterspace.rules.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import ds2.enterspace.rules.api.CommonAttributes;
import ds2.enterspace.rules.api.NamespaceRule;
import ds2.enterspace.rules.api.TextBlocks;
import ds2.enterspace.rules.api.XmlFormatRules;

/**
 * @author kaeto23
 * 
 */
@XmlRootElement(name = "xml-format", namespace = "http://www.ds2/ns/socofo")
@XmlSeeAlso( { NamespaceRuleXml.class, CommonAttributesXml.class })
public class XmlFormatRulesXml implements XmlFormatRules {
	@XmlElement(required = true, type = CommonAttributesXml.class)
	private CommonAttributes commonAttributes = null;
	@XmlElement(required = false)
	private Boolean separateAttributesPerLine = false;
	@XmlElement(required = false)
	private Boolean alignFinalBracketOnNewline = false;
	@XmlElement(required = false)
	private Boolean clearBlankLines = false;
	@XmlElement(required = false)
	private Boolean insertWsAtEmptyElement = false;
	@XmlElement
	private Boolean sortAttributes;
	@XmlElements(value = { @XmlElement(type = NamespaceRuleXml.class, name = "enforceNamespace") })
	private List<NamespaceRule> namespaceRules = null;
	@XmlElement(type = TextBlocksXml.class)
	private TextBlocks textBlocks = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean getAlignFinalBracketOnNewline() {
		return alignFinalBracketOnNewline;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean getClearBlanklines() {
		return clearBlankLines;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CommonAttributes getCommonAttributes() {
		return commonAttributes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean getInsertWsAtEmptyElement() {
		return insertWsAtEmptyElement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<NamespaceRule> getNamespaceRules() {
		return namespaceRules;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean getSeparateAttributesPerLine() {
		return separateAttributesPerLine;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TextBlocks getTextBlocks() {
		return textBlocks;
	}

	@Override
	public Boolean getSortAttributes() {
		return sortAttributes;
	}

}
