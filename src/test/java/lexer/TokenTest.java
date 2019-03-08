package lexer;

import lexer.token.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("WeakerAccess")
public class TokenTest {
    @Test
    public void operator_shouldEqual () {
        Assertions.assertTrue(Token.ADD.matches("+"));
        Assertions.assertTrue(Token.SUBTRACT.matches("-"));
        Assertions.assertTrue(Token.MULTIPLY.matches("*"));
        Assertions.assertTrue(Token.DIVIDE.matches("/"));

    }

    @Test
    public void operator_shouldNotEqual () {
        Assertions.assertFalse(Token.ADD.matches("-"));
        Assertions.assertFalse(Token.ADD.matches("*"));
        Assertions.assertFalse(Token.SUBTRACT.matches("/"));
        Assertions.assertFalse(Token.MULTIPLY.matches("+"));
    }

    @Test
    public void number_shouldEqual () {
        Assertions.assertTrue(Token.NUMBER.matches("342"));
        Assertions.assertTrue(Token.NUMBER.matches("0 + 3"));
        Assertions.assertTrue(Token.NUMBER.matches("-23 asd"));
        Assertions.assertTrue(Token.NUMBER.matches("-0.32"));
        Assertions.assertTrue(Token.NUMBER.matches("0,321342"));
        Assertions.assertTrue(Token.NUMBER.matches("342."));
    }

    @Test
    public void number_shouldNotEqual () {
        Assertions.assertFalse(Token.NUMBER.matches("adff"));
        Assertions.assertFalse(Token.NUMBER.matches("-."));
    }

    @Test
    public void parens () {
        Assertions.assertTrue(Token.LPAREN.matches("("));
        Assertions.assertFalse(Token.LPAREN.matches(")"));

        Assertions.assertTrue(Token.RPAREN.matches(")"));
        Assertions.assertFalse(Token.RPAREN.matches("("));
    }

    @Test
    public void end () {
        Assertions.assertTrue(Token.END.matches(""));
        Assertions.assertFalse(Token.END.matches("asdasd"));
    }

    @Test
    public void getToken () {
        assertSame(Token.getToken("-3 + 2"), Token.NUMBER);
        assertSame(Token.getToken("-3,34 + 2"), Token.NUMBER);
        assertSame(Token.getToken("-0.3 + 2"), Token.NUMBER);


        assertSame(Token.getToken("+ 2"), Token.ADD);
        assertSame(Token.getToken("2"), Token.NUMBER);
        assertSame(Token.getToken("() + 2"), Token.LPAREN);
        assertSame(Token.getToken(")"), Token.RPAREN);
    }
}