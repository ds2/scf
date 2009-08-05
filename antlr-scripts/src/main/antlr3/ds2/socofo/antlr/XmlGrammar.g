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
lexer grammar XmlGrammar;

@header {
    package ds2.socofo.antlr;
}

@members {
    boolean tagMode = false;
    boolean cdataMode=false;
    boolean doctypeMode=false;
}

PI_START : '<?' { tagMode=true;};
PI_STOP: {tagMode}?=> '?>' {tagMode=false;};

DOCTYPE_START:
{!tagMode && ! cdataMode}?=>
'<!DOCTYPE'
{doctypeMode=true;}
;
DOCTYPE_END:
{doctypeMode}?=>
'>'
{doctypeMode=false;}
;

CDATA_START:
{!tagMode}?=>
('<![CDATA[')
{cdataMode=true;}
;
CDATA_END:
{cdataMode}?=>
']]>'
{cdataMode=false;}
;

TAG_START_OPEN : {!cdataMode}?=> '<' { tagMode = true; } ;
TAG_END_OPEN : {!cdataMode}?=> '</' { tagMode = true; } ;
TAG_CLOSE : { tagMode && !cdataMode }?=> '>' { tagMode = false; } ;
TAG_EMPTY_CLOSE : { tagMode && !cdataMode }?=> '/>' { tagMode = false; } ;

ATTR_EQ : { tagMode }?=> '=' ;

ATTR_VALUE : { tagMode }?=>
        ( '"' (~'"')* '"'
        | '\'' (~'\'')* '\''
        )
    ;

/** ATTRIBUTE : GENERIC_ID (WS)* ATTR_EQ (WS)* ATTR_VALUE; */

CDATA_SECTION:
{ cdataMode }?=> (~']')+
;

COMMENT_SECTION:'<!--' (~'-')* '-->';
DOCDATA:
{doctypeMode}?=> (~'>')+
;

PCDATA : { !tagMode && !cdataMode && !doctypeMode }?=> (~'<')+ ;

GENERIC_ID
    : { tagMode }?=>
      ( LETTER | '_' | ':') (NAMECHAR)*
    ;

fragment NAMECHAR
    : LETTER | DIGIT | '.' | '-' | '_' | ':'
    ;

fragment DIGIT
    :    '0'..'9'
    ;

fragment LETTER
    : 'a'..'z'
    | 'A'..'Z'
    ;
    

WS  :  { tagMode }?=>
       (' '|'\r'|'\t'|'\u000C'|'\n')+ { $channel=HIDDEN; }
    ;
