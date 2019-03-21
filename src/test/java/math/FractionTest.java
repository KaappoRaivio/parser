package math;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"WeakerAccess", "AssertEqualsBetweenInconvertibleTypes"})
class FractionTest {
    @Test
    public void shouldEqualToItself () {
        assertEquals(new Fraction(3, 4), new Fraction(6, 8));
        assertEquals(new Fraction(1, 1), new Fraction(5, 5));
        assertEquals(new Fraction(10, 5),2);
    }

    @Test
    public void shouldThrowError_ifDenominatorIsZero () {
        assertThrows(ArithmeticException.class, () -> new Fraction(4, 0));
    }

    @Test
    public void testExpanding () {
        assertEquals(new Fraction(6, 8).expand(BigInteger.TWO), new Fraction(12, 16));
    }

    @Test
    public void shouldAddNumbers () {
        assertEquals(new Fraction(2, 3).add(3), new Fraction(11, 3));
        assertEquals(new Fraction(2, 4).add(3), new Fraction(7, 2));
        assertEquals(new Fraction(5, 6).add(new Fraction(3, 4)), new Fraction(19, 12));
        assertEquals(new Fraction(-1, 30).subtract(new Fraction(-1, 20)), new Fraction(1, 60));
    }

    @Test
    public void shouldMultiplyNumbers () {
        assertEquals(new Fraction(2, 3).multiply(3), new Fraction(6, 3));
        assertEquals(new Fraction(5, 2).multiply(new Fraction(2, 5)), 1);
    }

    @Test
    public void shouldDivideMumbers () {
        assertEquals(new Fraction(9, 34).divide(2), new Fraction(9, 68));
        assertEquals(new Fraction(1 , 10).divide(new Fraction(3, 4)), new Fraction(4, 30));
    }

    @Test
    public void shouldParseNumbers () {
//        assertEquals(Fraction.fromDecimal("0.33", true), new Fraction(1, 3));
//        assertEquals(Fraction.fromDecimal("0.5", false), new Fraction(1, 2));
//        assertEquals(Fraction.fromDecimal("0.5", true), new Fraction(1, 2));
//        assertEquals(Fraction.fromDecimal("5,42", true), new Fraction(542, 100));
//        assertEquals(Fraction.fromDecimal("5,4242", true), new Fraction(179, 33));
        assertEquals(Fraction.fromRepeatingDecimal("551.23123"), new Fraction(183560, 333));
        assertEquals(Fraction.fromRepeatingDecimal("0.33"), new Fraction(1, 3));
        assertEquals(Fraction.fromRepeatingDecimal("5.4242"), new Fraction(179, 33));
        assertEquals(Fraction.fromRepeatingDecimal("033,30"), new Fraction(100, 3));
        assertEquals(Fraction.fromRepeatingDecimal("0.285714285714"), new Fraction(2, 7));
        assertEquals(Fraction.fromRepeatingDecimal("1.23123"), new Fraction(410, 333));




    }

}