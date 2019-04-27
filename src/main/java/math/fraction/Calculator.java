package math.fraction;

import lexer.token.Token;
import operator.unaryoperator.UnaryOperator;
import operator.unaryoperator.UnaryOperatorType;
import puupaska.Expression;
import puupaska.Symbol;

public class Calculator<T extends Symbol> {

    public Expression abs(Expression t1) {
        return t1.makeUnaryOperation(new UnaryOperator(Token.ABS, (a)-> a.fractionValue().abs(), UnaryOperatorType.PREFIX));
    }

    public Symbol valueOf (String string) {
        return T.valueOf(string);
    }
}
