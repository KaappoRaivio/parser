package parser;

import math.Calculator;
import math.Fraction;
import operator.OperatorGroup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("WeakerAccess")
class ParserTest {
    private OperatorGroup<Fraction> operatorGroup = OperatorGroup.UNARY_OPERATOR_GROUP;

    @Test
    public void shouldThrowError_whenInputIsEmpty () {
        assertThrows(RuntimeException.class, () -> new Parser<>("", new Calculator<Fraction>(), operatorGroup));
    }

    @Test
    public void shouldIgnoreWhitespace () {
        assertEquals(new Parser<>("3      +   2", new Calculator<Fraction>(), operatorGroup).parse(), new Fraction(5, 1));
    }

    @Test
    public void shouldIgnoreBrackets () {
        assertEquals(new Parser<>("((((((3))))))", new Calculator<Fraction>(), operatorGroup).parse(), new Fraction(3, 1));
    }
}