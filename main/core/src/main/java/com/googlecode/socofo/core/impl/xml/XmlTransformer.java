/*
 * SoCoFo - Another source code formatter
 * Copyright (C) 2013  Dirk Strauss
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

import javax.inject.Inject;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * the source writer to write the XML result to.
     */
    @Inject
    private SourceWriter sw;
    /**
     * The rules to transform the content.
     */
    private XmlFormatRules rules;
    /**
     * A logger.
     */
    private static final transient Logger LOG = LoggerFactory
        .getLogger(XmlTransformer.class);
    /**
     * the xml grammar that has been loaded.
     */
    private XmlGrammar grammar;
    /**
     * The line handler.
     */
    @Inject
    private LineHandler lh;
    /**
     * The tree handler to set the levels of the various xml objects.
     */
    @Inject
    private TreeHandler treeHandler;
    /**
     * The loaded rule set.
     */
    private RuleSet ruleSet;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final String getResult() {
        final String rc = sw.getResult();
        LOG.debug("result of the transformation is\n{}", rc);
        return rc;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final void setRules(final RuleSet r) {
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
    public final void parseContent(final String s) {
        if ((s == null) || (s.length() <= 0)) {
            LOG.warn("No content given!");
            return;
        }
        LOG.debug("given string to parse is {}", s);
        ANTLRInputStream antlrIS = new ANTLRInputStream(s);
        final CharStream input = antlrIS;
        grammar = new XmlGrammar(input);
        final TokenStream tr = new CommonTokenStream(grammar);
        new XmlParser(tr);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void performTranslation() throws TranslationException {
        LOG.debug("entering");
        if (sw == null) {
            throw new TranslationException(
                "No source writer has been injected!");
        }
        LOG.debug("preparing source writer");
        sw.prepare();
        if (rules == null) {
            throw new TranslationException("No rules have been loaded!");
        }
        if (rules.getCommonAttributes() == null) {
            throw new TranslationException("No CA rules have been loaded!");
        }
        LOG.debug("Setting rules and attributes");
        sw.setCommonAttributes(rules.getCommonAttributes());
        lh.setTabSize(rules.getCommonAttributes().getTabSize());
        Token token;
        int currentIndent = 0;
        LOG.debug("Starting token run");
        XmlObject currentObject = null;
        XmlObject lastObject = null;
        while (((token = grammar.nextToken()) != null)
            && (token.getType() != XmlGrammar.EOF)) {
            LOG.debug("Token ({}) for this run: {}", token.getType(),
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
                    LOG.warn("unknown token: type={} with content {}",
                        token.getType(), token.getText());
            }
        }
        LOG.debug("finishing the result");
        sw.finish();
        LOG.debug("exit");
    }
    
    /**
     * Returns the xml format rules.
     * 
     * @return the xml format rules
     */
    public final XmlFormatRules getRuleSet() {
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
