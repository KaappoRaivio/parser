package expression;

import ch.obermuhlner.math.big.BigDecimalMath;
import lexer.token.Token;
import math.fraction.ApproxFraction;
import math.fraction.Fraction;
import math.fraction.Fractionable;
import misc.BigFunctions;
import operator.BoundingOperator;
import operator.binaryoperator.BinaryOperator;
import operator.binaryoperator.EvaluatingOrder;
import operator.genericoperator.Operator;
import operator.genericoperator.OperatorType;
import operator.unaryoperator.UnaryOperator;
import operator.unaryoperator.UnaryOperatorType;
import parser.MyValueProvider;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Expression {
    public static final MathContext CONTEXT = MathContext.DECIMAL128;

    public final static Operator operatorAdd = new BinaryOperator(Token.ADD,        (fractionable, fractionable2) -> fractionable.fractionValue().add(fractionable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
    public final static Operator operatorSub = new BinaryOperator(Token.SUBTRACT,   (fractionable, fractionable2) -> fractionable.fractionValue().subtract(fractionable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
    public final static Operator operatorMul = new BinaryOperator(Token.MULTIPLY,   (fractionable, fractionable2) -> fractionable.fractionValue().multiply(fractionable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
    public final static Operator operatorDiv = new BinaryOperator(Token.DIVIDE,     (fractionable, fractionable2) -> fractionable.fractionValue().divide(fractionable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
    public final static Operator operatorPow = new BinaryOperator(Token.POWER,      (fractionable, fractionable2) -> fractionable.fractionValue().power(fractionable2.fractionValue()), EvaluatingOrder.RIGHT_TO_LEFT);
    public final static Operator operatorRot = new BinaryOperator(Token.ROOT,       (fractionable, fractionable2) -> fractionable2.fractionValue().root(fractionable.fractionValue().safeIntValue()), EvaluatingOrder.LEFT_TO_RIGHT);
    public final static Operator operatorIPo = new BinaryOperator(Token.INVPOW,     (fractionable, fractionable2) -> fractionable.fractionValue().power(fractionable2.fractionValue().negate()), EvaluatingOrder.RIGHT_TO_LEFT);
    public final static Operator operatorIRo = new BinaryOperator(Token.NEG_ROOT,   (fractionable, fractionable2) -> fractionable2.fractionValue().negate().root(fractionable.fractionValue().safeIntValue()), EvaluatingOrder.LEFT_TO_RIGHT);

    public final static Operator operatorSqr = new UnaryOperator(Token.SQRT,                       (fractionable) -> fractionable.fractionValue().root(BigInteger.TWO), UnaryOperatorType.PREFIX);
    public final static Operator operatorNeg = new UnaryOperator(Token.SUBTRACT,                   (fractionable) -> fractionable.fractionValue().negate(), UnaryOperatorType.PREFIX);
    public final static Operator operatorPos = new UnaryOperator(Token.ADD,                        (fractionable) -> fractionable, UnaryOperatorType.PREFIX);
    public final static Operator operatorEll = new UnaryOperator(Token.ELLIPSIS,                   (fractionable) -> fractionable.fractionValue().toEndless(), UnaryOperatorType.BOUNDARY);
    public final static Operator operatorFac = new UnaryOperator(Token.EXCLAMATION,                (fractionable) -> fractionable.fractionValue().factorial(), UnaryOperatorType.SUFFIX);
    public final static Operator operatorISq = new UnaryOperator(Token.NEG_SQRT,                   (fractionable) -> fractionable.fractionValue().negate().root(BigInteger.TWO), UnaryOperatorType.PREFIX);

    public final static Operator operatorSin = new UnaryOperator(Token.SIN,                        (fractionable) -> new ApproxFraction(BigDecimalMath.sin(fractionable.fractionValue().toDecimal(), CONTEXT)), UnaryOperatorType.PREFIX);
    public final static Operator operatorCos = new UnaryOperator(Token.COS,                        (fractionable) -> new ApproxFraction(BigDecimalMath.cos(fractionable.fractionValue().toDecimal(), CONTEXT)), UnaryOperatorType.PREFIX);
    public final static Operator operatorTan = new UnaryOperator(Token.TAN,                        (fractionable) -> new ApproxFraction(BigDecimalMath.tan(fractionable.fractionValue().toDecimal(), CONTEXT)), UnaryOperatorType.PREFIX);
    public final static Operator operatorL10 = new UnaryOperator(Token.LOG10,                      (fractionable) -> new ApproxFraction(BigDecimalMath.log10(fractionable.fractionValue().toDecimal(), CONTEXT)), UnaryOperatorType.PREFIX);
    public final static Operator operatorLo2 = new UnaryOperator(Token.LOG2,                       (fractionable) -> new ApproxFraction(BigDecimalMath.log2(fractionable.fractionValue().toDecimal(), CONTEXT)), UnaryOperatorType.PREFIX);
    public final static Operator operatorLon = new UnaryOperator(Token.LN,                         (fractionable) -> new ApproxFraction(BigFunctions.ln(fractionable.fractionValue().toDecimal(), CONTEXT.getPrecision())), UnaryOperatorType.PREFIX);

    public final static Operator operatorAbs = new BoundingOperator(Token.ABS, Token.ABS,          (fractionable) -> fractionable.fractionValue().abs(), "Absolute value");
    public final static Operator operatorParen = new BoundingOperator(Token.LPAREN, Token.RPAREN,  (fractionable) -> fractionable, "Parenthesis");


    public Tree<Payload> getTree () {
        return tree;
    }

    private Tree<Payload> tree;

    public Expression () {
        tree = new Tree<>(new Node<>(new MyValueProvider().valueOf("0")));
    }

    public Expression (Fractionable initialValue) {
        this(new Tree<>(new Node<>(initialValue)));
    }

    public Expression (Tree<Payload> expressionTree) {
        this.tree = expressionTree;
    }

    @SuppressWarnings("UnusedReturnValue")
    public Expression makeBinaryOperation (Operator operator, Expression otherOperand) {
        if (operator.getOperatorType() != OperatorType.BINARY) {
            throw new RuntimeException(operator.toString() + " is not a binary operator!");
        }

        var newParent = new Node<Payload>(operator);
        newParent.addChild(tree.getParentNode(), otherOperand.tree.getParentNode());
        tree = new Tree<>(newParent);

        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public Expression makeBinaryOperation (BinaryOperator operator, Fractionable otherOperand) {
        if (operator.getOperatorType() != OperatorType.BINARY) {
            throw new RuntimeException(operator.toString() + " is not a binary operator!");
        }

        var newParent = new Node<Payload>(operator);
        newParent.addChild(tree.getParentNode(), new Node<>(otherOperand));
        tree = new Tree<>(newParent);

        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public Expression makeUnaryOperation (UnaryOperator operator) {
        if (operator.getOperatorType() != OperatorType.UNARY) {
            throw new RuntimeException(operator.toString() + " is not an unary operator!");
        }

        var newParent = new Node<Payload>(operator);
        newParent.addChild(tree.getParentNode());
        tree = new Tree<>(newParent);

        return this;
    }

    public Fractionable reduce () {
        return reduce(getTree().getParentNode());
    }

    private Fractionable reduce (Node<Payload> currentNode) {
        if (currentNode.getValue().isFraction()) {
            return  (Fraction) currentNode.getValue();
        } else {
            Operator operator = (Operator) currentNode.getValue();

            var children = currentNode.getChildren();
            var evaluated = children.stream().map(this::reduce).collect(Collectors.toCollection(ArrayList::new));

            if (evaluated.size() != operator.getArity()) {
                throw new RuntimeException("Wrong number of operands " + evaluated + " for operator " + operator + "!");
            }

            switch (operator.getOperatorType()) {
                case UNARY:
                    return operator.invoke(evaluated);
                case BINARY:
                    return operator.invoke(evaluated);
                case BOUNDARY:
                    return operator.invoke(evaluated);
                default:
                    throw new RuntimeException("Unknown arity of " + operator.getArity() + " for operator " + operator);
            }
        }
    }

    @Override
    public String toString() {
        return tree.toString();
    }

    public static void main(String[] args) {
//        final Payload plus = new BinaryOperator(Token.ADD, (fractionable, fractionable2) -> fractionable.fractionValue().add(fractionable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
//        final Payload left = new Symbol(3);
//        final Payload right = new Symbol(5);
//
//        Node<Payload> parent = new Node<>(plus);
//        Node<Payload> l = new Node<>(left);
//        Node<Payload> r = new Node<>(right);
//
//        parent.addChild(l, r);
//
//        var tree = new Tree<>(parent);
//        System.out.println(tree);
//
//        final Payload mult = new BinaryOperator(Token.MULTIPLY, (fractionable, fractionable2) -> fractionable.fractionValue().multiply(fractionable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
//        Node<Payload> newParent = new Node<>(mult);
//        Node<Payload> nl = new Node<>(right);
//
//        newParent.addChild(nl, parent);
//
//        tree = new Tree<>(newParent);
//        System.out.println(tree);
//        System.out.println("---");

//        Expression expression = new Expression();
//        expression.makeBinaryOperation(operatorAdd, new Symbol("10"));
//        expression.makeUnaryOperation(operatorNeg);
//        expression.makeBinaryOperation(operatorDiv, new Symbol("4"));
//        expression.makeUnaryOperation(operatorSqr);
////        System.out.println(expression);
//
//        for (var a : expression.tree) {
//            System.out.println(a + "::");
//        }

    }
}
