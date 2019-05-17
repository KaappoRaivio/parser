package operator.genericoperator;

import lexer.token.FoundToken;
import operator.BoundingOperator;
import operator.unaryoperator.UnaryOperator;
import puupaska.Expression;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import java.util.stream.Collectors;

public class GenericOperatorStack {
    private List<GenericOperatorGroup> operatorGroups;

    public GenericOperatorStack(GenericOperatorGroup... operators) {
        this(Arrays.stream(operators).collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return operatorGroups.toString();
    }

    public GenericOperatorGroup getSuffixOperators () {
        return new GenericOperatorGroup(
                OperatorType.UNARY,
                Expression.operatorEll,
                Expression.operatorFac
        );
    }

    public GenericOperatorGroup getBoundaryOperators () {
        return new GenericOperatorGroup(OperatorType.UNARY,
                Expression.operatorAbs,
                Expression.operatorParen
        );
    }

    public GenericOperatorStack(List<GenericOperatorGroup> operatorGroups) {
        this.operatorGroups = operatorGroups;
    }

    public GenericOperatorGroup getGroup (int index) {
        return operatorGroups.get(index);
    }

    public int size () {
        return operatorGroups.size();
    }

    public boolean isOperatorRecognized(FoundToken token) {
        return operatorGroups.stream().anyMatch(genericOperatorGroup -> genericOperatorGroup.isOperator(token));
    }
}
