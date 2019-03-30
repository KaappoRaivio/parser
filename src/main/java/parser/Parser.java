package parser;

import lexer.Lexer;
import lexer.token.FoundToken;
import lexer.token.NumberToken;
import lexer.token.Token;
import math.Calculator;
import math.Fraction;
import math.Fractionatable;
import operator.OperatorGroup;
import operator.UnaryOperator;

import java.util.Arrays;
import java.util.List;

public class Parser {
    private Lexer lexer;
    private Calculator<Fraction> calculator;
    private OperatorGroup operatorGroup;

    public Parser(String input, Calculator<Fraction> calculator, OperatorGroup operatorGroup) {
        lexer = new Lexer(input);
        this.calculator = calculator;
        this.operatorGroup = operatorGroup;

        if (lexer.isEmpty()) {
            throw new RuntimeException("Input cannot be nothing!");
        }
    }

    public Fractionatable parse () {
        Fractionatable expressionValue = expression();

        FoundToken token = lexer.getNextToken();
        if (!token.is(Token.END)) {
            throw new RuntimeException("End expected, got " + token + " instead!");
        } else {
            return expressionValue.fractionValue();
        }
    }

    private Fractionatable expression () {
        final List<Token> additiveOperators = Arrays.asList(Token.ADD, Token.SUBTRACT);


        Fractionatable leftOperand = factor();
        FoundToken operator = lexer.getNextToken();

        while (operator.isIn(additiveOperators)) {
            Fractionatable rightOperand = factor();

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

    private Fractionatable factor () {
        final List<Token> multiplicativeOperators = Arrays.asList(Token.MULTIPLY, Token.DIVIDE, Token.LPAREN);


        Fractionatable leftOperand = prefixUnary();
        FoundToken operator = lexer.getNextToken();

        while (operator.isIn(multiplicativeOperators)) {
            if (operator.is(Token.LPAREN)) {
                lexer.revert();
                operator = new FoundToken(Token.MULTIPLY);
            }
            
            Fractionatable rightOperand = prefixUnary();

            switch (operator.getTokenType()) {
                case MULTIPLY:
                    leftOperand = calculator.multiply(leftOperand, rightOperand);
                    break;
                case DIVIDE:
                    leftOperand = calculator.divide(leftOperand, rightOperand);
                    break;
                case LPAREN:
                    leftOperand = calculator.multiply(leftOperand, rightOperand);
                    break;
                default:
                    throw new RuntimeException("Unknown exception @ factor");

            }
            operator = lexer.getNextToken();
        }


        lexer.revert();
        return leftOperand;
    }

    private Fractionatable prefixUnary() {

        FoundToken token = lexer.getNextToken();

        if (operatorGroup.isPrefixOperator(token)) {
            UnaryOperator operator = operatorGroup.getPrefixOperator(token);
            return operator.invoke(prefixUnary());
        } else {
            lexer.revert();
            return suffixUnary();
        }


//        if (token.is(Token.SUBTRACT)) {
//            sign = -1;
//            token = lexer.getNextToken();
//        } else if (token.is(Token.ADD)) {
//            sign = 1;
//            token = lexer.getNextToken();
//        } else if (token.is(Token.SQRT)) {
//            return calculator.root(prefixUnary(), 2);
//        }
//
//        FoundToken possibleSqrt = lexer.getNextToken();
//        if (possibleSqrt.is(Token.SQRT)) {
//            return calculator.root(prefixUnary(), 2);
//        } else {
//            lexer.revert();
//        }




    }

    private Fractionatable suffixUnary () {

        Fractionatable value = number();
        FoundToken possibleSuffixUnary = lexer.getNextToken();
        while (operatorGroup.isSuffixOperator(possibleSuffixUnary)) {
            value = operatorGroup.getSuffixOperator(possibleSuffixUnary).invoke(value);
            possibleSuffixUnary = lexer.getNextToken();
        }

        lexer.revert();
        return value;
    }

    private Fractionatable number() {
        FoundToken token = lexer.getNextToken();
        Fractionatable value;

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
            value = calculator.valueOf(((NumberToken) token).getValue().toString(), false); // "repeating decimal" case is handled in suffixUnary().
        } else {
            System.out.println(lexer);
            throw new RuntimeException("Invalid token " + token);
        }

        return value;
    }

    public Lexer getLexer() {
        return lexer;
    }

}
