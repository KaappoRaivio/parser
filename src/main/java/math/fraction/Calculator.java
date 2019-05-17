package math.fraction;

;

import puupaska.Expression;
import puupaska.Node;
import puupaska.Payload;
import puupaska.Tree;

import static puupaska.Expression.operatorAbs;


public class Calculator<T extends Fraction> {
    public Expression abs(Expression t1) {
        return new Expression(new Tree<>(new Node<Payload>(operatorAbs).addChild(t1.getTree().getParentNode())));
    }

    public Fractionable valueOf (String string) {
        return T.valueOf(string);
    }
}
