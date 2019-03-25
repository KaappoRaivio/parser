package parser;

import lexer.Lexer;
import lexer.token.FoundToken;
import lexer.token.NumberToken;
import lexer.token.Token;
import math.Calculator;
import math.Fraction;

import java.util.Arrays;
import java.util.List;

public class Parser<T extends Fraction> {
    private Lexer lexer;
    private Calculator<T> calculator;

    public Parser (String input, Calculator<T> calculator) {
        lexer = new Lexer(input);
        this.calculator = calculator;

        if (lexer.isEmpty()) {
            throw new RuntimeException("Input cannot be nothing!");
        }
    }

    public T parse () {
        T expressionValue = expression();

        FoundToken token = lexer.getNextToken();
        if (!token.is(Token.END)) {
            throw new RuntimeException("End expected, got " + token + " instead!");
        } else {
            return expressionValue;
        }
    }

    private T expression () {
        final List<Token> additiveOperators = Arrays.asList(Token.ADD, Token.SUBTRACT);


        T leftOperand = factor();
        FoundToken operator = lexer.getNextToken();

        while (operator.isIn(additiveOperators)) {
            T rightOperand = factor();

            switch (operator.getTokenType()) {
                case ADD:
                    leftOperand = calculator.add(leftOperand, rightOperand);
                    break;
                case SUBTRACT:
                    leftOperand = calculator.subtract(leftOperand, rightOperand);
                    break;
                default:
                    throw new RuntimeException("Unknown exception @ expression!");
            }

            operator = lexer.getNextToken();
        }
        lexer.revert();
        return leftOperand;

    }

    private T factor () {
        final List<Token> multiplicativeOperators = Arrays.asList(Token.MULTIPLY, Token.DIVIDE);


        T leftOperand = number();
        FoundToken operator = lexer.getNextToken();

        while (operator.isIn(multiplicativeOperators)) {
            T rightOperand = number();

            switch (operator.getTokenType()) {
                case MULTIPLY:
                    leftOperand = calculator.multiply(leftOperand, rightOperand);
                    break;
                case DIVIDE:
                    leftOperand = calculator.divide(leftOperand, rightOperand);
                    break;
                default:
                    throw new RuntimeException("Unknown exception @ factor");
            }
            operator = lexer.getNextToken();
        }


        lexer.revert();
        return leftOperand;
    }

    private T number () {
        int sign = 1;
        FoundToken token = lexer.getNextToken();

        T value;

        if (token.is(Token.SUBTRACT)) {
            sign = -1;
            token = lexer.getNextToken();
        } else if (token.is(Token.ADD)) {
            sign = 1;
            token = lexer.getNextToken();
        }

        boolean repeating = false;
        FoundToken possibleEllipsis = lexer.getNextToken();
        if (possibleEllipsis.is(Token.ELLIPSIS)) {
            repeating = true;
        } else {
            lexer.revert();
        }

        if (token.is(Token.LPAREN)) {
            value = expression();
            FoundToken expectedClosingParen = lexer.getNextToken();
            if (!expectedClosingParen.is(Token.RPAREN)) {
                throw new RuntimeException("Unbalanced parentheses! " + expectedClosingParen);
            }

        } else if (token.is(Token.ABS)) {
            value = calculator.abs(expression());
            FoundToken expectedClosingPipe = lexer.getNextToken();
            if (!expectedClosingPipe.is(Token.ABS)) {
                throw new RuntimeException("Unbalanced pipes!" + expectedClosingPipe);
            }
        } else if (token.getClass() == NumberToken.class) {
            value = calculator.valueOf(((NumberToken) token).getValue().toString(), repeating);
        } else {
            System.out.println(lexer);
            throw new RuntimeException("Invalid token " + token);
        }

        return calculator.multiply(value, sign);
    }

    public Lexer getLexer() {
        return lexer;
    }
}
