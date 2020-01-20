package operator;

import lexer.token.Token;
import math.fraction.fraction.Fraction;
import operator.genericoperator.OperatorType;
import operator.unaryoperator.UnaryOperator;
import operator.unaryoperator.UnaryOperatorType;

public class BoundOperator extends UnaryOperator {
    private final Token rightToken;
    private final Token leftToken;

    private final String representation;

    public Token getRightToken () {
        return rightToken;
    }

    public Token getLeftToken () {
        return leftToken;
    }

    public BoundOperator(Token leftToken, Token rightToken, java.util.function.UnaryOperator<Fraction> function, String representation) {
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

    @Override
    public boolean isOperator() {
        return true;
    }

    @Override
    public boolean isFraction () {
        return false;
    }
}
