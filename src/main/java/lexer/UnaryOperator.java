package lexer;

import lexer.token.Token;
import math.Fraction;

public class UnaryOperator<T extends Fraction> implements Operator<Fraction> {
    private java.util.function.UnaryOperator<T> function;
    private Token token;

    public UnaryOperator(Token token, java.util.function.UnaryOperator<T> function) {
        this.token = token;
        this.function = function;
    }

    public T invoke (T a) {
        return function.apply(a);
    }

    public static void main(String[] args) {
        new UnaryOperator<Fraction>(Token.SUBTRACT, Fraction::negate);
    }
}
