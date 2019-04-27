package operator.unaryoperator;

import lexer.token.Token;
import math.fraction.Fractionatable;
import operator.genericoperator.Operator;
import operator.genericoperator.OperatorType;
import puupaska.Payload;

public class UnaryOperator implements Operator, Payload {
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
//        return "UnaryOperator{" +
//                "unaryOperatorType=" + unaryOperatorType +
//                ", token=" + token +
//                '}';
        return token.toString();
    }

    @Override
    public Token getTokenType() {
        return token;
    }

    @Override
    public boolean isOperator() {
        return true;
    }

    @Override
    public boolean isSymbol() {
        return false;
    }
}
