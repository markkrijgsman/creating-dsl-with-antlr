grammar HelloGrammar;

greeting : 'Hello' NAME;

NAME : [A-z]+;
WS : [ \t\r\n]+ -> skip ; // skip whitespace
