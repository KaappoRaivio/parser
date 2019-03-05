import lexer.Token;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("WeakerAccess")
public class TokenTest {
    @Test
    public void operator_shouldEqual () {
        assertTrue(Token.ADD.matches("+"));
        assertTrue(Token.SUBTRACT.matches("-"));
        assertTrue(Token.MULTIPLY.matches("*"));
        assertTrue(Token.DIVIDE.matches("/"));

    }

    @Test
    public void operator_shouldNotEqual () {
        assertFalse(Token.ADD.matches("-"));
        assertFalse(Token.ADD.matches("*"));
        assertFalse(Token.SUBTRACT.matches("/"));
        assertFalse(Token.MULTIPLY.matches("+"));
    }

    @Test
    public void number_shouldEqual () {
        assertTrue(Token.NUMBER.matches("342"));
        assertTrue(Token.NUMBER.matches("0 + 3"));
        assertTrue(Token.NUMBER.matches("-23 asd"));
        assertTrue(Token.NUMBER.matches("-0.32"));
        assertTrue(Token.NUMBER.matches("0,321342"));
        assertTrue(Token.NUMBER.matches("342."));
    }

    @Test
    public void number_shouldNotEqual () {
        assertFalse(Token.NUMBER.matches("adff"));
        assertFalse(Token.NUMBER.matches("-."));
    }

    @Test
    public void parens () {
        assertTrue(Token.LPAREN.matches("("));
        assertFalse(Token.LPAREN.matches(")"));

        assertTrue(Token.RPAREN.matches(")"));
        assertFalse(Token.RPAREN.matches("("));
    }

    @Test
    public void end () {
        assertTrue(Token.END.matches(""));
        assertFalse(Token.END.matches("asdasd"));
    }

    @Test
    public void getToken () {
        assertSame(Token.getToken("-3 + 2"), Token.NUMBER);
        assertSame(Token.getToken("+ 2"), Token.ADD);
        assertSame(Token.getToken("2"), Token.NUMBER);
        assertSame(Token.getToken("() + 2"), Token.LPAREN);
        assertSame(Token.getToken(")"), Token.RPAREN);
    }
}