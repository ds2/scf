/**
 * 
 */
package ds2.enterspace.rules.api;

import java.util.List;

/**
 * All the formatter rules for XML files
 * 
 * @author kaeto23
 * 
 */
public interface XmlFormatRules {
	CommonAttributes getCommonAttributes();

	List<NamespaceRule> getNamespaceRules();

	Boolean getSeparateAttributesPerLine();

	Boolean getAlignFinalBracketOnNewline();

	Boolean getClearBlanklines();

	Boolean getInsertWsAtEmptyElement();

	Boolean getSortAttributes();

	TextBlocks getTextBlocks();
}
