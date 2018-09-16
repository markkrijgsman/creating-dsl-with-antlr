grammar KeyValuePairGrammar;
import KeyValuePairLexer;

query   : pair+;
pair    : LEFTBRACKET KEY SEPARATOR value RIGHTBRACKET;
value   : TERM (COMBINATOR TERM)*;
