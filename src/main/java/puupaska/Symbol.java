package puupaska;

public class Symbol implements Payload {
    private int value;

    public int getValue() {
        return value;
    }

    public Symbol(int value) {
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
}
