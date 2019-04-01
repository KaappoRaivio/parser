package operator.genericoperator;

import lexer.token.FoundToken;

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
