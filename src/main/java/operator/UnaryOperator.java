package operator;

import lexer.token.Token;
import math.Fractionatable;

public class UnaryOperator {
    private java.util.function.UnaryOperator<Fractionatable> function;

    public UnaryOperatorType getUnaryOperatorType() {
        return unaryOperatorType;
    }

    private UnaryOperatorType unaryOperatorType;
    private Token token;

    public UnaryOperator(Token token, java.util.function.UnaryOperator<Fractionatable> function, UnaryOperatorType unaryOperatorType) {
        this.token = token;
        this.function = function;
        this.unaryOperatorType = unaryOperatorType;
    }

    public Fractionatable invoke (Fractionatable a) {
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
        new UnaryOperator(Token.SUBTRACT, fractionizeable -> fractionizeable.fractionValue().negate(), UnaryOperatorType.SUFFIX);
    }

    public Token getTokenType() {
        return token;
    }
}
