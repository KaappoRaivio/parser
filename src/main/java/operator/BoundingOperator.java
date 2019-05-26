package operator;

import lexer.token.Token;
import math.fraction.Fractionable;
import operator.genericoperator.Operator;
import operator.genericoperator.OperatorType;
import operator.unaryoperator.UnaryOperator;
import operator.unaryoperator.UnaryOperatorType;

import java.util.List;

public class BoundingOperator extends UnaryOperator {
    private final Token rightToken;
    private final String representation;
    private final Token leftToken;

    public Token getRightToken () {
        return rightToken;
    }

    public Token getLeftToken () {
        return leftToken;
    }

    public BoundingOperator(Token leftToken, Token rightToken, java.util.function.UnaryOperator<Fractionable> function, String representation) {
        super(rightToken, function, UnaryOperatorType.BOUNDARY);
        this.leftToken = leftToken;
        this.rightToken = rightToken;
        this.representation = representation;
    }

    @Override
    public String toString () {
        return representation;
    }

    @Override
    public OperatorType getOperatorType() {
        return OperatorType.BOUNDARY;
    }

    @Override
    public Token getTokenType() {
        return leftToken;
    }

    @Override
    public int getArity () {
        return 1;
    }

//    @Override
//    public Fractionable invoke (List<Fractionable> operands) {
//        if (operands.size() != getArity()) {
//            throw new RuntimeException("Bounding operator " + toString() + " cannot be applied to " + operands + "!");
//        }
//
//        return
//    }

    @Override
    public boolean isOperator() {
        return true;
    }

    @Override
    public boolean isFraction () {
        return false;
    }
}
