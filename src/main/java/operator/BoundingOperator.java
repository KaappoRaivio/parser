package operator;

import lexer.token.Token;
import math.Fraction;

public class BoundingOperator<T extends Fraction> extends UnaryOperator<T>{
    private final Token rightToken;

    public BoundingOperator(Token leftToken, Token rightToken, java.util.function.UnaryOperator<T> function) {
        super(leftToken, function, UnaryOperatorType.PREFIX);
        this.rightToken = rightToken;
    }
}
