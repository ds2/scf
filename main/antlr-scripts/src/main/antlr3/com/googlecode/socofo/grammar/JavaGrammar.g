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
lexer grammar JavaGrammar;

@header {
    package com.googlecode.socofo.grammar;
}

IMPORT:
'import '(~';')* ';'
;
ANNOTATION:
'@' LETTER (LETTER|DIGIT)*
;
ROUND_BRACE_OPEN: '(';
ROUND_BRACE_CLOSE: ')';
BRACE_OPEN: '{';
BRACE_CLOSE:'}';

CLASS_DEFINITION: 'class' WS+ NAMECHAR*;

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
    

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n')+ { $channel=HIDDEN; }
    ;