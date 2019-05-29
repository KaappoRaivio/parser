package math.utils;

import expression.SymbolTable;
import lexer.token.SymbolToken;
import math.fraction.approxfraction.ApproxFraction;
import math.fraction.fraction.Fraction;

import java.util.Map;

public class TrigonometryUtils {
    public static final Map<ApproxFraction, ApproxFraction> sinMap = Map.ofEntries(
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")), new Fraction(0, 1)),
    )
}
