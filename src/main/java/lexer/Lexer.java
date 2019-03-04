package lexer;

import java.util.Objects;
import java.util.Stack;

public class Lexer {
    private String input;

    private Token previousToken;
    private boolean returnPrevious = false;

    public Lexer (String input) {
        this.input = input;
    }

    public Token getNextToken () {
        if (returnPrevious) {
            returnPrevious = false;
            return Objects.requireNonNull(previousToken);
        }

        input = input.replaceAll("^\\s+", "");

        Token token = Token.getToken(input);

    }
}
