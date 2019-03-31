package operator.unaryoperator;

import lexer.token.Token;
import math.Fractionatable;

public class UnaryOperator {
    private java.util.function.UnaryOperator<Fractionatable> function;
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

    public UnaryOperatorType getUnaryOperatorType() {
        return unaryOperatorType;
    }

    public Token getTokenType() {
        return token;
    }

    @Override
    public String toString() {
        return "UnaryOperator{" +
                "function=" + function +
                ", unaryOperatorType=" + unaryOperatorType +
                ", token=" + token +
                '}';
    }
}
