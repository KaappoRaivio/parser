package operator.genericoperator;

import expression.Payload;
import lexer.token.Token;
import lexer.token.Tokens;
import math.fraction.fraction.Fraction;
import operator.BoundOperator;
import operator.binaryoperator.BinaryOperator;
import operator.binaryoperator.EvaluatingOrder;
import operator.unaryoperator.UnaryOperator;
import operator.unaryoperator.UnaryOperatorType;

import java.math.BigInteger;
import java.util.List;

public interface Operator extends Payload {
    Operator operatorAdd        = new BinaryOperator(Token.Default.PLUS,        Fraction::add, EvaluatingOrder.LEFT_TO_RIGHT);
    Operator operatorSubtract   = new BinaryOperator(Token.Default.MINUS,       Fraction::subtract, EvaluatingOrder.LEFT_TO_RIGHT);
    Operator operatorMultiply   = new BinaryOperator(Token.Default.ASTERISK,    Fraction::multiply, EvaluatingOrder.LEFT_TO_RIGHT);
    Operator operatorDivide     = new BinaryOperator(Token.Default.SLASH,       Fraction::divide, EvaluatingOrder.LEFT_TO_RIGHT);
    Operator operatorPower      = new BinaryOperator(Token.Default.POWER,       Fraction::power, EvaluatingOrder.RIGHT_TO_LEFT);
    Operator operatorRoot       = new BinaryOperator(Token.Default.NTH_ROOT,    (fraction, fraction2) -> fraction2.root(fraction.safeIntValue()), EvaluatingOrder.LEFT_TO_RIGHT);
    Operator operatorSqrt       = new UnaryOperator(Token.Default.SQUARE_ROOT,  fraction -> fraction.root(BigInteger.valueOf(2)), UnaryOperatorType.PREFIX);
    Operator operatorNegate     = new UnaryOperator(Token.Default.MINUS,        Fraction::negate, UnaryOperatorType.PREFIX);
    Operator operatorPosite     = new UnaryOperator(Token.Default.PLUS,         fraction -> fraction, UnaryOperatorType.PREFIX);
    Operator operatorEllipsis   = new UnaryOperator(Token.Default.ELLIPSIS,     Fraction::toEndless, UnaryOperatorType.BOUNDARY);
    Operator operatorFactorial  = new UnaryOperator(Token.Default.EXCLAMATION,  Fraction::factorial, UnaryOperatorType.SUFFIX);
    Operator operatorSin        = new UnaryOperator(Token.Default.SIN,          Fraction::sin, UnaryOperatorType.PREFIX);
    Operator operatorCos        = new UnaryOperator(Token.Default.COS,          Fraction::cos, UnaryOperatorType.PREFIX);
    Operator operatorTan        = new UnaryOperator(Token.Default.TAN,          Fraction::tan, UnaryOperatorType.PREFIX);
    Operator operatorLog10      = new UnaryOperator(Token.Default.LOG10,        Fraction::log10, UnaryOperatorType.PREFIX);
    Operator operatorLog2       = new UnaryOperator(Token.Default.LOG2,         Fraction::log2, UnaryOperatorType.PREFIX);
    Operator operatorLn         = new UnaryOperator(Token.Default.LN,           Fraction::ln, UnaryOperatorType.PREFIX);

    Operator operatorAbs        = new BoundOperator(Token.Default.PIPE,
            Token.Default.PIPE,       Fraction::abs, "Absolute value");
    Operator operatorParen      = new BoundOperator(Token.Default.LEFT_PARENTHESIS,
            Token.Default.RIGHT_PARENTHESIS, fraction -> fraction, "Parenthesis");

    OperatorType getOperatorType ();
    Token getTokenType();
    int getArity();
    Fraction invoke (List<Fraction> operands);
}
