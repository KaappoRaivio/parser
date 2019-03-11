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
        assertTrue(goodLexer.getNextToken().is(Token.NUMBER));
        assertTrue(goodLexer.getNextToken().is(Token.ADD));
        assertTrue(goodLexer.getNextToken().is(Token.LPAREN));
        assertTrue(goodLexer.getNextToken().is(Token.NUMBER));
        assertTrue(goodLexer.getNextToken().is(Token.SUBTRACT));
        assertTrue(goodLexer.getNextToken().is(Token.NUMBER));
        assertTrue(goodLexer.getNextToken().is(Token.DIVIDE));
        assertTrue(goodLexer.getNextToken().is(Token.NUMBER));
        assertTrue(goodLexer.getNextToken().is(Token.MULTIPLY));
        assertTrue(goodLexer.getNextToken().is(Token.NUMBER));
        assertTrue(goodLexer.getNextToken().is(Token.RPAREN));
        assertTrue(goodLexer.getNextToken().is(Token.SUBTRACT));
        assertTrue(goodLexer.getNextToken().is(Token.NUMBER));
        assertTrue(goodLexer.getNextToken().is(Token.END));



        assertThrows(UnknownTokenException.class, () -> new Lexer("-0,.2 + (12,.45 - 5 / 2 * 4.2) - 8"));

    }
}