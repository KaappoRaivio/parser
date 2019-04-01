package operator.unaryoperator;

import lexer.token.Token;
import math.Fractionatable;
import operator.genericoperator.Operator;
import operator.genericoperator.OperatorType;

public class UnaryOperator implements Operator {
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

    @Override
    public OperatorType getOperatorType() {
        return OperatorType.UNARY;
    }

    @Override
    public String toString() {
        return "UnaryOperator{" +
                ", unaryOperatorType=" + unaryOperatorType +
                ", token=" + token +
                '}';
    }

    @Override
    public Token getTokenType() {
        return token;
    }

}
