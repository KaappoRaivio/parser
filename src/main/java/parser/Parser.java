package parser;

import lexer.Lexer;
import lexer.token.FoundToken;
import lexer.token.NumberToken;
import lexer.token.Token;

import java.util.Arrays;
import java.util.List;

public class Parser {
    private String input;
    private Lexer lexer;

    public Parser (String input) {
        this.input = input;
        lexer = new Lexer(input);
    }

    public Number parse () {
        Number expressionValue = expression();

        FoundToken token = lexer.getNextToken();
        if (!token.is(Token.END)) {
            throw new RuntimeException("End expected, got " + token + " instead!");
        } else {
            return expressionValue;
        }
    }

    private Number expression () {
        final List<Token> additiveOperators = Arrays.asList(Token.ADD, Token.SUBTRACT);


        Double leftOperand = factor().doubleValue();
        FoundToken operator = lexer.getNextToken();

        while (operator.isIn(additiveOperators)) {
            Double rightOperand = factor().doubleValue();

            switch (operator.getTokenType()) {
                case ADD:
                    leftOperand += rightOperand;
                    break;
                case SUBTRACT:
                    leftOperand -= rightOperand;
                    break;
                default:
                    throw new RuntimeException("Unknown exception @ expression!");
            }

            operator = lexer.getNextToken();
        }
        lexer.revert();
        return leftOperand;

    }

    private Number factor () {
        final List<Token> multiplicativeOperators = Arrays.asList(Token.MULTIPLY, Token.DIVIDE);


        Double leftOperand = number().doubleValue();
        FoundToken operator = lexer.getNextToken();

        while (operator.isIn(multiplicativeOperators)) {
            Double rightOperand = number().doubleValue();

            switch (operator.getTokenType()) {
                case MULTIPLY:
                    leftOperand *= rightOperand;
                    break;
                case DIVIDE:
                    leftOperand /= rightOperand;
                    break;
                default:
                    throw new RuntimeException("Unknown exception @ factor");
            }
            operator = lexer.getNextToken();
        }


        lexer.revert();
        return leftOperand;
    }

    private Number number () {
        int sign = 1;
        FoundToken token = lexer.getNextToken();

        Number value;

        if (token.is(Token.SUBTRACT)) {
            sign = -1;
            token = lexer.getNextToken();
        } else if (token.is(Token.ADD)) {
            sign = 1;
            token = lexer.getNextToken();
        }

        if (token.is(Token.LPAREN)) {
            value = expression();
            FoundToken expectedClosingParen = lexer.getNextToken();
            if (!expectedClosingParen.is(Token.RPAREN)) {
                throw new RuntimeException("Unbalanced parentheses! " + expectedClosingParen);
            }

        } else if (token.is(Token.ABS)) {
            value = Math.abs(expression().doubleValue());
            FoundToken expectedClosingPipe = lexer.getNextToken();
            if (!expectedClosingPipe.is(Token.ABS)) {
                throw new RuntimeException("Unbalanced pipes!" + expectedClosingPipe);
            }
        } else if (token.getClass() == NumberToken.class) {
            value = ((NumberToken) token).getValue();
        } else {
            throw new RuntimeException("Invalid token " + token);
        }

        return value.doubleValue() * sign;
    }

    public Lexer getLexer() {
        return lexer;
    }
}
