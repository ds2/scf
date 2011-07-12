/**
 * SoCoFo Source Code Formatter
 * Copyright (C) 2009 Dirk Strauss <lexxy23@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package com.googlecode.socofo.core.impl.xml;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.googlecode.socofo.core.api.LineHandler;
import com.googlecode.socofo.core.api.SourceTransformer;
import com.googlecode.socofo.core.api.SourceWriter;
import com.googlecode.socofo.core.exceptions.TranslationException;
import com.googlecode.socofo.grammar.XmlGrammar;
import com.googlecode.socofo.grammar.XmlParser;
import com.googlecode.socofo.rules.api.v1.RuleSet;
import com.googlecode.socofo.rules.api.v1.XmlFormatRules;

/**
 * The xml transformer.
 * 
 * @author Dirk Strauss
 * @version 1.0
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
    private static final transient Logger log = LoggerFactory
        .getLogger(XmlTransformer.class);
    /**
     * the xml grammar that has been loaded.
     */
    private XmlGrammar grammar = null;
    /**
     * The line handler.
     */
    @Inject
    private LineHandler lh = null;
    /**
     * The tree handler to set the levels of the various xml objects
     */
    @Inject
    private TreeHandler treeHandler = null;
    /**
     * The loaded rule set.
     */
    private RuleSet ruleSet;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getResult() {
        final String rc = sw.getResult();
        return rc;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setRules(final RuleSet r) {
        if (r == null) {
            throw new IllegalArgumentException("Ruleset not set!");
        }
        if (r.getXmlFormatRules() == null) {
            throw new IllegalArgumentException(
                "The loaded rules do not contain XML rules!");
        }
        rules = r.getXmlFormatRules();
        ruleSet = r;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void parseContent(final String s) {
        if (s == null || s.length() <= 0) {
            log.warn("No content given!");
            return;
        }
        final ANTLRStringStream ss = new ANTLRStringStream(s);
        final CharStream input = ss;
        grammar = new XmlGrammar(input);
        TokenStream tr = null;
        tr = new CommonTokenStream(grammar);
        new XmlParser(tr);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void performTranslation() throws TranslationException {
        log.debug("entering");
        if (sw == null) {
            throw new TranslationException(
                "No source writer has been injected!");
        }
        log.debug("preparing source writer");
        sw.prepare();
        if (rules == null) {
            throw new TranslationException("No rules have been loaded!");
        }
        if (rules.getCommonAttributes() == null) {
            throw new TranslationException("No CA rules have been loaded!");
        }
        log.debug("Setting rules and attributes");
        sw.setCommonAttributes(rules.getCommonAttributes());
        lh.setTabSize(rules.getCommonAttributes().getTabSize());
        Token token;
        int currentIndent = 0;
        log.debug("Starting token run");
        XmlObject currentObject = null;
        XmlObject lastObject = null;
        while ((token = grammar.nextToken()) != Token.EOF_TOKEN) {
            log.debug("Token ({}) for this run: {}", token.getType(),
                token.getText());
            switch (token.getType()) {
                case XmlGrammar.PI_START:
                    currentObject = new ProcessingInstruction();
                    break;
                case XmlGrammar.PI_STOP:
                    // write Element
                    treeHandler.setLevel(currentObject);
                    currentObject.writeElement(lastObject, currentIndent, sw,
                        rules, lh);
                    lastObject = currentObject;
                    currentObject = null;
                    break;
                case XmlGrammar.WS:
                    if (currentObject != null) {
                        break;
                    }
                    sw.addToLine(" ");
                    break;
                case XmlGrammar.GENERIC_ID:
                    if (currentObject != null) {
                        if (currentObject.getElementName() == null) {
                            // this is an element name
                            currentObject.setElementName(token.getText());
                        } else {
                            // it is an attribute name
                            currentObject.setAttributName(token.getText());
                        }
                    }
                    break;
                case XmlGrammar.TAG_START_OPEN:
                    currentObject = new StartElement();
                    break;
                case XmlGrammar.TAG_CLOSE:
                    // write element
                    treeHandler.setLevel(currentObject);
                    currentObject.writeElement(lastObject, currentIndent, sw,
                        rules, lh);
                    if (!currentObject.isEndTag()) {
                        currentIndent++;
                    } else {
                        currentIndent--;
                    }
                    lastObject = currentObject;
                    currentObject = null;
                    break;
                case XmlGrammar.TAG_EMPTY_CLOSE:
                    currentObject.setEndSequence(token.getText());
                    treeHandler.setLevel(currentObject);
                    currentObject.writeElement(lastObject, currentIndent, sw,
                        rules, lh);
                    lastObject = currentObject;
                    currentObject = null;
                    break;
                case XmlGrammar.TAG_END_OPEN:
                    currentObject = new EndElement();
                    break;
                case XmlGrammar.ATTR_EQ:
                    // sw.addToLine(token.getText());
                    break;
                case XmlGrammar.ATTR_VALUE:
                    // sw.addToLine(token.getText());
                    if (currentObject != null) {
                        currentObject.setAttributValue(token.getText());
                    }
                    break;
                case XmlGrammar.PCDATA:
                    currentObject = new Text();
                    currentObject.setInnerContent(token.getText());
                    // insert WS detection here
                    // if (!currentObject.hasEmptyInnerContent()) {
                    treeHandler.setLevel(currentObject);
                    currentObject.writeElement(lastObject, currentIndent, sw,
                        rules, lh);
                    // }
                    lastObject = currentObject;
                    currentObject = null;
                    break;
                case XmlGrammar.CDATA_SECTION:
                    sw.addToLine(token.getText());
                    currentObject = null;
                    break;
                case XmlGrammar.COMMENT_SECTION:
                    currentObject = new Comment();
                    currentObject.setInnerContent(token.getText());
                    treeHandler.setLevel(currentObject);
                    currentObject.writeElement(lastObject, currentIndent, sw,
                        rules, lh);
                    lastObject = currentObject;
                    currentObject = null;
                    break;
                default:
                    log.warn("unknown token: type={} with content {}",
                        token.getType(), token.getText());
            }
        }
        log.debug("finishing the result");
        sw.finish();
        log.debug("exit");
    }
    
    /**
     * Sets the source writer. This method here is usually called in a test
     * case. DO NOT USE IT IN PRODUCTION!!
     * 
     * @param s
     *            the source writer to use
     */
    protected void setTestSw(final SourceWriter s) {
        this.sw = s;
    }
    
    /**
     * Sets the test line handler to perform some unit tests.
     * 
     * @param i
     *            the line handler to use for performing tests
     */
    public void setTestLh(final LineHandler i) {
        this.lh = i;
    }
    
    /**
     * Sets the test tree handler.
     * 
     * @param th
     *            the tree handler, used for tests
     */
    public void setTestTreehandler(final TreeHandler th) {
        treeHandler = th;
    }
    
    /**
     * Returns the xml format rules.
     * 
     * @return the xml format rules
     */
    public XmlFormatRules getRuleSet() {
        return rules;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public RuleSet getRules() {
        return ruleSet;
    }
    
}
