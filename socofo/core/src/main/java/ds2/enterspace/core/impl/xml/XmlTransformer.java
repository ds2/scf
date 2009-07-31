/**
 * 
 */
package ds2.enterspace.core.impl.xml;

import java.util.logging.Logger;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.Token;

import com.google.inject.Inject;

import ds2.enterspace.core.api.SourceTransformer;
import ds2.enterspace.core.api.SourceWriter;
import ds2.enterspace.rules.api.RuleSet;
import ds2.enterspace.rules.api.XmlFormatRules;
import ds2.socofo.antlr.XmlGrammar;

/**
 * The xml transformer
 * 
 * @author kaeto23
 * 
 */
public class XmlTransformer implements SourceTransformer {
	/**
	 * the source writer to write the XML result to
	 */
	@Inject
	private SourceWriter sw = null;
	/**
	 * The rules to transform the content
	 */
	private XmlFormatRules rules = null;
	/**
	 * A logger
	 */
	private static final transient Logger log = Logger
			.getLogger(XmlTransformer.class.getName());
	/**
	 * the xml grammar that has been loaded.
	 */
	private XmlGrammar grammar = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getResult() {
		String rc = sw.getResult();
		return rc;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void loadRules(RuleSet r) {
		if (r == null) {
			log.warning("No rules given!");
			return;
		}
		rules = r.getXmlFormatRules();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void parseContent(String s) {
		if (s == null || s.length() <= 0) {
			log.warning("No content given!");
			return;
		}
		ANTLRStringStream ss = new ANTLRStringStream(s);
		CharStream input = ss;
		grammar = new XmlGrammar(input);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void performTranslation() {
		log.entering(XmlTransformer.class.getName(), "performTranslation");
		if (sw == null) {
			log.severe("No source writer has been injected!");
			return;
		}
		log.finer("preparing source writer");
		sw.prepare();
		if (rules == null || rules.getCommonAttributes() == null) {
			log.severe("No rules have been loaded!");
			return;
		}
		log.finer("Setting rules and attributes");
		sw.setCommonAttributes(rules.getCommonAttributes());
		Token token;
		int currentIndent = 0;
		log.finer("Starting token run");
		while ((token = grammar.nextToken()) != Token.EOF_TOKEN) {
			log.finest("Token (" + token.getType() + "): " + token.getText());
			switch (token.getType()) {
			case XmlGrammar.PI_START:
				sw.addToLine(currentIndent, "<?");
				break;
			case XmlGrammar.PI_STOP:
				sw.addToLine(currentIndent, "?>");
				break;
			case XmlGrammar.WS:
				sw.addLine(currentIndent, " ");
				break;
			case XmlGrammar.GENERIC_ID: // attribute or element name
				sw.addLine(currentIndent, token.getText());
				break;
			default:
				log.warning("unknown token: type=" + token.getType()
						+ " with content " + token.getText());
			}
		}
		log.finer("finishing the result");
		sw.finish();
		log.exiting(XmlTransformer.class.getName(), "performTranslation");
	}

	/**
	 * Sets the source writer. This method here is usually called in a test
	 * case. DO NOT USE IT IN PRODUCTION!!
	 * 
	 * @param s
	 *            the source writer to use
	 */
	protected void setTestSw(SourceWriter s) {
		this.sw = s;
	}

}
