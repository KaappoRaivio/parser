package lexer.token;

public class NumberToken extends FoundToken {
    private Number value;

    public Number getValue() {
        return value;
    }

    @Override
    public String toString() {
        return super.toString() + "{" + value + "}";
    }

    public NumberToken(Token tokenType, Number value) {
        super(tokenType);

        this.value = value;
    }
}
