package lexer;

import lexer.token.Token;
import math.Fraction;

public class BinaryOperator<T extends Fraction> implements Operator<Fraction> {
    private java.util.function.BinaryOperator<T> function;
    private Token token;

    public BinaryOperator(Token token, java.util.function.BinaryOperator<T> function) {
        this.token = token;
        this.function = function;
    }

    public T invoke (T a, T b) {
        return function.apply(a, b);
    }

    public static void main(String[] args) {
        new BinaryOperator<Fraction>(Token.ADD, Fraction::add);
    }
}
