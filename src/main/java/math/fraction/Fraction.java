package math.fraction;

import misc.Pair;
import misc.StringUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Fraction implements Fractionable {
    static final int PRECISION = 16;

    private static final BigInteger BIG = new BigInteger("100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");



    protected String originalRepresentation;
    private BigInteger numerator;
    private BigInteger denominator;

    public Fraction (BigInteger numerator, BigInteger denominator) {
        this(numerator, denominator, true);
    }

    private Fraction (BigInteger numerator, BigInteger denominator, boolean compact) {
        if (denominator.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("Denominator cannot be zero!");
        }


        this.numerator = numerator;
        this.denominator = denominator;

        if (compact) {
            compactInPlace();
        }
    }

    private void compactInPlace () {
        BigInteger gcd = gcd(this.numerator, this.denominator);
        this.numerator = this.numerator.divide(gcd);
        this.denominator = this.denominator.divide(gcd);
    }

    public BigInteger safeIntValue () {
        if (isInteger()) {
            return numerator;
        } else {
            throw new RuntimeException("safeIntValue() isn't safe for " + this);
        }
    }

    public Fraction (String numerator, String denominator) {
        this(new BigInteger(numerator), new BigInteger(denominator));
    }

    public Fraction (int numerator, int denominator) {
        this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
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
    public String toString() {
        if (isInteger()) {
            return numerator.toString();
        } else {
            return "(" + numerator + " / " + denominator + ")";
        }
    }

    public Fraction add (int other) {
        return add(new Fraction(BigInteger.valueOf(other), BigInteger.ONE));
    }

    public Fraction add (Fraction other) {
        if (other instanceof ApproxFraction) {
            return other.add(this);
        }
        Fraction a = this.expand(other.denominator);
        Fraction b = other.expand(this.denominator);


        return new Fraction(a.numerator.add(b.numerator), a.denominator);
    }

    Fraction expand (BigInteger amount) {
        return new Fraction(numerator.multiply(amount), denominator.multiply(amount), false);
    }

    public Fraction subtract (int other) {
        return add(-other);
    }

    protected Fraction compact () {
        BigInteger gcd = gcd(numerator, denominator);
        return reduce(gcd);
    }

    public Fraction subtract (Fraction other) {
        return add(other.negate());
    }

    private Fraction reduce (BigInteger amount) {
        return new Fraction(numerator.divide(amount), denominator.divide(amount));
    }

    public Fraction negate () {
        return new Fraction(numerator.negate(), denominator);
    }

    public Fraction power (Fraction exponent) {
        return power(exponent.numerator).root(exponent.denominator);
    }

    public Fraction root (BigInteger n) {
        String a = RootCalculus.nthRoot(n, toDecimal()).toString();

        if (a.contains(".")) {
            return fromDecimal(a.substring(0, a.length() - 1), true);
        } else {
            return fromDecimal(a, false);
        }
    }

    public Fraction multiply (int other) {
        return multiply(new Fraction(other, 1));
    }

    public Fraction multiply (Fraction other) {
        if (other instanceof ApproxFraction) {
            return other.add(this);
        }
        return new Fraction(numerator.multiply(other.numerator), denominator.multiply(other.denominator)).compact();
    }

    public Fraction divide (Fraction other) {
//        return new Fraction(numerator.multiply(other.denominator), denominator.multiply(other.numerator)).compact();
        return multiply(other.inverse());
    }

    public Fraction divide (int other) {
        return divide(new Fraction(other, 1));
    }

    private Fraction power (BigInteger exponent) {
        int integerExponent = exponent.intValueExact();

        if (integerExponent < 0) {
            return new Fraction(numerator.pow(-integerExponent), denominator.pow(-integerExponent)).inverse();
        } else {
            return new Fraction(numerator.pow(integerExponent), denominator.pow(integerExponent));
        }
    }

    public BigDecimal toDecimal () {
        BigDecimal tempResult = new BigDecimal(numerator).divide(new BigDecimal(denominator), MathContext.DECIMAL128);
        String string = tempResult.toString();

        return new BigDecimal(string);
    }

    public static Fraction fromDecimal (String representation, boolean endless) {
        if (endless) {
            return fromEndlessDecimal(representation);
        } else {
            return fromEndingDecimal(representation);
        }
    }

    private static Fraction fromEndlessDecimal (String representation) {
        if (!representation.contains(".") && !representation.contains(",")) {
//            throw new NumberFormatException("No comma or decimal point found in number " + representation + "!");
            return fromEndingDecimal(representation);
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

            // non-repeating, endless
            return ApproxFraction.fromEndingDecimal(sliced);
        }
    }

    private static Fraction fromEndingDecimal(String x) {
        String original = x;


        int commaPlace = StringUtil.getCurrentCommaPlace(x);
        x = x.replaceAll("[.,]", "");

        Fraction fraction = new Fraction(x, StringUtil.moveComma("1.0", x.length() - commaPlace));
        fraction.originalRepresentation = original;
        return fraction;
    }

    private static Fraction fromRepeatingDecimal(String x) {
        String original = x;
        x = x.replaceAll("^0+", "");

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
            return ApproxFraction.fromEndingDecimal(x);
//            throw new RuntimeException("Fraction.java only supports exact values, and '" + x + "' is not such.");
        }

        Fraction fraction = baseFraction.add(cycleFraction);
        fraction.originalRepresentation = original;
        return fraction;
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

    public Fraction toEndless () {
        return Fraction.fromDecimal(getOriginalRepresentation(), true);
    }

    public String getOriginalRepresentation () {
        if (originalRepresentation == null) {
            return toDecimal().toString();
        } else {
            return originalRepresentation;
        }
    }

    public BigInteger getNumerator() {
        return numerator;
    }

    public BigInteger getDenominator() {
        return denominator;
    }

    public Fraction inverse () {
        return new Fraction(denominator, numerator);
    }

    public Fraction abs () {
        return new Fraction(numerator.abs(), denominator.abs());
    }

    public static Fraction valueOf (int integer, boolean endless) {
        return valueOf(String.valueOf(integer), endless);
    }

    public static Fraction valueOf (String string, boolean endless) {
        return fromDecimal(string, endless);
    }

    public static Fraction valueOf (double integer, boolean endless) {
        return valueOf(String.valueOf(integer), endless);
    }

    public static Fraction valueOf (float integer, boolean endless) {
        return valueOf(String.valueOf(integer), endless);
    }


    public boolean isInteger () {
        return denominator.equals(BigInteger.ONE);
    }

    public boolean isNegative () {
        return toDecimal().compareTo(BigDecimal.ZERO) < 0;
    }

    public Fraction factorial () {
        if (!isInteger() || isNegative()) {
            throw new RuntimeException("Factorial is only defined for positive integer fractions, not \"" + toString() + "\"!");
        }

        Fraction result = new Fraction(numerator, denominator);
        for (Fraction i = this.subtract(1); i.getNumerator().compareTo(BigInteger.ZERO) > 0 ; i = i.subtract(1)) {
            result = result.multiply(i);
        }

        return result;
    }

    @Override
    public Fraction fractionValue() {
        return this;
    }



    @Override
    public boolean isOperator () {
        return false;
    }

    @Override
    public boolean isFraction () {
        return true;
    }

    private static BigInteger lcm (BigInteger num, BigInteger den) {
        return Stream.of(num, den).reduce(new BigInteger("1"), (x, y) -> x.multiply(y.divide(gcd(x, y))));
    }

    private static BigInteger gcd (BigInteger num, BigInteger den) {
        return num.gcd(den);
    }


}
