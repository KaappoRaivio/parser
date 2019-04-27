package puupaska;

import math.fraction.Fractionatable;

public class Symbol implements Payload {
    private String value;

    public String getValue() {
        return value;
    }

    public Symbol(String value) {
        this.value = value;
    }

    @Override
    public boolean isOperator() {
        return false;
    }

    @Override
    public boolean isSymbol() {
        return true;
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "value=" + value +
                '}';
    }

    public static Symbol valueOf (String string) {
        return new Symbol(string);
    }
}
