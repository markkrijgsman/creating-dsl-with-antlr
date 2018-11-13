grammar HelloGrammar;

greeting : 'Hello' NAME;

NAME : [A-Za-z]+;
WS : [ \t\r\n]+ -> skip ; // skip whitespace
