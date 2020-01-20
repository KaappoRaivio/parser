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
    Operator operatorAdd        = new BinaryOperator(Tokens.DEFAULT_TOKENS.getByName("Plus"),      Fraction::add, EvaluatingOrder.LEFT_TO_RIGHT);
    Operator operatorSubtract   = new BinaryOperator(Tokens.DEFAULT_TOKENS.getByName("Minus"), Fraction::subtract, EvaluatingOrder.LEFT_TO_RIGHT);
    Operator operatorMultiply   = new BinaryOperator(Tokens.DEFAULT_TOKENS.getByName("Asterisk"), Fraction::multiply, EvaluatingOrder.LEFT_TO_RIGHT);
    Operator operatorDivide     = new BinaryOperator(Tokens.DEFAULT_TOKENS.getByName("Slash"),              Fraction::divide, EvaluatingOrder.LEFT_TO_RIGHT);
    Operator operatorPower      = new BinaryOperator(Tokens.DEFAULT_TOKENS.getByName("Power"),               Fraction::power, EvaluatingOrder.RIGHT_TO_LEFT);
    Operator operatorRoot       = new BinaryOperator(Tokens.DEFAULT_TOKENS.getByName("Nth root"),                (fraction, fraction2) -> fraction2.root(fraction.safeIntValue()), EvaluatingOrder.LEFT_TO_RIGHT);
    Operator operatorSqrt       = new UnaryOperator(Tokens.DEFAULT_TOKENS.getByName("Square root"), fraction -> fraction.root(BigInteger.valueOf(2)), UnaryOperatorType.PREFIX);
    Operator operatorNegate     = new UnaryOperator(Tokens.DEFAULT_TOKENS.getByName("Minus"),             Fraction::negate, UnaryOperatorType.PREFIX);
    Operator operatorPosite     = new UnaryOperator(Tokens.DEFAULT_TOKENS.getByName("Plus"), fraction -> fraction, UnaryOperatorType.PREFIX);
    Operator operatorEllipsis   = new UnaryOperator(Tokens.DEFAULT_TOKENS.getByName("Ellipsis"),             Fraction::toEndless, UnaryOperatorType.BOUNDARY);
    Operator operatorFactorial  = new UnaryOperator(Tokens.DEFAULT_TOKENS.getByName("Factorial"),          Fraction::factorial, UnaryOperatorType.SUFFIX);
    Operator operatorSin        = new UnaryOperator(Tokens.DEFAULT_TOKENS.getByName("Sin"),                  Fraction::sin, UnaryOperatorType.PREFIX);
    Operator operatorCos        = new UnaryOperator(Tokens.DEFAULT_TOKENS.getByName("Cos"),                  Fraction::cos, UnaryOperatorType.PREFIX);
    Operator operatorTan        = new UnaryOperator(Tokens.DEFAULT_TOKENS.getByName("Tan"),                  Fraction::tan, UnaryOperatorType.PREFIX);
    Operator operatorLog10      = new UnaryOperator(Tokens.DEFAULT_TOKENS.getByName("Log10"),                Fraction::log10, UnaryOperatorType.PREFIX);
    Operator operatorLog2       = new UnaryOperator(Tokens.DEFAULT_TOKENS.getByName("Log2"),                 Fraction::log2, UnaryOperatorType.PREFIX);
    Operator operatorLn         = new UnaryOperator(Tokens.DEFAULT_TOKENS.getByName("Ln"),                   Fraction::ln, UnaryOperatorType.PREFIX);

    Operator operatorAbs        = new BoundOperator(Tokens.DEFAULT_TOKENS.getByName("Pipe"),
            Tokens.DEFAULT_TOKENS.getByName("Pipe"),       Fraction::abs, "Absolute value");
    Operator operatorParen      = new BoundOperator(Tokens.DEFAULT_TOKENS.getByName("Left parenthesis"),
            Tokens.DEFAULT_TOKENS.getByName("Right parenthesis"), fraction -> fraction, "Parenthesis");

    OperatorType getOperatorType ();
    Token getTokenType();
    int getArity();
    Fraction invoke (List<Fraction> operands);
}
