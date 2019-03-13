package math;

import java.math.BigInteger;
import java.util.Objects;
import java.util.stream.Stream;

public class Fraction {
    private BigInteger numerator;
    private BigInteger denominator;

    public Fraction(BigInteger numerator, BigInteger denominator) {
        this(numerator, denominator, true);
    }

    @Override
    public boolean equals (Object o) {
        if (o == null) {
            return false;
        } else if (this == o) {
            return true;
        } else if (o instanceof Integer) {
            return numerator.equals(new BigInteger(String.valueOf((int) o))) && denominator.equals(BigInteger.ONE);
        } else if (o instanceof Fraction) {
            Fraction other = (Fraction) o;
            Fraction a = expand(other.denominator);
            Fraction b = other.expand(denominator);

            return a.numerator.equals(b.numerator) && a.denominator.equals(b.denominator);
        } else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

    public Fraction (int numerator, int denominator) {
        this(new BigInteger(String.valueOf(numerator)), new BigInteger(String.valueOf(denominator)));
    }

    public Fraction (BigInteger numerator, BigInteger denominator, boolean compact) {
        if (denominator.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("Denominator cannot be zero!");
        }

        this.numerator = numerator;
        this.denominator = denominator;


        if (compact) {
            BigInteger gcd = gcd(this.numerator, this.denominator);
            this.numerator = this.numerator.divide(gcd);
            this.denominator = this.denominator.divide(gcd);
        }
    }

    public BigInteger value () {
        return numerator.divide(denominator);
    }

    public Fraction add (Fraction other) {
        Fraction a = this.expand(other.denominator);
        Fraction b = other.expand(this.denominator);

        return new Fraction(a.numerator.add(b.numerator), a.denominator);
    }

    public Fraction add (int other) {
        return add(new Fraction(new BigInteger(String.valueOf(other)), BigInteger.ONE));
    }

    public Fraction subtract (Fraction other) {
        return add(other.negate());
    }

    public Fraction subtract (int other) {
        return subtract(new Fraction(numerator.multiply(new BigInteger(String.valueOf(other))), denominator)).compact();
    }

    public Fraction negate () {
        return new Fraction(numerator.negate(), denominator);
    }

    public Fraction multiply (Fraction other) {
        return new Fraction(numerator.multiply(other.numerator), denominator.multiply(other.denominator)).compact();
    }

    public Fraction multiply (int other) {
        return new Fraction(numerator.multiply(new BigInteger(String.valueOf(other))), denominator).compact();
    }

    private Fraction compact () {
        BigInteger gcd = gcd(numerator, denominator);
        return reduce(gcd);
    }

    public Fraction invert () {
        return new Fraction(denominator, numerator);
    }

    public Fraction divide (Fraction other) {
        return new Fraction(numerator.multiply(other.denominator), denominator.multiply(other.numerator)).compact();
    }

    public Fraction divide (int other) {
        return new Fraction(numerator, denominator.multiply(new BigInteger(String.valueOf(other)))).compact();
    }

    private Fraction reduce (BigInteger amount) {
        return new Fraction(numerator.divide(amount), denominator.divide(amount));
    }

    Fraction expand(BigInteger amount) {
        return new Fraction(numerator.multiply(amount), denominator.multiply(amount), false);
    }

    private static BigInteger gcd (BigInteger num, BigInteger den) {
        return num.gcd(den);
    }

    private static BigInteger lcm (BigInteger num, BigInteger den) {
        return Stream.of(num, den).reduce(new BigInteger("1"), (x, y) -> x.multiply(y.divide(gcd(x, y))));
    }


    @Override
    public String toString() {
        return "(" + numerator + " / " + denominator + ")";
    }
}
