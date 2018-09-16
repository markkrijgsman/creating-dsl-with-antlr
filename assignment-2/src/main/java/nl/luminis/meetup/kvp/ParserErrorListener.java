package nl.luminis.meetup.kvp;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class ParserErrorListener extends BaseErrorListener {

    private static ParserErrorListener instance;

    private ParserErrorListener() {
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        throw new ParseCancellationException("Error occurred at line " + line + ":" + charPositionInLine + " - " + msg);
    }

    public static ParserErrorListener getInstance() {
        if (instance == null) {
            instance = new ParserErrorListener();
        }
        return instance;
    }
}
