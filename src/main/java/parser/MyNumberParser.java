package parser;

import math.fraction.fraction.Fractionable;

public class MyNumberParser<T extends Fractionable> implements NumberParser<T> {
    @Override
    public T valueOf (String string) {
        return (T) T.valueOf(string);
    }
}
