parser  grammar XmlParser;

options {
tokenVocab=XmlGrammar; 
language=Java;
}

@header {
    package ds2.socofo.antlr.parsers;
}

document  : element ;

element
    : startTag
        (element
        | PCDATA
        )*
        endTag
    | emptyElement
    ;

startTag  : TAG_START_OPEN GENERIC_ID (attribute)* TAG_CLOSE ;

attribute  : GENERIC_ID ATTR_EQ ATTR_VALUE ;

endTag :  TAG_END_OPEN GENERIC_ID TAG_CLOSE ;

emptyElement : TAG_START_OPEN GENERIC_ID  (attribute)* TAG_EMPTY_CLOSE ;
