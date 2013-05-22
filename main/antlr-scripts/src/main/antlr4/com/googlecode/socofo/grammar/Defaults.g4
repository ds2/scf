lexer grammar Defaults;

WS:
    [' '|'\t'|'\r'|'\n']+ -> skip;

fragment DIGIT:
       '0'..'9'
;
fragment LETTER:
        'A'..'Z'|'a'..'z'
      ;
COMMENT_MULTI: '/*' .*? '*/' {skip();} ;
COMMENT_SINGLELINE: '//' ~('\n'|'\r')* '\r'? '\n' {skip();};
