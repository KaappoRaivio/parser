package parser;

;

import math.fraction.fraction.Fractionable;


public interface NumberParser<T extends Fractionable> {
    T valueOf (String string);
}
