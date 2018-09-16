package nl.luminis.meetup.helloworld;

import static org.assertj.core.api.Assertions.assertThat;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

public class HelloGrammarTest {

    @Test
    public void testHelloName() {
        HelloGrammarParser parser = createParser("Hello name");

        HelloGrammarParser.GreetingContext greeting = parser.greeting();

        assertThat(greeting.NAME().getText()).isEqualTo("name");
    }

    @Test
    public void testHelloUpperCaseName() {
        HelloGrammarParser parser = createParser("Hello Name");

        HelloGrammarParser.GreetingContext greeting = parser.greeting();

        assertThat(greeting.NAME().getText()).isEqualTo("Name");
    }

    private HelloGrammarParser createParser(String input) {
        HelloGrammarLexer lexer = new HelloGrammarLexer(CharStreams.fromString(input));

        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

        return new HelloGrammarParser(commonTokenStream);
    }
}
