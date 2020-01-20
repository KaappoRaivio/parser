package operator.unaryoperator;

import lexer.token.Token;
import math.fraction.fraction.Fraction;
import operator.genericoperator.Operator;
import operator.genericoperator.OperatorType;
import expression.Payload;

import java.util.List;

public class UnaryOperator implements Operator, Payload {
    private java.util.function.UnaryOperator<Fraction> function;
    private UnaryOperatorType unaryOperatorType;
    private Token token;



    public UnaryOperator(Token token, java.util.function.UnaryOperator<Fraction> function, UnaryOperatorType unaryOperatorType) {
        this.token = token;
        this.function = function;
        this.unaryOperatorType = unaryOperatorType;
    }

    public Fraction invoke (List<Fraction> operands) {
        if (operands.size() != getArity()) {
            throw new RuntimeException("Unary operator " + toString() + " cannot be applied to " + operands + "!");
        } else {
            return function.apply(operands.get(0));
        }
    }

    public UnaryOperatorType getUnaryOperatorType () {
        return unaryOperatorType;
    }

    @Override
    public OperatorType getOperatorType() {
        return OperatorType.UNARY;
    }

    @Override
    public String toString() {
        return token.toString();
    }

    @Override
    public Token getTokenType() {
        return token;
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
