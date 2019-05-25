package lexer;

import expression.Expression;
import lexer.token.FoundToken;
import lexer.token.NumberToken;
import lexer.token.SymbolToken;
import lexer.token.Token;
import misc.Pair;
import operator.binaryoperator.BinaryOperator;
import operator.unaryoperator.UnaryOperator;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;

public class Lexer {
    private final BinaryOperator implicitOperator;
    private Deque<FoundToken> tokens = new LinkedList<>();
    private Deque<FoundToken> alreadyRequestedTokens = new LinkedList<>();

    @Override
    public String toString() {
        return tokens.toString() + " at " + alreadyRequestedTokens.size();
    }

    public Lexer (String input, final BinaryOperator implicitOperator) {
        this.implicitOperator = implicitOperator;
        processInput(input, new LinkedList<>(), new FoundToken(Token.END));
    }

    private void processInput(String input, Deque<FoundToken> alreadyProcessed, FoundToken latestToken) {
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

            foundToken = new NumberToken(extracted.replaceAll(",", "."));
        } else if (token == Token.SYMBOL) {
            Matcher matcher = token.getRegex().matcher(input);

            //noinspection ResultOfMethodCallIgnored
            matcher.lookingAt();

            String extracted = matcher.group();

            foundToken = new SymbolToken(extracted.replaceAll(",", "."));
        }
        else {
            foundToken = new FoundToken(token);
        }

        String inputBefore = input;
        input = token
                .getRemoverRegex()
                .matcher(input)
                .replaceFirst("");
        if (input.equals(inputBefore)) {
            throw new RuntimeException("Doesnt recognize " + input + "!");
        }


        if (needsImplicitOperator(latestToken, foundToken)) {
            alreadyProcessed.add(new FoundToken(implicitOperator.getTokenType()));
        }

        alreadyProcessed.add(foundToken);
        processInput(input, alreadyProcessed, foundToken);
    }

    private boolean needsImplicitOperator (FoundToken left, FoundToken right) {
//        latestToken.getTokenType() == Token.RPAREN || foundToken.getTokenType() == Token.LPAREN
        return Map.ofEntries(
                Map.entry(new Pair<>(Token.SYMBOL, Token.SYMBOL), true),
                Map.entry(new Pair<>(Token.NUMBER, Token.SYMBOL), true),
                Map.entry(new Pair<>(Token.SYMBOL, Token.NUMBER), true),

                Map.entry(new Pair<>(Token.RPAREN, Token.LPAREN), true),

                Map.entry(new Pair<>(Token.RPAREN, Token.NUMBER), true),
                Map.entry(new Pair<>(Token.RPAREN, Token.SYMBOL), true),

                Map.entry(new Pair<>(Token.NUMBER, Token.LPAREN), true),
                Map.entry(new Pair<>(Token.SYMBOL, Token.LPAREN), true),

                Map.entry(new Pair<>(Token.NUMBER, Token.SQRT), true),
                Map.entry(new Pair<>(Token.SYMBOL, Token.SQRT), true)
        )
                .getOrDefault(new Pair<>(left.getTokenType(), right.getTokenType()), false);

    }

    public FoundToken getNextToken () {
        alreadyRequestedTokens.addFirst(tokens.peekFirst());
        return tokens.pop();
    }

    private FoundToken peekNextToken () {
        return tokens.peek();
    }

    public void revert () {
        tokens.addFirst(alreadyRequestedTokens.pop());
    }

    public boolean isEmpty() {
        return tokens.size() == 1; // 1 for the END token
    }

    public static void main (String[] args) {
        System.out.println(new Lexer("3x(2 + 2)(2-4)", (BinaryOperator) Expression.operatorMul));
    }
}
