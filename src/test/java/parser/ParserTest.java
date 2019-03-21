package parser;

import math.Calculator;
import math.Fraction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("WeakerAccess")
class ParserTest {
    @Test
    public void shouldThrowError_whenInputIsEmpty () {
        assertThrows(RuntimeException.class, () -> new Parser<>("", new Calculator<Fraction>()));
    }

    @Test
    public void shouldIgnoreWhitespace () {
        assertEquals(new Parser<>("3 + 2", new Calculator<Fraction>()).parse(), new Fraction(5, 1));
    }
}