package math;

import misc.Pair;
import misc.StringUtil;

import java.math.BigInteger;
import java.util.Objects;
import java.util.stream.Stream;

public class Fraction {
    private BigInteger numerator;
    private BigInteger denominator;

    public Fraction(BigInteger numerator, BigInteger denominator) {
        this(numerator, denominator, true);
    }

    public Fraction(String numerator, String denominator) {
        this(new BigInteger(numerator), new BigInteger(denominator));
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
        return new Fraction(numerator.multiply(BigInteger.valueOf(other)), denominator).compact();
    }

    public Fraction pow (int other) {
        return new Fraction(numerator.pow(other), denominator.pow(other));
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

    public static Fraction fromDecimal (String representation, boolean repeating) {
        if (repeating) {
            if (!representation.contains(".") && !representation.contains(",")) {
                throw new NumberFormatException("No comma or decimal point found in number " + representation + "!");
            }
            return fromRepeatingDecimal(representation);
        } else {
            int commaPlace = StringUtil.getCurrentCommaPlace(representation);
//            System.out.println(Integer.parseInt(representation) + ", " + (int) Math.pow(10, representation.length()));
            representation = representation.replaceAll("[.,]", "");
            return new Fraction(Integer.parseInt(representation), (int) Math.pow(10, representation.length() - commaPlace));
        }
    }

    static Fraction fromRepeatingDecimal(String x) {
        x = x.replaceAll("^0+", "");
        x = x.replaceAll("0+$", "");

        Pair<String, String> pattern = StringUtil.findPattern(x);

        String base = pattern.getK();
        String cycle = pattern.getV();

        int commaPlace = StringUtil.getCurrentCommaPlace(x);
        int coefficient = base.length() - commaPlace;


        var frac1 = new Fraction(StringUtil.moveComma(base, -coefficient), "1");
        var frac2 = new Fraction(new BigInteger(cycle), new BigInteger(StringUtil.moveComma("1.0", cycle.length())).subtract(BigInteger.ONE)).multiply((int) Math.pow(10, -coefficient));


        return frac1.add(frac2);
    }

    private static int findCommaplace (double number) {
        return (int) Math.log10(number);
    }


    public static void main(String[] args) {
//        System.out.println(findPattern("3,3323332"));
//        System.out.println(fromDecimal("33", false));
        System.out.println(fromRepeatingDecimal("51,23123"));
        System.out.println(fromRepeatingDecimal("3.3"));
    }
}
