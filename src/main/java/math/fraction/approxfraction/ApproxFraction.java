package math.fraction.approxfraction;

import expression.Expression;
import expression.SymbolTable;
import lexer.token.SymbolToken;
import math.fraction.fraction.Fraction;
import misc.BigFunctions;
import misc.StringUtil;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ApproxFraction extends Fraction {
    private BigDecimal actualValue;

    public ApproxFraction (BigInteger numerator, BigInteger denominator) {
        super(numerator, denominator);
        actualValue = super.toDecimal();
    }

    public ApproxFraction (BigDecimal value) {
        super(1, 1);
        actualValue = value;
    }

    public ApproxFraction (String numerator, String denominator) {
        this(new BigInteger(numerator), new BigInteger(denominator));
    }

    private ApproxFraction (Fraction fraction) {
        this(fraction.toDecimal());
    }

    protected static Fraction fromEndingDecimal (String s) {
        String original = s;


        int commaPlace = StringUtil.getCurrentCommaPlace(s);
        s = s.replaceAll("[.,]", "");

        Fraction fraction = new ApproxFraction(s, StringUtil.moveComma("1.0", s.length() - commaPlace));
        fraction.originalRepresentation = original;
        return fraction;
    }

    @Override
    public Fraction inverse () {
        return new ApproxFraction(BigDecimal.ONE.divide(actualValue, Expression.CONTEXT));
    }

    @Override
    public String toString () {
        return actualValue.toString();
    }

    @Override
    public Fraction add (Fraction other) {
        return new ApproxFraction(actualValue.add(other.toDecimal()));
    }

    @Override
    public Fraction factorial () {
        throw new RuntimeException("Fractions are not defined for approximates such as " + toString() +"!");
    }

    @Override
    public BigInteger getNumerator () {
        throw new RuntimeException("The numerator is not exact!");
    }

    @Override
    public BigInteger getDenominator () {
        throw new RuntimeException("The denominator is not exact!");
    }

    @Override
    protected ApproxFraction compact () {
        return this;
    }

    @Override
    public Fraction multiply (Fraction other) {
        return new ApproxFraction(actualValue.multiply(other.toDecimal()));
    }

    @Override
    public Fraction negate () {
        return new ApproxFraction(actualValue.negate());
    }

    @Override
    public Fraction power (Fraction exponent) {
        return new ApproxFraction(BigFunctions.exp(BigFunctions.ln(actualValue, Fraction.PRECISION).multiply(exponent.toDecimal(), Expression.CONTEXT), Fraction.PRECISION));
    }

    @Override
    public BigDecimal toDecimal () {
        return actualValue;
    }

    @Override
    public boolean isNegative () {
        return actualValue.signum() == -1;
    }

    @Override
    public boolean equals (Object o) {
        if (o == null) {
            return false;
        } else if (o instanceof Fraction) {
            return actualValue.equals(((ApproxFraction) o).toDecimal());
        } else {
            return false;
        }
//        return super.equals(o);
//        throw new RuntimeException("Not implemented yet!");
    }

    public Fraction inversePower (Fraction mantissa) {
        return new ApproxFraction(BigFunctions.exp(BigFunctions.ln(mantissa.toDecimal(), Fraction.PRECISION).multiply(actualValue, Expression.CONTEXT), Fraction.PRECISION));
    }

    private static ApproxFraction getPi () {
        if (USE_DEGREES) {
            return new ApproxFraction(BigDecimal.valueOf(180));
        } else {
            return (ApproxFraction) SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue();
        }
    }

    private static ApproxFraction getMinusPi () {
        return new ApproxFraction(BigDecimal.valueOf(0));
    }

    private static ApproxFraction getHalfPi () {
        if (USE_DEGREES) {
            return new ApproxFraction(BigDecimal.valueOf(90));
        } else {
            return (ApproxFraction) SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().divide(2);
        }
    }

    @Override
    public Fraction sin () {
        if (equals(getPi()) || equals(getMinusPi())) {
            return new Fraction(0, 1);
        } else if (equals(getHalfPi())) {
            return new Fraction(1, 1);
        } else if (equals(getZero())) {
            return new Fraction(-1 , 1);
        } else {
            return super.sin();
        }
    }

    @Override
    public Fraction cos () {
        if (equals(getHalfPi()) || equals(getZero())) {
            return new Fraction(0, 1);
        } else if (equals(getPi())) {
            return new Fraction(-1, 1);
        } else if (equals(getZero())) {
            return new Fraction(1 , 1);
        } else {
            return super.cos();
        }
    }

    @Override
    public Fraction tan () {
        return super.tan();
    }

    private static ApproxFraction getZero () {
        if (USE_DEGREES) {
            return new ApproxFraction(BigDecimal.valueOf(-90));
        } else {
            return (ApproxFraction) SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().divide(2).negate();
        }
    }
}
