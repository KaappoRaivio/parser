package math;

import misc.Pair;
import misc.StringUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Objects;
import java.util.stream.Stream;

public class Fraction {
    public static final int PRECISION = 10;
    private BigInteger numerator;
    private BigInteger denominator;

    private boolean approx = false;

    public Fraction(BigInteger numerator, BigInteger denominator) {
        this(numerator, denominator, false);
    }

    public Fraction (BigInteger numerator, BigInteger denominator, boolean approximation) {
        this(numerator, denominator, true, approximation);
    }

    public Fraction(String numerator, String denominator) {
        this(numerator, denominator, false);
    }

    public Fraction (String numerator, String denominator, boolean approx) {
        this(new BigInteger(numerator), new BigInteger(denominator), approx);
    }

    public Fraction(int numerator, int denominator) {
        this(numerator, denominator, false);
    }

    public Fraction (int numerator, int denominator, boolean approx) {
        this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator), approx);
    }

    public Fraction (BigInteger numerator, BigInteger denominator, boolean compact, boolean approximation) {
        if (denominator.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("Denominator cannot be zero!");
        }

        this.numerator = numerator;
        this.denominator = denominator;


        if (compact) {
            compactInPlace();
        }

        this.approx = approximation;
    }

    private void compactInPlace () {
        BigInteger gcd = gcd(this.numerator, this.denominator);
        this.numerator = this.numerator.divide(gcd);
        this.denominator = this.denominator.divide(gcd);
    }

    @Override
    public boolean equals (Object o) {
        if (o == null) {
            return false;
        } else if (this == o) {
            return true;
        } else if (o instanceof Integer) {
            return numerator.equals(new BigInteger(String.valueOf((int) o))) && denominator.equals(BigInteger.ONE) && !approx;
        } else if (o instanceof Fraction) {
            Fraction other = (Fraction) o;

            if (approx || other.approx) {
                return compareDecimal(other) && approx == other.approx;
            } else {
                Fraction a = expand(other.denominator);
                Fraction b = other.expand(denominator);


                return a.numerator.equals(b.numerator) && a.denominator.equals(b.denominator) && a.approx == b.approx;
            }

        } else {
            return false;
        }

    }

    private boolean compareDecimal (Fraction other) {
        String a = toDecimal().toString();
        String b = other.toDecimal().toString();

        for (int i = 0; i < b.length(); i++) {
            try {
                if (a.charAt(i) != b.charAt(i)) {
                    return  false;
                }
            } catch (StringIndexOutOfBoundsException e) {
                break;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

    public Fraction add (Fraction other) {
        Fraction a = this.expand(other.denominator);
        Fraction b = other.expand(this.denominator);


        return new Fraction(a.numerator.add(b.numerator), a.denominator, approx || other.approx);
    }

    public Fraction add (int other) {
        return add(new Fraction(BigInteger.valueOf(other), BigInteger.ONE));
    }

    public Fraction subtract (Fraction other) {
        return add(other.negate());
    }

    public Fraction subtract (int other) {
        return subtract(new Fraction(numerator.multiply(new BigInteger(String.valueOf(other))), denominator)).compact();
    }

    public Fraction negate () {
        return new Fraction(numerator.negate(), denominator, approx);
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

    public Fraction root (int n) {
        String a = RootCalculus.nthRoot(n, toDecimal()).toString();
//        System.out.println(a);

        if (a.contains(".")) {
            return fromDecimal(a.substring(0, a.length() - 1), true);
        } else {
            return fromDecimal(a, true);
        }
    }

    public Fraction inverse () {
        return new Fraction(denominator, numerator);
    }


    public Fraction abs() {
        return new Fraction(numerator.abs(), denominator.abs());
    }

    public BigDecimal toDecimal () {
        BigDecimal tempResult = new BigDecimal(numerator).divide(new BigDecimal(denominator), MathContext.DECIMAL128);
        String string = tempResult.toString();

        if (string.contains(".")) {
            try {
                return new BigDecimal(string.substring(0, string.length() - 1));
            } catch (NumberFormatException e) {
                return new BigDecimal(string.substring(0, string.length() - 2));
            }
        } else {
            return new BigDecimal(string);
        }
    }


    public Fraction valueOf(String string, boolean endless) {
        return fromDecimal(string, endless);
    }


    public Fraction valueOf(int integer, boolean endless) {
        return valueOf(String.valueOf(integer), endless);
    }


    public Fraction valueOf(double integer, boolean endless) {
        return valueOf(String.valueOf(integer), endless);
    }


    public Fraction valueOf(float integer, boolean endless) {
        return valueOf(String.valueOf(integer), endless);
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

    Fraction expand (BigInteger amount) {
        return new Fraction(numerator.multiply(amount), denominator.multiply(amount), false, false);
    }

    private static BigInteger gcd (BigInteger num, BigInteger den) {
        return num.gcd(den);
    }

    private static BigInteger lcm (BigInteger num, BigInteger den) {
        return Stream.of(num, den).reduce(new BigInteger("1"), (x, y) -> x.multiply(y.divide(gcd(x, y))));
    }


    @Override
    public String toString() {
        if (approx) {
            return toDecimal().toString();
        } else {
            return "(" + numerator + " / " + denominator + ")";
        }
    }



    public static Fraction fromDecimal (String representation, boolean endless) {
        if (endless) {
            return fromEndlessDecimal(representation);
        } else {
            return fromEndingDecimal(representation, false);
        }
    }

    private static Fraction fromEndlessDecimal (String representation) {
        if (!representation.contains(".") && !representation.contains(",")) {
            throw new NumberFormatException("No comma or decimal point found in number " + representation + "!");
        }

        boolean repeating = !StringUtil.findPattern(representation).getV().equals("");

        if (repeating) {
            return fromRepeatingDecimal(representation);
        } else {
            String sliced;
            try {
                sliced = representation.substring(0, PRECISION);
            } catch (IndexOutOfBoundsException e) {
                sliced = representation;
            }

            return fromEndingDecimal(sliced, true);
        }
    }

    private static Fraction fromEndingDecimal(String x, boolean approx) {
        int commaPlace = StringUtil.getCurrentCommaPlace(x);
        x = x.replaceAll("[.,]", "");
        return new Fraction(x, StringUtil.moveComma("1.0", x.length() - commaPlace), approx);
    }

    static Fraction fromRepeatingDecimal(String x) {
        x = x.replaceAll("^0+", "");
//        x = x.replaceAll("0+$", "");
        if (x.startsWith(".") || x.startsWith(",")) {
            x = "0" + x;
        }

        Pair<String, String> pattern = StringUtil.findPattern(x);

        String base = pattern.getK();
        String cycle = pattern.getV();


        int commaPlace = StringUtil.getCurrentCommaPlace(x);
        int exponent = base.length() - commaPlace;

        Fraction baseFraction;
        Fraction cycleFraction;

        try {
            baseFraction = new Fraction(StringUtil.moveComma(base, -exponent), "1");
            cycleFraction = new Fraction(new BigInteger(cycle), new BigInteger(StringUtil.moveComma("1.0", cycle.length())).subtract(BigInteger.ONE)).multiply(tenExp(-exponent));
        } catch (NumberFormatException e) {
            return fromEndingDecimal(x, true);
        }

        return baseFraction.add(cycleFraction);
    }


    private static Fraction tenExp (int exponent) {
        if (exponent < 0) {
            return new Fraction(1, (int) Math.pow(10, -exponent));
        } else if (exponent > 0) {
            return new Fraction((int) Math.pow(10, exponent), 1);
        } else {
            return new Fraction(1, 1);
        }
    }

    public static void main(String[] args) {
        var a = new Fraction(1000, 1);
        var b = a.root(3);
        System.out.println(b.approx);
    }
}
