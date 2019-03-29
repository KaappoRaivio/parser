package operator;

import lexer.token.Token;
import math.Fraction;

public class UnaryOperator<T extends Fraction> {
    private java.util.function.UnaryOperator<T> function;

    public UnaryOperatorType getUnaryOperatorType() {
        return unaryOperatorType;
    }

    private UnaryOperatorType unaryOperatorType;
    private Token token;

    public UnaryOperator(Token token, java.util.function.UnaryOperator<T> function, UnaryOperatorType unaryOperatorType) {
        this.token = token;
        this.function = function;
        this.unaryOperatorType = unaryOperatorType;
    }

    public T invoke (T a) {
        return function.apply(a);
    }

    @Override
    public String toString() {
        return "UnaryOperator{" +
                "function=" + function +
                ", unaryOperatorType=" + unaryOperatorType +
                ", token=" + token +
                '}';
    }

    public static void main(String[] args) {
        new UnaryOperator<Fraction>(Token.SUBTRACT, Fraction::negate, UnaryOperatorType.SUFFIX);
    }

    public Token getTokenType() {
        return token;
    }
}
