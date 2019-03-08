package lexer;

import lexer.token.FoundToken;
import lexer.token.Token;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoundTokenTest {

    @SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
    @Test
    void equals() {
        assertEquals(new FoundToken(Token.ADD), Token.ADD);
        assertNotEquals(new FoundToken(Token.ADD), Token.SUBTRACT);
    }
}