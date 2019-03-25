package operator;

import lexer.token.Token;
import math.Fraction;

public interface Operator<T extends Fraction> {
    Token getTokenType ();
}
