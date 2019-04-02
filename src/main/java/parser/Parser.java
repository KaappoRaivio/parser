package parser;

import lexer.Lexer;
import lexer.token.FoundToken;
import lexer.token.NumberToken;
import lexer.token.Token;
import math.Calculator;
import math.Fraction;
import math.Fractionatable;
import operator.binaryoperator.BinaryOperator;
import operator.binaryoperator.BinaryOperatorGroup;
import operator.binaryoperator.BinaryOperatorStack;
import operator.unaryoperator.UnaryOperatorGroup;
import operator.unaryoperator.UnaryOperator;

import java.util.Arrays;
import java.util.List;

import static operator.binaryoperator.EvaluatingOrder.LEFT_TO_RIGHT;
import static operator.binaryoperator.EvaluatingOrder.RIGHT_TO_LEFT;

public class Parser {
    private Lexer lexer;
    private Calculator<Fraction> calculator;
    private UnaryOperatorGroup unaryOperatorGroup;
    private BinaryOperatorStack binaryOperatorStack;

    public Parser(String input, Calculator<Fraction> calculator, UnaryOperatorGroup unaryOperatorGroup, BinaryOperatorStack binaryOperatorStack) {
        lexer = new Lexer(input);
        this.calculator = calculator;
        this.unaryOperatorGroup = unaryOperatorGroup;
        this.binaryOperatorStack = binaryOperatorStack;

        if (lexer.isEmpty()) {
            throw new RuntimeException("Input cannot be nothing!");
        }
    }

    public Fractionatable parse () {
//        Fractionatable expressionValue = expression();
        Fractionatable expressionValue = genericEval(0);

        FoundToken token = lexer.getNextToken();
        if (!token.is(Token.END)) {
            throw new RuntimeException("End expected, got " + token + " instead!");
        } else {
            return expressionValue.fractionValue();
        }
    }

    private Fractionatable genericEval (int binaryOperatorStackPointer) {
        if (binaryOperatorStackPointer >= binaryOperatorStack.size()) {
            return prefixUnary();
        }

        BinaryOperatorGroup currentBinaryOperators = binaryOperatorStack.getGroup(binaryOperatorStackPointer);

        Fractionatable leftOperand = genericEval(binaryOperatorStackPointer + 1);
        FoundToken operatorToken = lexer.getNextToken();

        if (currentBinaryOperators.isOperator(operatorToken)) {
            BinaryOperator operator = currentBinaryOperators.getOperator(operatorToken);

            switch (operator.getEvaluatingOrder()) {
                case LEFT_TO_RIGHT:
                    while (currentBinaryOperators.isOperator(operatorToken)) {
                        operator = currentBinaryOperators.getOperator(operatorToken);
                        Fractionatable rightOperand = genericEval(binaryOperatorStackPointer + 1);

                        leftOperand = operator.invoke(leftOperand, rightOperand);

                        operatorToken = lexer.getNextToken();
                    }

                    lexer.revert();
                    return leftOperand;
                case RIGHT_TO_LEFT:
                    return operator.invoke(leftOperand, genericEval(binaryOperatorStackPointer));
                default:
                    throw new RuntimeException("Unknown error " + operator.getEvaluatingOrder() + "!");
            }
        } else {
//            throw new RuntimeException("Operator " + operatorToken + " is not known by the parser!");
            lexer.revert();
            return leftOperand;
        }
    }

    private Fractionatable prefixUnary() {

        FoundToken token = lexer.getNextToken();

        if (unaryOperatorGroup.isPrefixOperator(token)) {
            UnaryOperator operator = unaryOperatorGroup.getPrefixOperator(token);
            return operator.invoke(prefixUnary());
        } else {
            lexer.revert();
            return suffixUnary();
        }
    }

    private Fractionatable suffixUnary () {
        Fractionatable value = number();
        FoundToken possibleSuffixUnary = lexer.getNextToken();

        while (unaryOperatorGroup.isSuffixOperator(possibleSuffixUnary)) {
            value = unaryOperatorGroup.getSuffixOperator(possibleSuffixUnary).invoke(value);
            possibleSuffixUnary = lexer.getNextToken();
        }

        lexer.revert();
        return value;
    }

    private Fractionatable number() {
        FoundToken token = lexer.getNextToken();
        Fractionatable value;

        if (token.is(Token.LPAREN)) {
            value = genericEval(0);
            FoundToken expectedClosingParen = lexer.getNextToken();
            if (!expectedClosingParen.is(Token.RPAREN)) {
                throw new RuntimeException("Unbalanced parentheses! " + expectedClosingParen);
            }

        } else if (token.is(Token.ABS)) {
            value = calculator.abs(genericEval(0));
            FoundToken expectedClosingPipe = lexer.getNextToken();
            if (!expectedClosingPipe.is(Token.ABS)) {
                throw new RuntimeException("Unbalanced pipes!" + expectedClosingPipe);
            }
        } else if (token.getClass() == NumberToken.class) {
            value = calculator.valueOf(((NumberToken) token).getValue().toString()); // "repeating decimal" case is handled in suffixUnary().
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
