package operator;

import lexer.UnknownTokenException;
import lexer.token.FoundToken;
import lexer.token.Token;
import math.Fraction;

import java.util.Arrays;
import java.util.List;

public class OperatorGroup<T extends Fraction> {
    private List<Operator<T>> operators;

    @SafeVarargs
    public OperatorGroup(Operator<T>... operators) {
        this.operators = Arrays.asList(operators);
    }

    public List<Operator<T>> getOperators() {
        return operators;
    }

    public Operator<T> getOperator(FoundToken operator) {
        for (Operator<T> a : operators) {
            if (a.getTokenType().equals(operator.getTokenType())) {
                return a;
            }
        }

        throw new UnknownTokenException("Unknown token " + operator + "!");
    }
}
