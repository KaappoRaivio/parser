package math.utils;

import expression.SymbolTable;
import lexer.token.SymbolToken;
import math.fraction.approxfraction.ApproxFraction;
import math.fraction.fraction.Fraction;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class TrigonometryUtils {
    public static final Map<Fraction, Fraction> sinMapRadians;
    public static final Map<Fraction, Fraction> sinMapDegrees;
    public static final Map<Fraction, Fraction> cosMapRadians;
    public static final Map<Fraction, Fraction> cosMapDegrees;

    static {
        sinMapRadians = new HashMap<>();

        sinMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().divide(6),              new Fraction(1, 2));
        sinMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().divide(4),              new Fraction(2, 1).root(BigInteger.valueOf(2)).divide(2));
        sinMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().divide(3),              new Fraction(3, 1).root(BigInteger.valueOf(2)).divide(2));
        sinMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().divide(2),              new Fraction(1, 1));

        sinMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(2).divide(3),  new Fraction(3, 1).root(BigInteger.valueOf(2)).divide(2));
        sinMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(3).divide(4),  new Fraction(2, 1).root(BigInteger.valueOf(2)).divide(2));
        sinMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(5).divide(6),  new Fraction(1, 2));
        sinMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue(),                        new Fraction(0, 1));

        sinMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(7).divide(6),  new Fraction(-1, 2));
        sinMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(5).divide(4),  new Fraction(2, 1).root(BigInteger.valueOf(2)).divide(-2));
        sinMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(4).divide(3),  new Fraction(3, 1).root(BigInteger.valueOf(2)).divide(-2));
        sinMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(3).divide(2),  new Fraction(-1, 1));

        sinMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(5).divide(3),  new Fraction(3, 1).root(BigInteger.valueOf(2)).divide(-2));
        sinMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(7).divide(4),  new Fraction(2, 1).root(BigInteger.valueOf(2)).divide(-2));
        sinMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(11).divide(6), new Fraction(-1, 2));
        sinMapRadians.put(new Fraction(0, 1),  new Fraction(0, 1));
        sinMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(2),            new Fraction(0, 1));

        sinMapDegrees = new HashMap<>();

        sinMapDegrees.put(new Fraction(30, 1),                                                             new Fraction(1, 2));
        sinMapDegrees.put(new Fraction(45, 1),                                                             new Fraction(2, 1).root(BigInteger.valueOf(2)).divide(2));
        sinMapDegrees.put(new Fraction(60, 1),                                                             new Fraction(3, 1).root(BigInteger.valueOf(2)).divide(2));
        sinMapDegrees.put(new Fraction(90, 1),                                                             new Fraction(1, 1));

        sinMapDegrees.put(new Fraction(120, 1),                                                            new Fraction(3, 1).root(BigInteger.valueOf(2)).divide(2));
        sinMapDegrees.put(new Fraction(135, 1),                                                            new Fraction(2, 1).root(BigInteger.valueOf(2)).divide(2));
        sinMapDegrees.put(new Fraction(150, 1),                                                            new Fraction(1, 2));
        sinMapDegrees.put(new Fraction(180, 1),                                                            new Fraction(0, 1));

        sinMapDegrees.put(new Fraction(210, 1),                                                            new Fraction(-1, 2));
        sinMapDegrees.put(new Fraction(225, 1),                                                            new Fraction(2, 1).root(BigInteger.valueOf(2)).divide(-2));
        sinMapDegrees.put(new Fraction(240, 1),                                                            new Fraction(3, 1).root(BigInteger.valueOf(2)).divide(-2));
        sinMapDegrees.put(new Fraction(270, 1),                                                            new Fraction(-1, 1));

        sinMapDegrees.put(new Fraction(300, 1),                                                            new Fraction(3, 1).root(BigInteger.valueOf(2)).divide(-2));
        sinMapDegrees.put(new Fraction(315, 1),                                                            new Fraction(2, 1).root(BigInteger.valueOf(2)).divide(-2));
        sinMapDegrees.put(new Fraction(330, 1),                                                            new Fraction(-1, 2));
        sinMapDegrees.put(new Fraction(0, 1),                                                              new Fraction(0, 1));
        sinMapDegrees.put(new Fraction(360, 1),                                                            new Fraction(0, 1));

        cosMapRadians = new HashMap<>();

        cosMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().divide(6),               new Fraction(3, 1).root(BigInteger.valueOf(2)).divide(2));
        cosMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().divide(3),               new Fraction(1, 2));
        cosMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().divide(4),               new Fraction(2, 1).root(BigInteger.valueOf(2)).divide(2));
        cosMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().divide(2),               new Fraction(0, 1));

        cosMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(2).divide(3),   new Fraction(-1, 2));
        cosMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(3).divide(4),   new Fraction(2, 1).root(BigInteger.valueOf(2)).divide(-2));
        cosMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(5).divide(6),   new Fraction(3, 1).root(BigInteger.valueOf(2)).divide(-2));
        cosMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue(),                         new Fraction(-1, 1));

        cosMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(7).divide(6),   new Fraction(3, 1).root(BigInteger.valueOf(2)).divide(-2));
        cosMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(5).divide(4),   new Fraction(2, 1).root(BigInteger.valueOf(2)).divide(-2));
        cosMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(4).divide(3),   new Fraction(-1, 2));
        cosMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(3).divide(2),   new Fraction(0, 1));

        cosMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(5).divide(3),   new Fraction(1, 2));
        cosMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(7).divide(4),   new Fraction(2, 1).root(BigInteger.valueOf(2)).divide(2));
        cosMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(11).divide(6),  new Fraction(3, 1).root(BigInteger.valueOf(2)).divide(2));
        cosMapRadians.put(new Fraction(0, 1),                                                               new Fraction(1, 1));
        cosMapRadians.put(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(2),             new Fraction(1, 1));

        cosMapDegrees = new HashMap<>();

        cosMapDegrees.put(new Fraction(0, 1),                                                               new Fraction(1, 1));
        cosMapDegrees.put(new Fraction(30, 1),                                                              new Fraction(3, 1).root(BigInteger.valueOf(2)).divide(2));
        cosMapDegrees.put(new Fraction(45, 1),                                                              new Fraction(2, 1).root(BigInteger.valueOf(2)).divide(2));
        cosMapDegrees.put(new Fraction(60, 1),                                                              new Fraction(1, 2));

        cosMapDegrees.put(new Fraction(90, 1),                                                              new Fraction(0, 1));
        cosMapDegrees.put(new Fraction(120, 1),                                                             new Fraction(-1, 2));
        cosMapDegrees.put(new Fraction(135, 1),                                                             new Fraction(2, 1).root(BigInteger.valueOf(2)).divide(-2));
        cosMapDegrees.put(new Fraction(150, 1),                                                             new Fraction(3, 1).root(BigInteger.valueOf(2)).divide(-2));

        cosMapDegrees.put(new Fraction(180, 1),                                                             new Fraction(-1, 1));
        cosMapDegrees.put(new Fraction(210, 1),                                                             new Fraction(3, 1).root(BigInteger.valueOf(2)).divide(-2));
        cosMapDegrees.put(new Fraction(225, 1),                                                             new Fraction(2, 1).root(BigInteger.valueOf(2)).divide(-2));
        cosMapDegrees.put(new Fraction(240, 1),                                                             new Fraction(-1, 2));

        cosMapDegrees.put(new Fraction(270, 1),                                                             new Fraction(0, 1));
        cosMapDegrees.put(new Fraction(300, 1),                                                             new Fraction(1, 2));
        cosMapDegrees.put(new Fraction(315, 1),                                                             new Fraction(2, 1).root(BigInteger.valueOf(2)).divide(2));
        cosMapDegrees.put(new Fraction(330, 1),                                                             new Fraction(3, 1).root(BigInteger.valueOf(2)).divide(2));

        cosMapDegrees.put(new Fraction(360, 1),                                                             new Fraction(1, 1));
    }
}
