package parser;

;

import expression.Expression;
import expression.Node;
import expression.Payload;
import expression.Tree;
import math.fraction.Fraction;
import math.fraction.Fractionable;

import static expression.Expression.operatorAbs;


public interface ValueProvider<T extends Fractionable> {
    T valueOf (String string);
}
