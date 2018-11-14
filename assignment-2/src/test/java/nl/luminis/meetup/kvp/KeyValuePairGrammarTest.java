package nl.luminis.meetup.kvp;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.jupiter.api.Test;

public class KeyValuePairGrammarTest {

    @Test
    public void testKeyValuePairSingleTerm() {
        KeyValuePairGrammarParser parser = createParser("(KEY:value)");

        KeyValuePairGrammarParser.QueryContext query = parser.query();
        List<KeyValuePairGrammarParser.PairContext> pairs = query.pair();

        assertThat(pairs.size()).isEqualTo(1);
        assertThat(pairs.get(0).KEY().getText()).isEqualTo("KEY");

        assertThat(pairs.get(0).value().TERM().size()).isEqualTo(1);
        assertThat(pairs.get(0).value().TERM().get(0).getText()).isEqualTo("value");
    }

    @Test
    public void testKeyValuePairMultipleTerms() {
        KeyValuePairGrammarParser parser = createParser("(KEY:value1,value2)");

        KeyValuePairGrammarParser.QueryContext query = parser.query();
        List<KeyValuePairGrammarParser.PairContext> pairs = query.pair();

        assertThat(pairs.size()).isEqualTo(1);
        assertThat(pairs.get(0).KEY().getText()).isEqualTo("KEY");

        List<TerminalNode> terms = pairs.get(0).value().TERM();

        assertThat(terms.stream().map(ParseTree::getText).collect(toList())).containsExactly("value1", "value2");
    }

    @Test
    public void testMultipleKeyValuePairs() {
        KeyValuePairGrammarParser parser = createParser("(KEY1:value)(KEY2:value1,value2)");

        KeyValuePairGrammarParser.QueryContext query = parser.query();
        List<KeyValuePairGrammarParser.PairContext> pairs = query.pair();

        assertThat(pairs.stream().map(pair -> pair.KEY().getText()).collect(toList())).containsExactly("KEY1", "KEY2");

        List<TerminalNode> termsInFirstKVP = pairs.get(0).value().TERM();
        List<TerminalNode> termsInSecondKVP = pairs.get(1).value().TERM();

        assertThat(termsInFirstKVP.stream().map(ParseTree::getText).collect(toList())).containsExactly("value");
        assertThat(termsInSecondKVP.stream().map(ParseTree::getText).collect(toList())).containsExactly("value1", "value2");
    }

    @Test
    public void testKeyShouldBeUpperCaseOnly() {
        KeyValuePairGrammarParser parser = createParser("(Key:value)");

        ParseCancellationException exception = assertThrows(ParseCancellationException.class, parser::query);
    }

    @Test
    public void testInvalidKeyValuePairKey() {
        KeyValuePairGrammarParser parser = createParser("(9KEY:value)");

        ParseCancellationException exception = assertThrows(ParseCancellationException.class, parser::query);
        assertThat(exception.getMessage()).isEqualTo("Error occurred at line 1:1 - token recognition error at: '9'");
    }

    @Test
    public void testValueShouldBeLowerCaseOnly() {
        KeyValuePairGrammarParser parser = createParser("(KEY:Value)");

        ParseCancellationException exception = assertThrows(ParseCancellationException.class, parser::query);
    }

    @Test
    public void testValueShouldNotEndWithComma() {
        KeyValuePairGrammarParser parser = createParser("(KEY:value1,value2,)");

        ParseCancellationException exception = assertThrows(ParseCancellationException.class, parser::query);
        assertThat(exception.getMessage()).isEqualTo("Error occurred at line 1:19 - missing TERM at ')'");
    }

    @Test
    public void testInvalidKeyValuePairTerm() {
        KeyValuePairGrammarParser parser = createParser("(KEY:9value)");

        ParseCancellationException exception = assertThrows(ParseCancellationException.class, parser::query);
        assertThat(exception.getMessage()).isEqualTo("Error occurred at line 1:5 - token recognition error at: '9'");
    }

    private KeyValuePairGrammarParser createParser(String input) {
        KeyValuePairGrammarLexer lexer = new KeyValuePairGrammarLexer(CharStreams.fromString(input));
        lexer.addErrorListener(ParserErrorListener.getInstance());

        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

        KeyValuePairGrammarParser parser = new KeyValuePairGrammarParser(commonTokenStream);
        parser.addErrorListener(ParserErrorListener.getInstance());

        return parser;
    }
}
