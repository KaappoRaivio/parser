package lexer;

import lexer.token.Token;
import math.Fraction;

import java.util.Arrays;
import java.util.List;

public class OperatorStack<T extends Fraction> {
    public static final OperatorStack<Fraction> NORMAL_OPERATORS = new OperatorStack<Fraction>(
            new OperatorGroup<>(new BinaryOperator<>(Token.MULTIPLY, Fraction::multiply), new BinaryOperator<>(Token.DIVIDE, Fraction::divide)),
            new OperatorGroup<>(new BinaryOperator<>(Token.ADD, Fraction::add), new BinaryOperator<>(Token.SUBTRACT, Fraction::subtract)),
            new OperatorGroup<>(new UnaryOperator<>(Token.SUBTRACT, Fraction::negate)),
            new OperatorGroup<>(new BoundingOperator<>(Token.ABS, Token.ABS, Fraction::abs), new BoundingOperator<>(Token.LPAREN, Token.RPAREN,  (a) -> a))
    );

    public List<OperatorGroup<T>> getOperatorGroups() {
        return operatorGroups;
    }

    private List<OperatorGroup<T>> operatorGroups;

    @SafeVarargs
    public OperatorStack(OperatorGroup<T>... operatorGroups) {
        this.operatorGroups = Arrays.asList(operatorGroups);
    }

    public static void main(String[] args) {


    }
}
