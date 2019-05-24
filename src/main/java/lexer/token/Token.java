package lexer.token;

import lexer.UnknownTokenException;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public enum Token {
    ADD ("\\+", "Plus", 1),
    SUBTRACT ("\\-", "Minus", 1),
    MULTIPLY ("\\*", "Asterisk", 1),
    DIVIDE ("\\/", "Slash", 1),
    LPAREN ("\\(", "Left parenthesis", 1),
    RPAREN ("\\)", "Right parenthesis", 1),
    END ("$", "End", 1),
    NUMBER ("([0123456789])+([.,]([0123456789])+)?", "Number", 2),
    ABS ("\\|", "Pipe", 1),
    SQRT ("(\\√|sqrt)", "Square root", 1),
    ROOT ("root", "nth root", 1),
    EXCLAMATION ("\\!", "Factorial", 1),
    POWER ("(\\*\\*|\\^)", "Power", 2),
    SYMBOL ("([a-z])", "Variable", 0),
    ELLIPSIS ("(\\.\\.\\.)?", "Ellipsis", 0);
//    FACTORIAL ("\\!", "Factorial", 0);


    private final Pattern regex;
    private final String repr;
    private final int precedence;

    Token(final String regex, final String repr, final int precedence) {
        this.regex = Pattern.compile("^" + regex);
        this.repr = repr;
        this.precedence = precedence;
    }

    public boolean matches (String another) {
        return regex.matcher(another).lookingAt();
    }

    public static Token getToken (String pattern) {
        Token token1 = null;

        boolean matched = false;

        for (Token token : sortedValues()) {
            if (token.matches(pattern)) {
                token1 = token;
                matched = true;
                break;

            }
        }
        if (!matched) {
            throw new UnknownTokenException("Unknown token " + pattern + "!");
        }




        return token1;
    }

    public Pattern getRemoverRegex () {
        return Pattern.compile(regex.pattern() + "(.*?)");
    }

    @Override
    public String toString() {
        return repr;
    }

    public static List<Token> sortedValues () {
        return Arrays.stream(Token.values()).sorted((item1, item2) -> item2.precedence - item1.precedence).collect(Collectors.toList());
    }

    public Pattern getRegex() {
        return regex;
    }
}
