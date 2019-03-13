package parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("WeakerAccess")
class ParserTest {
    @Test
    public void shouldThrowError_whenInputIsEmpty () {
        assertThrows(RuntimeException.class, () -> new Parser(""));
    }

    @Test
    public void shouldIgnoreWhitespace () {
        assertSame(new Parser("3 + 2").parse(), 5.0);
    }
}