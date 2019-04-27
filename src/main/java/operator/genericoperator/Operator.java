package operator.genericoperator;

import lexer.token.Token;
import puupaska.Payload;

public interface Operator extends Payload {
    OperatorType getOperatorType ();
    Token getTokenType() ;
}
