lexer grammar KeyValuePairLexer;

KEY         : ; /* TODO: Define lexer rule */
TERM        : ; /* TODO: Define lexer rule */

COMBINATOR  : [,];
SEPARATOR   : [:];
LEFTBRACKET : [(];
RIGHTBRACKET: [)];

WHITESPACE  : [\t\r\n]+ -> skip;
