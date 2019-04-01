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
        assertNotEquals(new Fraction(2, 7),new Fraction(2, 7, true));
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
        assertEquals(Fraction.fromRepeatingDecimal("0.33"), new Fraction(1, 3));
        assertEquals(Fraction.fromDecimal("0.5", false), new Fraction(1, 2));
        assertEquals(Fraction.fromDecimal("0.5", false), new Fraction(1, 2));
        assertEquals(Fraction.fromDecimal("5,42", false), new Fraction(542, 100));
        assertEquals(Fraction.fromDecimal("5,4242", true), new Fraction(179, 33));
        assertEquals(Fraction.fromDecimal("551.23123", true), new Fraction(183560, 333));
        assertEquals(Fraction.fromDecimal("0.33", true), new Fraction(1, 3));
        assertEquals(Fraction.fromDecimal("5.4242", true), new Fraction(179, 33));
        assertEquals(Fraction.fromDecimal("033,3", true), new Fraction(100, 3));
        assertEquals(Fraction.fromDecimal("0.285714285714", true), new Fraction(2, 7));
        assertEquals(Fraction.fromDecimal("1.23123", true), new Fraction(410, 333));
        assertEquals(Fraction.fromDecimal("123123.123", true), new Fraction(41_000_000, 333));
        assertEquals(Fraction.fromDecimal("0.000000000123123", true), new Fraction("410", "3330000000000"));
    }

    @Test
    public void nthRoot () {
        assertEquals(new Fraction(1000, 1).root(3), new Fraction(10, 1));
        assertEquals(new Fraction(16, 9).root(2), new Fraction(4, 3, false));
        assertEquals(new Fraction(2, 1).root(12), Fraction.fromDecimal("1.05946309", true));
    }

    @Test
    public void power () {
        System.out.println(new Fraction(2, 1).root(2).power(new Fraction(2, 1)));
    }

}