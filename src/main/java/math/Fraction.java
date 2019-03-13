package math;

import java.math.BigInteger;
import java.util.Objects;
import java.util.stream.Stream;

public class Fraction {
    private BigInteger numerator;
    private BigInteger denominator;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraction other = (Fraction) o;
        Fraction a = expand(other.denominator);
        Fraction b = other.expand(denominator);

        return a.numerator.equals(b.numerator) && a.denominator.equals(b.denominator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

    public Fraction (int numerator, int denominator) {
        this(new BigInteger(String.valueOf(numerator)), new BigInteger(String.valueOf(denominator)));
    }

    public Fraction (BigInteger numerator, BigInteger denominator) {
        if (denominator.equals(BigInteger.ZERO)) {
            throw new RuntimeException("Denominator cannot be zero!");
        }

        this.numerator = numerator;
        this.denominator = denominator;


        BigInteger gcd = gcd(this.numerator, this.denominator);
        this.numerator = this.numerator.divide(gcd);
        this.denominator = this.denominator.divide(gcd);
    }


    public Fraction add (Fraction other) {
        Fraction a = this.expand(other.denominator);
        Fraction b = other.expand(this.denominator);

        System.out.println(a + ", " + b);
        return new Fraction(a.numerator.add(b.numerator), a.denominator);
    }

    public Fraction add (int other) {
        return add(new Fraction(denominator.multiply(new BigInteger(String.valueOf(other))), BigInteger.ONE)).compact();
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
        return new Fraction(numerator.multiply(amount), denominator.multiply(amount));
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
