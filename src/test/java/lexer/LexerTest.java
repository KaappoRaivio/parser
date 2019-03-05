package lexer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("WeakerAccess")
public class LexerTest {

    @Test
    public void getNextToken () {
        System.out.println("moi");
        Lexer goodLexer = new Lexer("-0.2 + (12,45 - 5 / 2 * 4.2) - 8");
        assertSame(goodLexer.getNextToken(), Token.NUMBER);
        assertSame(goodLexer.getNextToken(), Token.ADD);
        assertSame(goodLexer.getNextToken(), Token.LPAREN);
        assertSame(goodLexer.getNextToken(), Token.NUMBER);
        assertSame(goodLexer.getNextToken(), Token.SUBTRACT);
        assertSame(goodLexer.getNextToken(), Token.NUMBER);
        assertSame(goodLexer.getNextToken(), Token.DIVIDE);
        assertSame(goodLexer.getNextToken(), Token.NUMBER);
        assertSame(goodLexer.getNextToken(), Token.MULTIPLY);
        assertSame(goodLexer.getNextToken(), Token.NUMBER);
        assertSame(goodLexer.getNextToken(), Token.RPAREN);
        assertSame(goodLexer.getNextToken(), Token.SUBTRACT);
        assertSame(goodLexer.getNextToken(), Token.NUMBER);


        Lexer badLexer = new Lexer("-0,.2 + (12,.45 - 5 / 2 * 4.2) - 8");
        assertThrows(UnknownTokenException.class, badLexer::getNextToken);

    }
}