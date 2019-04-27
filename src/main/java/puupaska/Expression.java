package puupaska;

import lexer.token.Token;
import operator.binaryoperator.BinaryOperator;
import operator.binaryoperator.EvaluatingOrder;
import operator.genericoperator.Operator;
import operator.genericoperator.OperatorType;
import operator.unaryoperator.UnaryOperator;
import operator.unaryoperator.UnaryOperatorType;

public class Expression {
    public final static Operator operatorAdd = new BinaryOperator(Token.ADD, (fractionatable, fractionatable2) -> fractionatable.fractionValue().add(fractionatable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
    public final static Operator operatorSub = new BinaryOperator(Token.SUBTRACT, (fractionatable, fractionatable2) -> fractionatable.fractionValue().subtract(fractionatable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
    public final static Operator operatorMul = new BinaryOperator(Token.MULTIPLY, (fractionatable, fractionatable2) -> fractionatable.fractionValue().multiply(fractionatable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
    public final static Operator operatorDiv = new BinaryOperator(Token.DIVIDE, (fractionatable, fractionatable2) -> fractionatable.fractionValue().divide(fractionatable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
    public final static Operator operatorPow = new BinaryOperator(Token.POWER, (fractionatable, fractionatable2) -> fractionatable.fractionValue().power(fractionatable2.fractionValue()), EvaluatingOrder.RIGHT_TO_LEFT);
    public final static Operator operatorSqr = new UnaryOperator(Token.SQRT, fractionatable -> fractionatable.fractionValue().root(2), UnaryOperatorType.PREFIX);
    public final static Operator operatorNeg = new UnaryOperator(Token.SUBTRACT, fractionatable -> fractionatable.fractionValue().negate(), UnaryOperatorType.PREFIX);

    private Tree<Payload> tree;

    public Expression () {
        tree = new Tree<>(new Node<>(new Symbol(0)));
    }

    public Expression (Symbol initialValue) {
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
    public Expression makeBinaryOperation (Operator operator, Symbol otherOperand) {
        if (operator.getOperatorType() != OperatorType.BINARY) {
            throw new RuntimeException(operator.toString() + " is not a binary operator!");
        }

        var newParent = new Node<Payload>(operator);
        newParent.addChild(tree.getParentNode(), new Node<>(otherOperand));
        tree = new Tree<>(newParent);

        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public Expression makeUnaryOperation (Operator operator) {
        if (operator.getOperatorType() != OperatorType.UNARY) {
            throw new RuntimeException(operator.toString() + " is not an unary operator!");
        }

        var newParent = new Node<Payload>(operator);
        newParent.addChild(tree.getParentNode());
        tree = new Tree<>(newParent);

        return this;
    }

    @Override
    public String toString() {
        return tree.toString();
    }

    public static void main(String[] args) {
//        final Payload plus = new BinaryOperator(Token.ADD, (fractionatable, fractionatable2) -> fractionatable.fractionValue().add(fractionatable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
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
//        final Payload mult = new BinaryOperator(Token.MULTIPLY, (fractionatable, fractionatable2) -> fractionatable.fractionValue().multiply(fractionatable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
//        Node<Payload> newParent = new Node<>(mult);
//        Node<Payload> nl = new Node<>(right);
//
//        newParent.addChild(nl, parent);
//
//        tree = new Tree<>(newParent);
//        System.out.println(tree);
//        System.out.println("---");

        Expression expression = new Expression();
        expression.makeBinaryOperation(operatorAdd, new Symbol(10));
        expression.makeUnaryOperation(operatorNeg);
        expression.makeBinaryOperation(operatorDiv, new Symbol(4));
        expression.makeUnaryOperation(operatorSqr);
//        System.out.println(expression);

        for (var a : expression.tree) {
            System.out.println(a + "::");
        }

    }
}
