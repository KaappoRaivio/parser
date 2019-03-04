import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
    }

    @Test
    public void number_shouldEqual () {
        assertTrue(Token.NUMBER.matches("342"));
        assertTrue(Token.NUMBER.matches("0"));
        assertTrue(Token.NUMBER.matches("-23"));
        assertTrue(Token.NUMBER.matches("-0.32"));
        assertTrue(Token.NUMBER.matches("0,321342"));
    }

    @Test
    public void number_shouldNotEqual () {
        assertFalse(Token.NUMBER.matches("342."));
        assertFalse(Token.NUMBER.matches("adff"));
        assertFalse(Token.NUMBER.matches("-."));
    }


}