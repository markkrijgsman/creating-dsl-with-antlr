lexer grammar KeyValuePairLexer;

KEY         : [A-Z]([A-Z0-9])*;
TERM        : [a-z]([a-z0-9])*;

COMBINATOR  : [,];
SEPARATOR   : [:];
LEFTBRACKET : [(];
RIGHTBRACKET: [)];

WHITESPACE  : [\t\r\n]+ -> skip;
