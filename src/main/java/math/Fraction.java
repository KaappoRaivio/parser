package math;

import misc.Pair;

import java.math.BigInteger;
import java.util.Objects;
import java.util.regex.Pattern;
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

    public static Fraction fromRepeatingDecimal (String representation) {
        var pair = findPattern(representation);
        int comma = getCurrentCommaPlace(representation);

        String base = pair.getV();
        String cycle =  pair.getK();

        String stripped = representation.replaceAll("[,.]", "");

        int wantedCommaPlace1 = base.length() + cycle.length();
        int wantedCommaPlace2 = base.length();

        String cycleLeft = insertComma(wantedCommaPlace1, stripped);
        String cycleRight = insertComma(wantedCommaPlace2, stripped);

        int xCoeff1 = (int) Math.pow(10, wantedCommaPlace1 - comma);
        int xCoeff2 = (int) Math.pow(10, wantedCommaPlace2 - comma);

        int numerator = (int) Double.parseDouble(cycleLeft) - (int) Double.parseDouble(cycleRight);
        int denominator = xCoeff1 - xCoeff2;

        return new Fraction(numerator, denominator);
    }

    private static String insertComma (int position, String number) {
        if (position >= number.length()) {
            return insertComma(position, number + "0");
        }
        return (number.substring(0, position) + "." + number.substring(position)).replaceAll("^0*", "");
    }

    private static int getCurrentCommaPlace(String string) {
        char[] charArray = string.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char a = charArray[i];

            if (a == ',' || a == '.') {
                return i;
            }
        }

        return -1;
    }

    private static Pair<String, String> findPattern (String s) {
        s = s.replaceAll("([.,])", "");

        var pattern = Pattern.compile("^(.+?)\\1+$");

        for (int i = 0; i < s.length(); i++) {
            if (pattern.matcher(s.substring(i)).matches()) {
                return new Pair<>(s.substring(i).replaceAll("^(.+?)\\1+$", "$1"), s.substring(0, i));
            }
        }
        return new Pair<>(s, s);
    }

    public static void main(String[] args) {
//        System.out.println(findPattern("3,3323332"));
        System.out.println(fromRepeatingDecimal("10.233"));
    }
}
