package lexer.token;


import java.util.Objects;

public class FoundToken {
    private Token tokenType;

    public FoundToken (Token tokenType) {
        this.tokenType = tokenType;
    }

    public Token getTokenType() {
        return tokenType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass() == Token.class) return tokenType == o;
        if (getClass() != o.getClass()) return false;
        if (this == o) return true;

        FoundToken that = (FoundToken) o;
        return tokenType == that.tokenType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenType);
    }

    @Override
    public String toString() {
        return tokenType.toString();
    }
}
