package parser;

import lexer.Lexer;
import lexer.token.FoundToken;
import lexer.token.NumberToken;
import lexer.token.Token;
import math.fraction.Calculator;
import math.fraction.Fraction;
import math.fraction.Fractionatable;
import operator.binaryoperator.BinaryOperator;
import operator.genericoperator.GenericOperatorGroup;
import operator.genericoperator.GenericOperatorStack;
import operator.genericoperator.Operator;
import operator.genericoperator.OperatorType;
import operator.unaryoperator.UnaryOperator;

public class GenericParser {
    private Lexer lexer;
    private Calculator<Fraction> calculator;
    private GenericOperatorStack genericOperatorStack;

    public GenericParser (String input, Calculator<Fraction> calculator, GenericOperatorStack genericOperatorStack) {
        lexer = new Lexer(input);
        this.calculator = calculator;
        this.genericOperatorStack = genericOperatorStack;


        if (lexer.isEmpty()) {
            throw new RuntimeException("Input cannot be nothing!");
        }
    }

    public Fractionatable parse () {
//        Fractionatable expressionValue = expression();
        Fractionatable expressionValue = binaryEval(0);

        FoundToken token = lexer.getNextToken();
        if (!token.is(Token.END)) {
            throw new RuntimeException("End expected, got " + token + " instead!");
        } else {
            return expressionValue.fractionValue();
        }
    }

    private Fractionatable binaryEval(int operatorStackPointer) {
        if (operatorStackPointer >= genericOperatorStack.size()) {
            return number();
        }

        GenericOperatorGroup currentOperators = genericOperatorStack.getGroup(operatorStackPointer);
        if (currentOperators.getGroupType() == OperatorType.UNARY) {
            return prefixUnary(operatorStackPointer);
        }

        Fractionatable leftOperand = binaryEval(operatorStackPointer + 1);
        FoundToken operatorToken = lexer.getNextToken();

        if (currentOperators.isOperator(operatorToken)) {
            Operator operator = currentOperators.getOperator(operatorToken);
            if (operator.getOperatorType() != OperatorType.BINARY) {
                throw new RuntimeException("Unknown error! " + operator);
            }
            BinaryOperator binaryOperator = (BinaryOperator) operator;

            switch (binaryOperator.getEvaluatingOrder()) {
                case LEFT_TO_RIGHT:
                    while (currentOperators.isOperator(operatorToken)) {
                        binaryOperator = (BinaryOperator) currentOperators.getOperator(operatorToken);
                        Fractionatable rightOperand = binaryEval(operatorStackPointer + 1);

                        leftOperand = binaryOperator.invoke(leftOperand, rightOperand);

                        operatorToken = lexer.getNextToken();
                    }

                    lexer.revert();

                    return leftOperand;
                case RIGHT_TO_LEFT:
                    return binaryOperator.invoke(leftOperand, binaryEval(operatorStackPointer));
               default:
                    throw new RuntimeException("Unknown error " + binaryOperator.getEvaluatingOrder() + "!");
            }
        } else {
//            throw new RuntimeException("Operator " + operatorToken + " is not known by the parser!");
            lexer.revert();
            return leftOperand;
        }
    }


    private Fractionatable prefixUnary (int operatorStackPointer) {
        if (operatorStackPointer >= genericOperatorStack.size()) {
            return number();
        }
        var currentOperators = genericOperatorStack.getGroup(operatorStackPointer);
        if (currentOperators.getGroupType() == OperatorType.BINARY) {
            return binaryEval(operatorStackPointer);
        }

        FoundToken token = lexer.getNextToken();

        if (currentOperators.isOperator(token)) {
            Operator operator = currentOperators.getOperator(token);
            UnaryOperator unaryOperator = (UnaryOperator) operator;
            return unaryOperator.invoke(prefixUnary(operatorStackPointer));
        } else {
            lexer.revert();
            return binaryEval(operatorStackPointer + 1);
//            return number();
        }

    }

    private Fractionatable number() {
        FoundToken token = lexer.getNextToken();
        Fractionatable value;

        if (token.is(Token.LPAREN)) {
            value = binaryEval(0);
            FoundToken expectedClosingParen = lexer.getNextToken();
            if (!expectedClosingParen.is(Token.RPAREN)) {
                throw new RuntimeException("Unbalanced parentheses! " + expectedClosingParen);
            }

        } else if (token.is(Token.ABS)) {
            value = calculator.abs(binaryEval(0));
            FoundToken expectedClosingPipe = lexer.getNextToken();
            if (!expectedClosingPipe.is(Token.ABS)) {
                throw new RuntimeException("Unbalanced pipes!" + expectedClosingPipe);
            }
        } else if (token.getClass() == NumberToken.class) {
            value = calculator.valueOf(((NumberToken) token).getValue()); // "repeating decimal" case is handled in suffixUnary().
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
