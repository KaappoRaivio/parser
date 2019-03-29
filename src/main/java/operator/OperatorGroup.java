package operator;

import lexer.UnknownTokenException;
import lexer.token.FoundToken;
import lexer.token.Token;
import math.Fraction;

import java.util.Arrays;
import java.util.List;

public class OperatorGroup<T extends Fraction> {
    public static final OperatorGroup<Fraction> UNARY_OPERATOR_GROUP = new OperatorGroup<>(
            Arrays.asList(new UnaryOperator<>(Token.ADD, (a) -> a, UnaryOperatorType.PREFIX), new UnaryOperator<>(Token.SUBTRACT, Fraction::negate, UnaryOperatorType.PREFIX), new UnaryOperator<>(Token.SQRT, (fraction) -> fraction.root(2), UnaryOperatorType.PREFIX)),
            Arrays.asList(new UnaryOperator<>(Token.ELLIPSIS, Fraction::toEndless, UnaryOperatorType.SUFFIX), new UnaryOperator<>(Token.EXCLAMATION, Fraction::factorial, UnaryOperatorType.SUFFIX))
    );


    private List<UnaryOperator<T>> prefixOperators;
    private List<UnaryOperator<T>> suffixOperators;

    public OperatorGroup(List<UnaryOperator<T>> prefixOperators, List<UnaryOperator<T>> suffixOperators) {
        this.prefixOperators = prefixOperators;
        this.suffixOperators = suffixOperators;
    }

    public boolean isPrefixOperator (FoundToken token) {
        for (UnaryOperator<T> operator : prefixOperators) {
            if (token.is(operator.getTokenType())) {
                return true;
            }
        }

        return false;
    }

    public UnaryOperator<T> getSuffixOperator (FoundToken token) {
        for (UnaryOperator<T> operator : suffixOperators) {
            if (token.is(operator.getTokenType())) {
                return operator;
            }
        }

        throw new RuntimeException("Token " + token + " is not a suffix operator");
    }

    public UnaryOperator<T> getPrefixOperator (FoundToken token) {
        for (UnaryOperator<T> operator : prefixOperators) {
            if (token.is(operator.getTokenType())) {
                return operator;
            }
        }

        throw new RuntimeException("Token " + token + " is not a prefix operator");
    }

    public boolean isSuffixOperator (FoundToken token) {
        for (UnaryOperator<T> operator : suffixOperators) {
            if (token.is(operator.getTokenType())) {
                return true;
            }
        }

        return false;
    }
}
