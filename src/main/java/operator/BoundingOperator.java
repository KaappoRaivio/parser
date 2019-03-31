package operator;

import lexer.token.Token;
import math.Fraction;
import math.Fractionatable;
import operator.unaryoperator.UnaryOperator;
import operator.unaryoperator.UnaryOperatorType;

public class BoundingOperator extends UnaryOperator {
    private final Token rightToken;

    public Token getRightToken () {
        return rightToken;
    }

    public Token getLeftToken () {
        return super.getTokenType();
    }

    public BoundingOperator(Token leftToken, Token rightToken, java.util.function.UnaryOperator<Fractionatable> function) {
        super(leftToken, function, UnaryOperatorType.PREFIX);
        this.rightToken = rightToken;
    }
}
