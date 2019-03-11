package lexer;

import lexer.token.FoundToken;
import lexer.token.NumberToken;
import lexer.token.Token;

import java.util.Deque;
import java.util.LinkedList;
import java.util.regex.Matcher;

public class Lexer {
    private String input;


    private Deque<FoundToken> tokens = new LinkedList<>();
    private Deque<FoundToken> alreadyRequestedTokens = new LinkedList<>();

    @Override
    public String toString() {
        return tokens.toString() + " at " + alreadyRequestedTokens.size();
    }

    public Lexer (String input) {
        this.input = input;
        processInput(new LinkedList<>());
    }

    private void processInput(Deque<FoundToken> alreadyProcessed) {
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
        alreadyRequestedTokens.addFirst(tokens.peekFirst());
        return tokens.pop();
    }

    public void revert () {
        tokens.addFirst(alreadyRequestedTokens.pop());
    }
}
