package expression;

import math.fraction.fraction.Fraction;
import math.fraction.fraction.Fractionable;
import operator.binaryoperator.BinaryOperator;
import operator.genericoperator.Operator;
import operator.genericoperator.OperatorType;
import operator.unaryoperator.UnaryOperator;
import parser.MyNumberParser;

import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Expression {
    public static final MathContext CONTEXT = MathContext.DECIMAL64;


    public Tree<Payload> getTree () {
        return tree;
    }

    private Tree<Payload> tree;

    public Expression () {
        tree = new Tree<>(new Node<>(new MyNumberParser().valueOf("0")));
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

        Node<Payload> newParent = new Node<>(operator);
        newParent.addChild(tree.getParentNode(), otherOperand.tree.getParentNode());
        tree = new Tree<>(newParent);

        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public Expression makeBinaryOperation (BinaryOperator operator, Fractionable otherOperand) {
        if (operator.getOperatorType() != OperatorType.BINARY) {
            throw new RuntimeException(operator.toString() + " is not a binary operator!");
        }

        Node<Payload> newParent = new Node<Payload>(operator);
        newParent.addChild(tree.getParentNode(), new Node<>(otherOperand));
        tree = new Tree<>(newParent);

        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public Expression makeUnaryOperation (UnaryOperator operator) {
        if (operator.getOperatorType() != OperatorType.UNARY) {
            throw new RuntimeException(operator.toString() + " is not an unary operator!");
        }

        Node<Payload> newParent = new Node<Payload>(operator);
        newParent.addChild(tree.getParentNode());
        tree = new Tree<>(newParent);

        return this;
    }

    public Fractionable reduce () {
        return reduce(getTree().getParentNode());
    }

    private Fraction reduce (Node<Payload> currentNode) {
        if (currentNode.getValue().isFraction()) {
            return  (Fraction) currentNode.getValue();
        } else {
            Operator operator = (Operator) currentNode.getValue();

            List<Node<Payload>> children = currentNode.getChildren();
            ArrayList<Fraction> evaluated = children.stream().map(this::reduce).collect(Collectors.toCollection(ArrayList::new));

            if (evaluated.size() != operator.getArity()) {
                throw new RuntimeException("Wrong number of operands " + evaluated + " for operator " + operator + "!");
            }

            return operator.invoke(evaluated);
        }
    }

    @Override
    public String toString() {
        return tree.toString();
    }

}
