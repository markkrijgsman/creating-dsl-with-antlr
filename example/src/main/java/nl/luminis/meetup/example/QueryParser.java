package nl.luminis.meetup.example;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class QueryParser {

    private static String input = "foo=42;";

    public static void main(String[] args) {
        ExampleGrammarLexer lexer = new ExampleGrammarLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExampleGrammarParser parser = new ExampleGrammarParser(tokens);

        System.out.println(new AssignmentVisitor().visit(parser.statement()));
    }

    private static class AssignmentVisitor extends ExampleGrammarBaseVisitor<String> {

        @Override
        public String visitStatement(ExampleGrammarParser.StatementContext ctx) {
            return ctx.identifier().getText() + ctx.ASSIGNMENT() + ctx.value().getText();
        }
    }
}
