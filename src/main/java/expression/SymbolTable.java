package expression;

import lexer.token.SymbolToken;

import math.fraction.ApproxFraction;
import math.fraction.Fraction;
import math.fraction.Fractionable;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public class SymbolTable {
    public static final SymbolTable defaultTable = new SymbolTable(Map.ofEntries(
            Map.entry(new SymbolToken("%"),  new Fraction(1, 100)),
            Map.entry(new SymbolToken("π"),  new ApproxFraction(new BigDecimal("3.14159 26535 89793 23846 26433 83279 50288".replace(" ", "")))),
            Map.entry(new SymbolToken("p"),  new ApproxFraction(new BigDecimal("3.14159 26535 89793 23846 26433 83279 50288".replace(" ", "")))),
            Map.entry(new SymbolToken("e"),  new ApproxFraction(new BigDecimal("2.71828 18284 59045 23536 02874 71352 66249".replace(" ", ""))))
            ));

    private Map<SymbolToken, Fractionable> symbolTable;

    public SymbolTable (Map<SymbolToken, Fractionable> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public Fractionable getValue (SymbolToken token) {
        return Optional.ofNullable(symbolTable.get(token)).orElseThrow(() -> new RuntimeException("Unknown variable '" + token.getValue() + "'!"));
    }

}
