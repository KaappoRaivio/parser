package operator.unaryoperator;

import lexer.token.FoundToken;

import java.util.List;

public class UnaryOperatorGroup {
//    public static final UnaryOperatorGroup UNARY_OPERATOR_GROUP = new UnaryOperatorGroup(
//            Arrays.asList(new UnaryOperator(OldToken.ADD, a -> a, UnaryOperatorType.PREFIX),
//                    new UnaryOperator(OldToken.SUBTRACT, fractionable -> fractionable.fractionValue().negate(), UnaryOperatorType.PREFIX),
//                    new UnaryOperator(OldToken.SQRT, fractionable -> fractionable.fractionValue().root(2), UnaryOperatorType.PREFIX)),
//
//            Arrays.asList(new UnaryOperator(OldToken.ELLIPSIS, fractionable -> fractionable.fractionValue().toEndless(), UnaryOperatorType.SUFFIX),
//                    new UnaryOperator(OldToken.EXCLAMATION, fractionable -> fractionable.fractionValue().factorial(), UnaryOperatorType.SUFFIX))
//    );


    private List<UnaryOperator> prefixOperators;
    private List<UnaryOperator> suffixOperators;

    UnaryOperatorGroup (List<UnaryOperator> prefixOperators, List<UnaryOperator> suffixOperators) {
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
                .orElseThrow(() -> new RuntimeException("OldToken " + token + " is not defined as a suffix operator!"));
    }

    public UnaryOperator getPrefixOperator (FoundToken token) {
        return prefixOperators
                .stream()
                .filter(unaryOperator -> token.is(unaryOperator.getTokenType()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("OldToken " + token + " is not defined as a prefix operator!"));
    }

    public boolean isSuffixOperator (FoundToken token) {
        return suffixOperators
                .stream()
                .anyMatch(operator -> token.is(operator.getTokenType()));
    }
}
