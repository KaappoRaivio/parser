package lexer;

import java.util.Stack;

public class Lexer {
    private String input;


    private Stack<Token> tokens = new Stack<>();
    private Stack<Token> alreadyRequestedTokens = new Stack<>();

    public Lexer (String input) {
        this.input = input;
        processInput(new Stack<>());
    }

    private void processInput(Stack<Token> alreadyProcessed) {
        if (input.length() == 0) {
            alreadyProcessed.push(Token.END);
            tokens = alreadyProcessed;
            return;
        }

        input = input.replaceAll("^\\s+", "");

        Token token = Token.getToken(input);
        System.out.println(input + ", " + token + ", " + token.getRemoverRegex().pattern());
        input = input.replace(token.getRemoverRegex().pattern(), "");
        alreadyProcessed.push(token);
        processInput(alreadyProcessed);
    }

    public Token getNextToken () {
        alreadyRequestedTokens.push(tokens.peek());
        return tokens.pop();
    }

    public void revert () {
        tokens.push(alreadyRequestedTokens.pop());
    }
}
