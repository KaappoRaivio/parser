package lexer.token;

public class NumberToken extends FoundToken {
    private String value;

    public String getValue() {
        return value;
    }



    @Override
    public String toString() {
        return super.toString() + "{" + value + "}";
    }

    public NumberToken(Token tokenType, String value) {
        super(tokenType);

        this.value = value;
    }
}
