package lexer;

import lexer.token.*;
import misc.Pair;
import operator.binaryoperator.BinaryOperator;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Matcher;

public class Lexer {
    private final BinaryOperator implicitOperator;
    private Tokens possibleTokens;
    private Deque<FoundToken> tokens = new LinkedList<>();
    private Deque<FoundToken> alreadyRequestedTokens = new LinkedList<>();

    @Override
    public String toString() {
        return tokens.toString() + " at " + alreadyRequestedTokens.size();
    }

    public Lexer (String input, final BinaryOperator implicitOperator, Tokens possibleTokens) {
        this.implicitOperator = implicitOperator;
        this.possibleTokens = possibleTokens;
        processInput(input, new LinkedList<>(), new FoundToken(Token.END));
    }

    private void processInput(String input, Deque<FoundToken> alreadyProcessed, FoundToken previousToken) {
        if (input.length() == 0) {
            alreadyProcessed.add(new FoundToken(Token.END));
            tokens = alreadyProcessed;
            return;
        }

        input = input.replaceAll("^\\s+", "");

        Token token = possibleTokens.getToken(input);

        FoundToken latestToken;

        if (token.getTokenType() == TokenType.NUMBER) {
            Matcher matcher = token.getRegex().matcher(input);

            // WTF JAVA REGEXES??? Doesn't work without the line two lines below   // 16.7.2019
            //noinspection ResultOfMethodCallIgnored
            matcher.lookingAt();

            String extracted = matcher.group();

            latestToken = new NumberToken(extracted.replaceAll(",", "."));
        } else if (token.getTokenType() == TokenType.SYMBOL) {
            Matcher matcher = token.getRegex().matcher(input);

            // WTF JAVA REGEXES??? Doesn't work without the line two lines below  // 16.7.2019
            //noinspection ResultOfMethodCallIgnored
            matcher.lookingAt();

            String extracted = matcher.group();

            latestToken = new SymbolToken(extracted.replaceAll(",", "."));
        }
        else {
            latestToken = new FoundToken(token);
        }

        String inputBefore = input;
        input = token
                .getRemoverRegex()
                .matcher(input)
                .replaceFirst("");

        if (input.equals(inputBefore)) {   // Aka nothing was recognized from the input string and it didn't change  // 16.7.2019
            throw new RuntimeException("Unrezognized input: " + input + "!");
        }


        if (possibleTokens.needsImplicitOperator(previousToken, latestToken)) {
            alreadyProcessed.add(new FoundToken(implicitOperator.getTokenType()));
        }

        alreadyProcessed.add(latestToken);
        processInput(input, alreadyProcessed, latestToken);
    }


    public FoundToken getNextToken () {
        alreadyRequestedTokens.addFirst(tokens.peekFirst());
        return tokens.pop();
    }

    public void revert () {
        tokens.addFirst(alreadyRequestedTokens.pop());
    }

    public boolean isEmpty() {
        return tokens.size() == 1; // 1 for the END token
    }

}
