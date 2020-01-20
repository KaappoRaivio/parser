package operator.binaryoperator;

import lexer.token.Token;
import math.fraction.fraction.Fraction;
import operator.genericoperator.Operator;
import operator.genericoperator.OperatorType;

import java.util.List;

public class BinaryOperator implements Operator {
    private java.util.function.BinaryOperator<Fraction> function;
    private EvaluatingOrder evaluatingOrder;
    private Token token;

    public BinaryOperator (Token token, java.util.function.BinaryOperator<Fraction> function, EvaluatingOrder evaluatingOrder) {
        this.function = function;
        this.evaluatingOrder = evaluatingOrder;
        this.token = token;
    }

    public EvaluatingOrder getEvaluatingOrder () {
        return evaluatingOrder;
    }


    public Fraction invoke (List<Fraction> operands) {
        if (operands.size() != getArity()) {
            throw new RuntimeException("Binary operator " + toString() + " cannot be applied to " + operands + "!");
        }
        return function.apply(operands.get(0), operands.get(1));
    }

    @Override
    public OperatorType getOperatorType () {
        return OperatorType.BINARY;
    }

    @Override
    public String toString () {
        return token.toString();
    }

    @Override
    public Token getTokenType () {
        return token;
    }

    @Override
    public int getArity () {
        return 2;
    }

    @Override
    public boolean isOperator () {
        return true;
    }

    @Override
    public boolean isFraction () {
        return false;
    }

    public Token getToken () {
        return token;
    }
}
