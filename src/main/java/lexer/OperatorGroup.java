package lexer;

import math.Fraction;

import java.util.Arrays;
import java.util.List;

public class OperatorGroup<T extends Fraction> {
    private List<Operator<T>> operators;

    @SafeVarargs
    public OperatorGroup(Operator<T>... operators) {
        this.operators = Arrays.asList(operators);
    }
}
