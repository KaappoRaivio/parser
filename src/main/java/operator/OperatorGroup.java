package operator;

import lexer.token.FoundToken;
import lexer.token.Token;
import math.Calculator;
import math.Fraction;

import java.util.Arrays;
import java.util.List;

public class OperatorGroup {
    private static final Calculator<Fraction> calculator = new Calculator<>();
    public static final OperatorGroup UNARY_OPERATOR_GROUP = new OperatorGroup(
            Arrays.asList(new UnaryOperator(Token.ADD, (a) -> a, UnaryOperatorType.PREFIX),
                    new UnaryOperator(Token.SUBTRACT, fractionizeable -> fractionizeable.fractionValue().negate(), UnaryOperatorType.PREFIX),
                    new UnaryOperator(Token.SQRT, (fractionizeable) -> fractionizeable.fractionValue().root(2), UnaryOperatorType.PREFIX)),

            Arrays.asList(new UnaryOperator(Token.ELLIPSIS, fractionizeable -> fractionizeable.fractionValue().toEndless(), UnaryOperatorType.SUFFIX),
                    new UnaryOperator(Token.EXCLAMATION, fractionizeable -> fractionizeable.fractionValue().factorial(), UnaryOperatorType.SUFFIX))
    );


    private List<UnaryOperator> prefixOperators;
    private List<UnaryOperator> suffixOperators;

    OperatorGroup (List<UnaryOperator> prefixOperators, List<UnaryOperator> suffixOperators) {
        this.prefixOperators = prefixOperators;
        this.suffixOperators = suffixOperators;
    }

    public boolean isPrefixOperator (FoundToken token) {
        return prefixOperators
                .stream()
                .anyMatch(operator -> token.is(operator.getTokenType()));
    }

    public UnaryOperator getSuffixOperator (FoundToken token) {
        return suffixOperators
                .stream()
                .filter(unaryOperator -> token.is(unaryOperator.getTokenType()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Token " + token + " is not defined as a suffix operator!"));
    }

    public UnaryOperator getPrefixOperator (FoundToken token) {
        return prefixOperators
                .stream()
                .filter(unaryOperator -> token.is(unaryOperator.getTokenType()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Token " + token + " is not defined as a prefix operator!"));
    }

    public boolean isSuffixOperator (FoundToken token) {
        return suffixOperators
                .stream()
                .anyMatch(operator -> token.is(operator.getTokenType()));
    }
}
