package lexer;

import lexer.token.FoundToken;
import lexer.token.NumberToken;
import lexer.token.Token;

import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;

public class Lexer {
    private String input;


    private Queue<FoundToken> tokens = new LinkedList<>();
    private Queue<FoundToken> alreadyRequestedTokens = new LinkedList<>();

    @Override
    public String toString() {
        return tokens.toString();
    }

    public Lexer (String input) {
        this.input = input;
        processInput(new LinkedList<>());
    }

    private void processInput(Queue<FoundToken> alreadyProcessed) {
        if (input.length() == 0) {
            alreadyProcessed.add(new FoundToken(Token.END));
            tokens = alreadyProcessed;
            return;
        }

        input = input.replaceAll("^\\s+", "");

        Token token = Token.getToken(input);

        FoundToken foundToken;

        if (token == Token.NUMBER) {


            Matcher matcher = token.getRegex().matcher(input);
            //noinspection ResultOfMethodCallIgnored
            matcher.lookingAt();

            String extracted = matcher.group();

            foundToken = new NumberToken(token, Float.parseFloat(extracted.replaceAll(",", ".")));
        } else {
            foundToken = new FoundToken(token);
        }

        input = token.getRemoverRegex().matcher(input).replaceFirst("");
        alreadyProcessed.add(foundToken);
        processInput(alreadyProcessed);
    }

    public FoundToken getNextToken () {
        alreadyRequestedTokens.add(tokens.peek());
        return tokens.remove();
    }

    public void revert () {
        tokens.add(alreadyRequestedTokens.remove());
    }
}
