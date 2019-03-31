package operator.binaryoperator;

import java.util.List;

public class BinaryOperatorStack {
    List<BinaryOperatorGroup> operatorGroups;

    public BinaryOperatorStack(List<BinaryOperatorGroup> operatorGroups) {
        this.operatorGroups = operatorGroups;
    }

    public BinaryOperatorGroup getGroup (int index) {
        return operatorGroups.get(index);
    }

    public int size () {
        return operatorGroups.size();
    }
}
