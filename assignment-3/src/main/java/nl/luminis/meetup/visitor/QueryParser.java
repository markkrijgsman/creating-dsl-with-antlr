package nl.luminis.meetup.visitor;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.elasticsearch.index.query.AbstractQueryBuilder;

public class QueryParser {

    private KeyValuePairGrammarVisitor visitor = new KeyValuePairGrammarVisitor();

    public AbstractQueryBuilder parse(String input) {
        KeyValuePairGrammarLexer lexer = new KeyValuePairGrammarLexer(CharStreams.fromString(input));
        lexer.addErrorListener(ParserErrorListener.getInstance());

        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

        KeyValuePairGrammarParser parser = new KeyValuePairGrammarParser(commonTokenStream);
        parser.addErrorListener(ParserErrorListener.getInstance());

        KeyValuePairGrammarParser.QueryContext query = parser.query();
        return visitor.visit(query);
    }

    private class KeyValuePairGrammarVisitor extends KeyValuePairGrammarBaseVisitor<AbstractQueryBuilder> {

        @Override
        public AbstractQueryBuilder visitQuery(KeyValuePairGrammarParser.QueryContext ctx) {
            // TODO: Add implementation;
            return null;
        }

        @Override
        public AbstractQueryBuilder visitPair(KeyValuePairGrammarParser.PairContext ctx) {
            // TODO: Add implementation;
            return null;
        }
    }
}
