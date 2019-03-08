package lexer;

import lexer.token.Token;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("WeakerAccess")
public class LexerTest {

    @SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
    @Test
    public void getNextToken () {
        Lexer goodLexer = new Lexer("-0.2 + (12,45 - 5 / 2 * 4.2) - 8");
        assertEquals(goodLexer.getNextToken(), Token.NUMBER);
        assertEquals(goodLexer.getNextToken(), Token.ADD);
        assertEquals(goodLexer.getNextToken(), Token.LPAREN);
        assertEquals(goodLexer.getNextToken(), Token.NUMBER);
        assertEquals(goodLexer.getNextToken(), Token.SUBTRACT);
        assertEquals(goodLexer.getNextToken(), Token.NUMBER);
        assertEquals(goodLexer.getNextToken(), Token.DIVIDE);
        assertEquals(goodLexer.getNextToken(), Token.NUMBER);
        assertEquals(goodLexer.getNextToken(), Token.MULTIPLY);
        assertEquals(goodLexer.getNextToken(), Token.NUMBER);
        assertEquals(goodLexer.getNextToken(), Token.RPAREN);
        assertEquals(goodLexer.getNextToken(), Token.SUBTRACT);
        assertEquals(goodLexer.getNextToken(), Token.NUMBER);
        assertEquals(goodLexer.getNextToken(), Token.END);



        assertThrows(UnknownTokenException.class, () -> new Lexer("-0,.2 + (12,.45 - 5 / 2 * 4.2) - 8"));

    }
}