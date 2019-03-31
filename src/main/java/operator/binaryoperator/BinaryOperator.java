package operator.binaryoperator;

import lexer.token.Token;
import math.Fractionatable;

public class BinaryOperator {
    private java.util.function.BinaryOperator<Fractionatable> function;
    private EvaluatingOrder evaluatingOrder;
    private Token token;

    public BinaryOperator(Token token, java.util.function.BinaryOperator<Fractionatable> function, EvaluatingOrder evaluatingOrder) {
        this.function = function;
        this.evaluatingOrder = evaluatingOrder;
        this.token = token;
    }

    public EvaluatingOrder getEvaluatingOrder() {
        return evaluatingOrder;
    }

    public Token getToken() {
        return token;
    }

    public Fractionatable invoke (Fractionatable t1, Fractionatable t2) {
        return function.apply(t1, t2);
    }

    Token getTokenType() {
        return token;
    }
}
