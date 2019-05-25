package parser;

import operator.unaryoperator.UnaryOperatorGroup;

@SuppressWarnings("WeakerAccess")
class ParserTest {
    private UnaryOperatorGroup unaryOperatorGroup = UnaryOperatorGroup.UNARY_OPERATOR_GROUP;

//    @Test
//    public void shouldThrowError_whenInputIsEmpty () {
//        assertThrows(RuntimeException.class, () -> new Parser("", new ValueProvider<>(), unaryOperatorGroup));
//    }
//
//    @Test
//    public void shouldIgnoreWhitespace () {
//        assertEquals(new Parser("3      +   2", new ValueProvider<>(), unaryOperatorGroup).parse(), new Fraction(5, 1));
//    }
//
//    @Test
//    public void shouldIgnoreBrackets () {
//        assertEquals(new Parser("((((((3))))))", new ValueProvider<>(), unaryOperatorGroup).parse(), new Fraction(3, 1));
//    }
}