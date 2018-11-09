grammar AmbiguousGrammar;

keywords: KEYWORD+;
KEYWORD	: 'bar'|'foobar'|'foo';

WHITESPACE  : [ \t\r\n]+ -> skip;
