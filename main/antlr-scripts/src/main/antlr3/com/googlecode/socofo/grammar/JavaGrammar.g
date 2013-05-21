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

JAVA_FILE:
PACKAGE_DECL? WS*
IMPORT_DECL* WS*
EOF
;

IMPORT_DECL:
'import' WS+ FQCN ';'
;

PACKAGE_DECL:
'package' WS+ FQPN ';'
;

ANNOTATION:
'@' CLASS_NAME
;
ROUND_BRACE_OPEN: '(';
ROUND_BRACE_CLOSE: ')';
BRACE_OPEN: '{';
BRACE_CLOSE:'}';

CLASS_DEFINITION: 'class' WS+ CLASS_NAME;

fragment DIGIT: '0'..'9';

fragment LETTER: 'a'..'z' | 'A'..'Z';

fragment LETTER_OR_DIGIT:
 LETTER | DIGIT
 ;

PACKAGE_PART: // defines a single package name fragment
  LETTER LETTER_OR_DIGIT*
  ;
FQPN: // a full qualified package name
  PACKAGE_PART ('.' PACKAGE_PART)*
;
CLASS_NAME:
LETTER (LETTER_OR_DIGIT | '_')*
;
FQCN: // full qualified Class name
 (PACKAGE_PART '.')* CLASS_NAME
;
WS  :  (' '|'\t'|'\r'|'\n') { $channel=HIDDEN; }
    ;
COMMENT_MULTI: '/*' .* '*/' {$channel=HIDDEN;} ;
COMMENT_SINGLELINE: '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;};
