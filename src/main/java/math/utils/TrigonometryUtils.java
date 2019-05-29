package math.utils;

import expression.SymbolTable;
import lexer.token.SymbolToken;
import math.fraction.approxfraction.ApproxFraction;
import math.fraction.fraction.Fraction;

import java.math.BigInteger;
import java.util.Map;

public class TrigonometryUtils {
    public static final Map<Fraction, Fraction> sinMapRadians = Map.ofEntries(
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().divide(6),              new Fraction(1, 2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().divide(4),              new Fraction(2, 1).root(BigInteger.TWO).divide(2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().divide(3),              new Fraction(3, 1).root(BigInteger.TWO).divide(2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().divide(2),              new Fraction(1, 1)),

            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(2).divide(3),  new Fraction(3, 1).root(BigInteger.TWO).divide(2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(3).divide(4),  new Fraction(2, 1).root(BigInteger.TWO).divide(2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(5).divide(6),  new Fraction(1, 2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue(),                        new Fraction(0, 1)),

            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(7).divide(6),  new Fraction(-1, 2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(5).divide(4),  new Fraction(2, 1).root(BigInteger.TWO).divide(-2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(4).divide(3),  new Fraction(3, 1).root(BigInteger.TWO).divide(-2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(3).divide(2),  new Fraction(-1, 1)),

            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(5).divide(3),  new Fraction(3, 1).root(BigInteger.TWO).divide(-2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(7).divide(4),  new Fraction(2, 1).root(BigInteger.TWO).divide(-2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(11).divide(6), new Fraction(-1, 2)),
            Map.entry(new Fraction(0, 1),  new Fraction(0, 1))
    );

    public static final Map<Fraction, Fraction> sinMapDegrees = Map.ofEntries(
            Map.entry(new Fraction(30, 1),                                                             new Fraction(1, 2)),
            Map.entry(new Fraction(45, 1),                                                             new Fraction(2, 1).root(BigInteger.TWO).divide(2)),
            Map.entry(new Fraction(60, 1),                                                             new Fraction(3, 1).root(BigInteger.TWO).divide(2)),
            Map.entry(new Fraction(90, 1),                                                             new Fraction(1, 1)),

            Map.entry(new Fraction(120, 1),                                                            new Fraction(3, 1).root(BigInteger.TWO).divide(2)),
            Map.entry(new Fraction(135, 1),                                                            new Fraction(2, 1).root(BigInteger.TWO).divide(2)),
            Map.entry(new Fraction(150, 1),                                                            new Fraction(1, 2)),
            Map.entry(new Fraction(180, 1),                                                            new Fraction(0, 1)),

            Map.entry(new Fraction(210, 1),                                                            new Fraction(-1, 2)),
            Map.entry(new Fraction(225, 1),                                                            new Fraction(2, 1).root(BigInteger.TWO).divide(-2)),
            Map.entry(new Fraction(240, 1),                                                            new Fraction(3, 1).root(BigInteger.TWO).divide(-2)),
            Map.entry(new Fraction(270, 1),                                                            new Fraction(-1, 1)),

            Map.entry(new Fraction(300, 1),                                                            new Fraction(3, 1).root(BigInteger.TWO).divide(-2)),
            Map.entry(new Fraction(315, 1),                                                            new Fraction(2, 1).root(BigInteger.TWO).divide(-2)),
            Map.entry(new Fraction(330, 1),                                                            new Fraction(-1, 2)),
            Map.entry(new Fraction(0, 1),                                                              new Fraction(0, 1))
    );

    public static final Map<Fraction, Fraction> cosMapRadians = Map.ofEntries(
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().divide(6),               new Fraction(3, 1).root(BigInteger.TWO).divide(2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().divide(3),               new Fraction(1, 2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().divide(4),               new Fraction(2, 1).root(BigInteger.TWO).divide(2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().divide(2),               new Fraction(0, 1)),

            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(2).divide(3),   new Fraction(-1, 2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(3).divide(4),   new Fraction(2, 1).root(BigInteger.TWO).divide(-2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(5).divide(6),   new Fraction(3, 1).root(BigInteger.TWO).divide(-2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue(),                         new Fraction(-1, 1)),

            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(7).divide(6),   new Fraction(3, 1).root(BigInteger.TWO).divide(-2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(5).divide(4),   new Fraction(2, 1).root(BigInteger.TWO).divide(-2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(4).divide(3),   new Fraction(-1, 2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(3).divide(2),   new Fraction(0, 1)),

            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(5).divide(3),   new Fraction(1, 2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(7).divide(4),   new Fraction(2, 1).root(BigInteger.TWO).divide(2)),
            Map.entry(SymbolTable.defaultTable.getValue(new SymbolToken("p")).fractionValue().multiply(11).divide(6),  new Fraction(3, 1).root(BigInteger.TWO).divide(2)),
            Map.entry(new Fraction(0, 1),                                                               new Fraction(1, 1))
    );

    public static final Map<Fraction, Fraction> cosMapDegrees = Map.ofEntries(
            Map.entry(new Fraction(30, 1),                                                              new Fraction(3, 1).root(BigInteger.TWO).divide(2)),
            Map.entry(new Fraction(45, 1),                                                              new Fraction(2, 1).root(BigInteger.TWO).divide(2)),
            Map.entry(new Fraction(60, 1),                                                              new Fraction(1, 2)),
            Map.entry(new Fraction(90, 1),                                                              new Fraction(0, 1)),

            Map.entry(new Fraction(120, 1),                                                             new Fraction(-1, 2)),
            Map.entry(new Fraction(135, 1),                                                             new Fraction(2, 1).root(BigInteger.TWO).divide(-2)),
            Map.entry(new Fraction(150, 1),                                                             new Fraction(3, 1).root(BigInteger.TWO).divide(-2)),
            Map.entry(new Fraction(180, 1),                                                             new Fraction(-1, 1)),

            Map.entry(new Fraction(210, 1),                                                             new Fraction(3, 1).root(BigInteger.TWO).divide(-2)),
            Map.entry(new Fraction(225, 1),                                                             new Fraction(2, 1).root(BigInteger.TWO).divide(-2)),
            Map.entry(new Fraction(240, 1),                                                             new Fraction(-1, 2)),
            Map.entry(new Fraction(270, 1),                                                             new Fraction(0, 1)),

            Map.entry(new Fraction(300, 1),                                                             new Fraction(1, 2)),
            Map.entry(new Fraction(315, 1),                                                             new Fraction(2, 1).root(BigInteger.TWO).divide(2)),
            Map.entry(new Fraction(330, 1),                                                             new Fraction(3, 1).root(BigInteger.TWO).divide(2)),
            Map.entry(new Fraction(0, 1),                                                               new Fraction(1, 1))
    );
}
