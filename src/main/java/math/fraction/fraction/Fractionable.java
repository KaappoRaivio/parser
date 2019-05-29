package math.fraction.fraction;

import expression.Payload;

import java.util.regex.Pattern;

public interface Fractionable extends Payload {
    Pattern pattern = Pattern.compile("(.*?)\\.\\.\\.$");

    Fraction fractionValue ();

    static Fractionable valueOf (String representation) {
        representation = representation.replaceAll("[.,]", ".");
        boolean endless = pattern.matcher(representation).matches();
        return Fraction.fromDecimal(representation, endless);
    }
}
