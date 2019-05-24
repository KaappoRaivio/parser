package math.fraction;

;

import expression.Expression;
import expression.Node;
import expression.Payload;
import expression.Tree;

import static expression.Expression.operatorAbs;


public class Calculator<T extends Fraction> {
    public Expression abs(Expression t1) {
        return new Expression(new Tree<>(new Node<Payload>(operatorAbs).addChild(t1.getTree().getParentNode())));
    }

    public Fractionable valueOf (String string) {
        return T.valueOf(string);
    }
}
