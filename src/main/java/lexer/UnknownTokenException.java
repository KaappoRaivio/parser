package lexer;

public class UnknownTokenException extends RuntimeException {
    public UnknownTokenException (String message) {
        super(message);
    }
}
