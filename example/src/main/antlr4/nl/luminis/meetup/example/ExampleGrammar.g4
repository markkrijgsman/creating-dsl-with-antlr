grammar ExampleGrammar;

statement   : identifier ASSIGNMENT value TERMINATOR;

identifier  : WORD;
value       : NUMBER;

ASSIGNMENT  : '=';
TERMINATOR  : ';';

NUMBER      : [0-9]+;
WORD        : [a-z]+;

WHITESPACE  : [ \t\r\n]+ -> skip;
