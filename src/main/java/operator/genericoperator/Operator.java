package operator.genericoperator;

import lexer.token.Token;
import expression.Payload;
import math.fraction.fraction.Fractionable;
import operator.BoundingOperator;
import operator.binaryoperator.BinaryOperator;
import operator.binaryoperator.EvaluatingOrder;
import operator.unaryoperator.UnaryOperator;
import operator.unaryoperator.UnaryOperatorType;

import java.math.BigInteger;
import java.util.List;

public interface Operator extends Payload {
    Operator operatorAdd        = new BinaryOperator(Token.ADD,        (fractionable, fractionable2) -> fractionable.fractionValue().add(fractionable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
    Operator operatorSubtract   = new BinaryOperator(Token.SUBTRACT,   (fractionable, fractionable2) -> fractionable.fractionValue().subtract(fractionable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
    Operator operatorMultiply   = new BinaryOperator(Token.MULTIPLY,   (fractionable, fractionable2) -> fractionable.fractionValue().multiply(fractionable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
    Operator operatorDivide     = new BinaryOperator(Token.DIVIDE,     (fractionable, fractionable2) -> fractionable.fractionValue().divide(fractionable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
    Operator operatorPower      = new BinaryOperator(Token.POWER,      (fractionable, fractionable2) -> fractionable.fractionValue().power(fractionable2.fractionValue()), EvaluatingOrder.RIGHT_TO_LEFT);
    Operator operatorRoot       = new BinaryOperator(Token.ROOT,       (fractionable, fractionable2) -> fractionable2.fractionValue().root(fractionable.fractionValue().safeIntValue()), EvaluatingOrder.LEFT_TO_RIGHT);
    Operator operatorSqrt       = new UnaryOperator(Token.SQRT,                       (fractionable) -> fractionable.fractionValue().root(BigInteger.valueOf(2)), UnaryOperatorType.PREFIX);
    Operator operatorNegate     = new UnaryOperator(Token.SUBTRACT,                   (fractionable) -> fractionable.fractionValue().negate(), UnaryOperatorType.PREFIX);
    Operator operatorPosite     = new UnaryOperator(Token.ADD,                        (fractionable) -> fractionable, UnaryOperatorType.PREFIX);
    Operator operatorEllipsis   = new UnaryOperator(Token.ELLIPSIS,                   (fractionable) -> fractionable.fractionValue().toEndless(), UnaryOperatorType.BOUNDARY);
    Operator operatorFactorial  = new UnaryOperator(Token.EXCLAMATION,                (fractionable) -> fractionable.fractionValue().factorial(), UnaryOperatorType.SUFFIX);
    Operator operatorSin        = new UnaryOperator(Token.SIN,                        (fractionable) -> fractionable.fractionValue().sin(), UnaryOperatorType.PREFIX);
    Operator operatorCos        = new UnaryOperator(Token.COS,                        (fractionable) -> fractionable.fractionValue().cos(), UnaryOperatorType.PREFIX);
    Operator operatorTan        = new UnaryOperator(Token.TAN,                        (fractionable) -> fractionable.fractionValue().tan(), UnaryOperatorType.PREFIX);
    Operator operatorLog10      = new UnaryOperator(Token.LOG10,                      (fractionable) -> fractionable.fractionValue().log10(), UnaryOperatorType.PREFIX);
    Operator operatorLog2       = new UnaryOperator(Token.LOG2,                       (fractionable) -> fractionable.fractionValue().log2(), UnaryOperatorType.PREFIX);
    Operator operatorLn         = new UnaryOperator(Token.LN,                         (fractionable) -> fractionable.fractionValue().ln(), UnaryOperatorType.PREFIX);

    Operator operatorAbs        = new BoundingOperator(Token.ABS, Token.ABS,          (fractionable) -> fractionable.fractionValue().abs(), "Absolute value");
    Operator operatorParen      = new BoundingOperator(Token.LPAREN, Token.RPAREN,    (fractionable) -> fractionable, "Parenthesis");

    OperatorType getOperatorType ();
    Token getTokenType();
    int getArity();
    Fractionable invoke (List<Fractionable> operands);
}
