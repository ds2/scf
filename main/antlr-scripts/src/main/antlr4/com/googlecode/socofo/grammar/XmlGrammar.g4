/**
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
lexer grammar XmlGrammar;

@members {
    boolean tagMode = false;
    boolean doctypeMode=false;
}

PI_START : '<?' { tagMode=true;};
PI_STOP: {tagMode}? '?>' {tagMode=false;};

CDATA_SECTION: {!tagMode}? '<![CDATA[' (~']')* ']]>' ;

COMMENT_SECTION:'<!--' (~'-')* '-->';

DOCTYPE_SECTION: {!tagMode}? '<!DOCTYPE' (~'>')* '>' ;

TAG_START_OPEN : '<' { tagMode = true; } ;
TAG_END_OPEN : '</' { tagMode = true; } ;
TAG_CLOSE : { tagMode }? '>' { tagMode = false; } ;
TAG_EMPTY_CLOSE : { tagMode }? '/>' { tagMode = false; } ;

ATTR_EQ : { tagMode }? '=' ;

ATTR_VALUE : { tagMode }?
        ( '"' (~'"')* '"'
        | '\'' (~'\'')* '\''
        )
    ;

/** ATTRIBUTE : GENERIC_ID (WS)* ATTR_EQ (WS)* ATTR_VALUE; */

PCDATA : { !tagMode && !doctypeMode }? (~'<')+ ;

GENERIC_ID
    : { tagMode }?
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
    

WS  :  { tagMode }?
       (' '|'\r'|'\t'|'\u000C'|'\n')+ { skip(); }
    ;
