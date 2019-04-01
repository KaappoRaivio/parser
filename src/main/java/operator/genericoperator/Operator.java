package operator.genericoperator;

import lexer.token.Token;

public interface Operator {
    OperatorType getOperatorType ();
    Token getTokenType() ;
}
