package parser;

import math.fraction.Fraction;
import math.fraction.Fractionable;

import java.util.regex.Pattern;

public class MyValueProvider<T extends Fractionable> implements ValueProvider<T> {


    @Override
    public T valueOf (String string) {
        return (T) T.valueOf(string);
    }
}
