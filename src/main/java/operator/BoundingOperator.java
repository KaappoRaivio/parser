package operator;

import lexer.token.Token;
import math.fraction.Fractionable;
import operator.genericoperator.Operator;
import operator.genericoperator.OperatorType;

public class BoundingOperator implements Operator {
    private final Token rightToken;
    private final Token leftToken;

    public Token getRightToken () {
        return rightToken;
    }

    public Token getLeftToken () {
        return leftToken;
    }

    public BoundingOperator(Token leftToken, Token rightToken, java.util.function.UnaryOperator<Fractionable> function) {
        this.leftToken = leftToken;
        this.rightToken = rightToken;
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
