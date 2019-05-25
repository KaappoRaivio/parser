package expression;

import lexer.token.SymbolToken;

import math.fraction.ApproxFraction;
import math.fraction.Fraction;
import math.fraction.Fractionable;

import java.util.Map;
import java.util.Optional;

public class SymbolTable {
    public static final SymbolTable defaultTable = new SymbolTable(Map.ofEntries(
            Map.entry(new SymbolToken("%"),  new Fraction(1, 100)),
            Map.entry(new SymbolToken("π"),  new ApproxFraction("3141592653589793", "1000000000000000")),
            Map.entry(new SymbolToken("pi"), new ApproxFraction("3141592653589793", "1000000000000000")),
            Map.entry(new SymbolToken("e"),  new ApproxFraction("2718281828459045", "1000000000000000"))
            ));

    private Map<SymbolToken, Fractionable> symbolTable;

    public SymbolTable (Map<SymbolToken, Fractionable> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public Fractionable getValue (SymbolToken token) {
        return Optional.of(symbolTable.get(token)).orElseThrow(() -> new RuntimeException("Unknown variable '" + token.getValue() + "'!"));
    }

}
