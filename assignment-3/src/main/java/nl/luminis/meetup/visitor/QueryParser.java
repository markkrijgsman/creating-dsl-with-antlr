package nl.luminis.meetup.visitor;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchNoneQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class QueryParser {

    private KeyValuePairGrammarVisitor visitor = new KeyValuePairGrammarVisitor();

    public AbstractQueryBuilder parse(String input) {
        KeyValuePairGrammarLexer lexer = new KeyValuePairGrammarLexer(CharStreams.fromString(input));
        lexer.addErrorListener(ParserErrorListener.getInstance());

        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

        KeyValuePairGrammarParser parser = new KeyValuePairGrammarParser(commonTokenStream);
        parser.addErrorListener(ParserErrorListener.getInstance());

        try {
            KeyValuePairGrammarParser.QueryContext query = parser.query();
            return visitor.visit(query);
        } catch (ParseCancellationException ex) {
            return new MatchNoneQueryBuilder();
        }
    }

    private class KeyValuePairGrammarVisitor extends KeyValuePairGrammarBaseVisitor<AbstractQueryBuilder> {

        @Override
        public AbstractQueryBuilder visitQuery(KeyValuePairGrammarParser.QueryContext ctx) {
            BoolQueryBuilder result = boolQuery();
            List<AbstractQueryBuilder> builders = new ArrayList<>();

            ctx.pair().forEach(pair -> builders.add(visitPair(pair)));

            if (builders.isEmpty()) {
                return new MatchNoneQueryBuilder();
            } else if (builders.size() == 1) {
                return builders.get(0);
            } else {
                builders.forEach(((BoolQueryBuilder) result)::should);
                return result;
            }
        }

        @Override
        public AbstractQueryBuilder visitPair(KeyValuePairGrammarParser.PairContext ctx) {
            String key = ctx.KEY().getText();
            String value = ctx.value().getText().replace(",", " ");

            return QueryBuilders.matchQuery(key, value);
        }
    }
}
