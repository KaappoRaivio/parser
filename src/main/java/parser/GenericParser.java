package parser;

import lexer.Lexer;
import lexer.token.FoundToken;
import lexer.token.NumberToken;
import lexer.token.SymbolToken;
import lexer.token.Token;
import math.fraction.Calculator;
import math.fraction.Fraction;
import operator.BoundingOperator;
import operator.binaryoperator.BinaryOperator;
import operator.genericoperator.GenericOperatorGroup;
import operator.genericoperator.GenericOperatorStack;
import operator.genericoperator.Operator;
import operator.genericoperator.OperatorType;
import operator.unaryoperator.UnaryOperator;
import expression.Expression;
import expression.Node;
import expression.Payload;
import expression.Tree;

public class GenericParser {
    private Lexer lexer;
    private Calculator<Fraction> calculator;
    private GenericOperatorStack genericOperatorStack;
    private BinaryOperator implicitOperator;

    public GenericParser(String input, Calculator<Fraction> calculator, GenericOperatorStack genericOperatorStack, BinaryOperator implicitOperator) {
        lexer = new Lexer(input);
        this.calculator = calculator;
        this.genericOperatorStack = genericOperatorStack;
        this.implicitOperator = implicitOperator;


        if (lexer.isEmpty()) {
            throw new RuntimeException("Input cannot be nothing!");
        }
    }

    public Expression parse () {
//        Fractionable expressionValue = expression();
//        Fractionable expressionValue = binaryEval(0);
        Expression expressionValue = binaryEval(0);

        FoundToken token = lexer.getNextToken();
        if (!token.is(Token.END)) {
            throw new RuntimeException("End expected, got " + token + " instead!");
        } else {
            return expressionValue;
        }
    }

    private Expression binaryEval(int operatorStackPointer) {
        if (operatorStackPointer >= genericOperatorStack.size()) {
            return boundaryUnary();
        }

        GenericOperatorGroup currentOperators = genericOperatorStack.getGroup(operatorStackPointer);
        if (currentOperators.getGroupType() == OperatorType.UNARY) {
            return prefixUnary(operatorStackPointer);
        }

        Expression leftOperand = binaryEval(operatorStackPointer + 1);
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
                        Expression rightOperand = binaryEval(operatorStackPointer + 1);

//                        leftOperand = binaryOperator.invoke(leftOperand, rightOperand);
                        leftOperand = leftOperand.makeBinaryOperation(binaryOperator, rightOperand);

                        operatorToken = lexer.getNextToken();
                    }

                    lexer.revert();

                    return leftOperand;
                case RIGHT_TO_LEFT:
//                    return binaryOperator.invoke(leftOperand, binaryEval(operatorStackPointer));
                    return leftOperand.makeBinaryOperation(binaryOperator, binaryEval(operatorStackPointer));
               default:
                    throw new RuntimeException("Unknown error " + binaryOperator.getEvaluatingOrder() + "!");
            }
        } else {
//            throw new RuntimeException("Operator " + operatorToken + " is not known by the parser!");
            lexer.revert();
            return leftOperand;
        }
    }


    private Expression prefixUnary (int operatorStackPointer) {
        if (operatorStackPointer >= genericOperatorStack.size()) {
            return boundaryUnary();
        }
        var currentOperators = genericOperatorStack.getGroup(operatorStackPointer);
        if (currentOperators.getGroupType() == OperatorType.BINARY) {
            return binaryEval(operatorStackPointer);
        }

        FoundToken token = lexer.getNextToken();

        if (currentOperators.isOperator(token)) {
            Operator operator = currentOperators.getOperator(token);
            UnaryOperator unaryOperator = (UnaryOperator) operator;
//            return unaryOperator.invoke(prefixUnary(operatorStackPointer));
            return prefixUnary(operatorStackPointer).makeUnaryOperation(unaryOperator);
        } else {
            lexer.revert();
            return binaryEval(operatorStackPointer + 1);
//            return number();
        }

    }

    private Expression number () {
        FoundToken token = lexer.getNextToken();
        Expression value;

//        if (token.is(Token.LPAREN)) {
//            value = binaryEval(0);
//            FoundToken expectedClosingParen = lexer.getNextToken();
//            if (!expectedClosingParen.is(Token.RPAREN)) {
//                throw new RuntimeException("Unbalanced parentheses! " + expectedClosingParen);
//            }
//
//        } else if (token.is(Token.ABS)) {
//            value = calculator.abs(binaryEval(0));
//            FoundToken expectedClosingPipe = lexer.getNextToken();
//            if (!expectedClosingPipe.is(Token.ABS)) {
//                throw new RuntimeException("Unbalanced pipes!" + expectedClosingPipe);
//            }
        if (token instanceof NumberToken) {
            value = new Expression(calculator.valueOf(((NumberToken) token).getValue())); // "repeating decimal" case is handled in suffixUnary().
        } else if (token instanceof SymbolToken) {
            value = new Expression(calculator.valueOf(((SymbolToken) token).getValue()));
        }
        else {
            System.out.println(lexer);
            throw new RuntimeException("Invalid token " + token);
        }

        return suffixUnary(value);
    }

    private Expression suffixUnary (Expression expression) {
        FoundToken nextToken = lexer.getNextToken();

//        System.out.println(genericOperatorStack.getSuffixOperators() + ", " + nextToken);

        if (genericOperatorStack.getSuffixOperators().isOperator(nextToken)) {
            var operator = genericOperatorStack.getSuffixOperators().getOperator(nextToken);

            return suffixUnary(expression.makeUnaryOperation(((UnaryOperator) operator)));
        } else {
            lexer.revert();
            return expression;
        }
    }

    private Expression boundaryUnary () {
        FoundToken nextToken = lexer.getNextToken();

        if (genericOperatorStack.getBoundaryOperators().isOperator(nextToken)) {
            BoundingOperator operator = (BoundingOperator) genericOperatorStack.getBoundaryOperators().getOperator(nextToken);
            var operand = binaryEval(0);
            FoundToken expectedClosing = lexer.getNextToken();

            if (operator.getRightToken() != expectedClosing.getTokenType()) {
                throw new RuntimeException("Unbalanced bounding operator tokens, expecting " + operator.getRightToken() + " but found " + expectedClosing.getTokenType() + "! " + lexer);
            }

            Node<Payload> parentNode = new Node<>(operator);
            parentNode.addChild(operand.getTree().getParentNode());

            return suffixUnary(new Expression(new Tree<>(parentNode)));
        } else {
            lexer.revert();
            return number();
        }
    }

    public Lexer getLexer() {
        return lexer;
    }

}
