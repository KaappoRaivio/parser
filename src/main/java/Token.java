import java.util.regex.Pattern;

public enum Token {
    ADD ("\\+"),
    SUBTRACT ("\\-"),
    MULTIPLY ("\\*"),
    DIVIDE ("\\/"),
    LPAREN ("\\("),
    RPAREN ("\\)"),
    END ("$"),

    NUMBER ("(-)?([123456789])+([.,]([123456789])+)?");

    private Pattern regex;

    Token (String regex) {
        this.regex = Pattern.compile("^" +regex);
    }

    public boolean matches (String another) {
        return regex.matcher(another).matches();
    }
}
