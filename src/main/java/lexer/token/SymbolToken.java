package lexer.token;

import lexer.token.FoundToken;
import lexer.token.Token;

public class SymbolToken extends FoundToken {

    private String value;

    public String getValue() {
        return value;
    }



    @Override
    public String toString() {
        return super.toString() + "{" + value + "}";
    }

    public SymbolToken (Token tokenType, String value) {
        super(tokenType);

        this.value = value;
    }
}

