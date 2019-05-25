package math.fraction;

import misc.StringUtil;

import java.math.BigInteger;

public class ApproxFraction extends Fraction {
    public ApproxFraction (BigInteger numerator, BigInteger denominator) {
        super(numerator, denominator);
    }

    public ApproxFraction (String numerator, String denominator) {
        super(numerator, denominator);
    }

    public ApproxFraction (int numerator, int denominator) {
        super(numerator, denominator);
    }

    private ApproxFraction (Fraction fraction) {
        this(fraction.getNumerator(), fraction.getDenominator());
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
        return new ApproxFraction(super.inverse());
    }

    @Override
    public String toString () {
        return super.toDecimal().toString();
    }

    @Override
    public Fraction add (Fraction other) {
        Fraction a = this.expand(other.getDenominator());
        Fraction b = other.expand(this.getDenominator());

        return new ApproxFraction(a.getNumerator().add(b.getNumerator()), a.getDenominator());
    }

    @Override
    protected ApproxFraction compact () {
        return new ApproxFraction(super.compact());
    }

    @Override
    public Fraction multiply (Fraction other) {
        return new ApproxFraction(getNumerator().multiply(other.getNumerator()), getDenominator().multiply(other.getDenominator())).compact();
    }

    @Override
    public Fraction negate () {
        return new ApproxFraction(super.negate());
    }
}
