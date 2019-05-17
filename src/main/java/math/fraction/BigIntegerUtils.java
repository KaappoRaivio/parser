package math.fraction;

import java.math.BigInteger;

public class BigIntegerUtils {
    public static Fractionable power (Fractionable base, BigInteger exponent) {
        Fraction result = new Fraction(1, 1);

        while (exponent.compareTo(BigInteger.ZERO) > 0) {
            result = result.multiply(base.fractionValue());
        }

        return result;
    }

//    public static Fractionable power (Fractionable base, Fractionable exponent) {
//        return power(base, exponent.fractionValue().getNumerator()).fractionValue().root()
//    }
}
