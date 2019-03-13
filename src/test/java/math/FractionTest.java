package math;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("WeakerAccess")
class FractionTest {
    @Test
    public void shouldEqualToItself () {
        assertEquals(new Fraction(3, 4), new Fraction(6, 8));
        assertEquals(new Fraction(1, 1), new Fraction(5, 5));
    }

    @Test
    public void shouldThrowError_ifDenominatorZero () {
        assertThrows(RuntimeException.class, () -> new Fraction(4, 0));
    }

//    @Test
//    public void testExpanding () {
//        assertEquals(new Fraction(6, 8).expand(2), new Fraction(12, 16));
//    }

    @Test
    public void shouldAddNumbers () {
        assertEquals(new Fraction(2, 3).add(3), new Fraction(11, 3));
        assertEquals(new Fraction(5, 6).add(new Fraction(3, 4)), new Fraction(19, 12));
    }

}