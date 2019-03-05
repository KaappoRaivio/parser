package lexer;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public enum Token {
    ADD ("\\+", "PLUS", 1),
    SUBTRACT ("\\-", "MINUS", 1),
    MULTIPLY ("\\*", "ASTERISK", 1),
    DIVIDE ("\\/", "SLASH", 1),
    LPAREN ("\\(", "LPAREN", 1),
    RPAREN ("\\)", "RPAREN", 1),
    END ("$", "END", 1),

    NUMBER ("(-)?([0123456789])+([.,]([0123456789])+)?", "NUMBER", 2);

    private final Pattern regex;

    private final String repr;

    private Number value;
    private final int precedence;

    Token (String regex, String repr, int precedence) {
        this.regex = Pattern.compile("^" + regex);
        this.repr = repr;
        this.precedence = precedence;
    }

    public boolean matches (String another) {
        return getFinderRegex().matcher(another).lookingAt();
    }

    public Number getValue () {
        if (this == NUMBER) {
            return Objects.requireNonNull(value);
        } else {
            throw new RuntimeException("Token " + this + " doesn't have value!");
        }
    }

    @Override
    public String toString () {
        return repr;
    }

    public static Token getToken (String pattern) {
        Token token1 = null;

        boolean matched = false;

        for (Token token : Arrays.stream(Token.values()).sorted((item1, item2) -> item2.precedence - item1.precedence).collect(Collectors.toList())) {
            if (token.matches(pattern)) {
                token1 = token;
                matched = true;
                break;

            }
        }
        if (!matched) {
            throw new UnknownTokenException("Unknown token " + pattern + "!");
        }


        if (token1 == NUMBER) {
            Matcher matcher = token1.regex.matcher(pattern);

            System.out.println(pattern + ", " + matcher);
            String extracted = pattern.substring(matcher.start(), matcher.end());
            token1.value = Float.parseFloat(extracted);
        }

        return token1;
    }

    public Pattern getRemoverRegex () {
        return regex;
    }

    public Pattern getFinderRegex () {
        return Pattern.compile(regex.pattern() + "(.*?)");
    }
}
