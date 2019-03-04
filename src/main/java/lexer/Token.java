package lexer;

import java.util.regex.Pattern;

public enum Token {
    ADD ("\\+"),
    SUBTRACT ("\\-"),
    MULTIPLY ("\\*"),
    DIVIDE ("\\/"),
    LPAREN ("\\("),
    RPAREN ("\\)"),
    END ("$"),

    NUMBER ("(-)?([0123456789])+([.,]([0123456789])+)?(.*?)");

    private Pattern regex;

    private Float value;

    Token (String regex) {
        this.regex = Pattern.compile("^" + regex);
    }

    public boolean matches (String another) {
        return regex.matcher(another).matches();
    }

    public float getValue () {
        if (this == NUMBER) {
            return value;
        } else {
            throw new RuntimeException("Token " + this + " doesn't have value!");
        }
    }

    public static Token getToken (String pattern) {
        Token token1 = null;

        for (Token token : Token.values()) {
            if (token.matches(pattern)) {
                token1 = token;
            }
        }

        if (token1 == NUMBER) {
            token1.value =
        }

        throw new RuntimeException("Unknown token " + pattern + "!");
    }
}
