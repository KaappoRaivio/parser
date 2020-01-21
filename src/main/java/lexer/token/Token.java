package lexer.token;

import java.util.Objects;
import java.util.regex.Pattern;

public class Token {
    private final Pattern regex;
    private final String repr;
    private final int precedence;
    private final TokenType tokenType;

    public Token(final String regex, final String repr, final int precedence) {
        this(regex, repr, precedence, TokenType.NORMAL);
    }

    public Token (final String regex, final String repr, final int precedence, TokenType tokenType) {
        this.regex = Pattern.compile("^" + regex);
        this.repr = repr;
        this.precedence = precedence;
        this.tokenType = tokenType;
    }

    public boolean matches (String another) {
        return regex.matcher(another).lookingAt();
    }

    public Pattern getRemoverRegex () {
        return Pattern.compile(regex.pattern() + "(.*?)");
    }

    @Override
    public String toString () {
        return repr;
    }

    public Pattern getRegex () {
        return regex;
    }

    public int getPrecedence () {
        return precedence;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return precedence == token.precedence &&
                Objects.equals(regex, token.regex) &&
                Objects.equals(repr, token.repr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regex, repr, precedence);
    }

    public TokenType getTokenType() {
        return tokenType;
    }
    public static class Default {
        public static final Token END = new Token ("$", "End", 1);
        public static final Token NUMBER = new Token("([0123456789])+([.,]([0123456789])+)?", "Number", 4, TokenType.NUMBER);
        public static final Token SYMBOL = new Token("(.*?)", "Symbol", -10, TokenType.SYMBOL);
        public static final Token ELLIPSIS = new Token ("\\.\\.\\.", "Ellipsis", -2);

        public static final Token SIN = new Token ("sin", "Sin", 0);
        public static final Token COS = new Token ("cos", "Cos", 0);
        public static final Token TAN = new Token ("tan", "Tan", 0);
        public static final Token LOG10 = new Token ("log10", "Log10", 0);
        public static final Token LOG2 = new Token ("log2", "Log2", 0);
        public static final Token LN = new Token ("ln", "Ln", 0);

        public static final Token PLUS = new Token ("\\+", "Plus", 1);
        public static final Token MINUS = new Token ("\\-", "Minus", 1);
        public static final Token ASTERISK = new Token ("\\*", "Asterisk", 1);
        public static final Token SLASH = new Token ("\\/", "Slash", 1);
        public static final Token LEFT_PARENTHESIS = new Token ("\\(", "Left parenthesis", 1);
        public static final Token RIGHT_PARENTHESIS = new Token ("\\)", "Right parenthesis", 1);
        public static final Token PIPE = new Token ("\\|", "Pipe", 1);
        public static final Token SQUARE_ROOT = new Token ("(\\√|sqrt)", "Square root", 1);
        public static final Token NTH_ROOT = new Token ("root", "nth root", 1);
        public static final Token EXCLAMATION = new Token ("\\!", "Exclamation", 1);
        public static final Token POWER = new Token ("(\\*\\*|\\^)", "Power", 2);
    }

}
