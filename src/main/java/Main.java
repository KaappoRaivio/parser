import expression.SymbolTable;
import math.error.MathError;
import math.fraction.fraction.Fraction;
import operator.binaryoperator.BinaryOperator;
import operator.genericoperator.GenericOperatorGroup;
import operator.genericoperator.GenericOperatorStack;
import operator.genericoperator.OperatorType;
import parser.ExpressionParser;
import expression.Expression;
import parser.MyValueProvider;

import java.util.Scanner;

public class Main {
//    public static final Operator operatorAdd = new BinaryOperator(Token.ADD, (fractionable, fractionable2) -> fractionable.fractionValue().add(fractionable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
//    public static final Operator operatorSub = new BinaryOperator(Token.SUBTRACT, (fractionable, fractionable2) -> fractionable.fractionValue().subtract(fractionable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
//    public static final Operator operatorMul = new BinaryOperator(Token.MULTIPLY, (fractionable, fractionable2) -> fractionable.fractionValue().multiply(fractionable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
//    public static final Operator operatorDiv = new BinaryOperator(Token.DIVIDE, (fractionable, fractionable2) -> fractionable.fractionValue().divide(fractionable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
//    public static final Operator operatorPow = new BinaryOperator(Token.POWER, (fractionable, fractionable2) -> fractionable.fractionValue().power(fractionable2.fractionValue()), EvaluatingOrder.RIGHT_TO_LEFT);
//    public static final Operator operatorSqr = new UnaryOperator(Token.SQRT, fractionable -> fractionable.fractionValue().root(2), UnaryOperatorType.PREFIX);
//    public static final Operator operatorNeg = new UnaryOperator(Token.SUBTRACT, fractionable -> fractionable.fractionValue().negate(), UnaryOperatorType.PREFIX);

    public static void main(String[] args) {
//        System.out.println(new Lexer("3 + 2"));
//        System.out.println(new Parser("-0.2 + (12,45 - 5 / 2 * 4.02 --23) - 8").parse());
//        System.out.println(new Lexer("-0.2 + (12,45 - 5 / 2 * 4.02 --23) - 8"));

//        BinaryOperatorStack binaryOperatorStack = new BinaryOperatorStack(
//                new BinaryOperatorGroup(operatorAdd, operatorSub),
//                new BinaryOperatorGroup(operatorMul, operatorDiv),
////                new UnaryOperatorGroup(operatorNeg),
//                new BinaryOperatorGroup(operatorPow)
//        );

        GenericOperatorStack operatorStack = new GenericOperatorStack(
                new GenericOperatorGroup(
                        OperatorType.UNARY,
                        Expression.operatorEll,
                        Expression.operatorFac
                ),

                new GenericOperatorGroup(
                        OperatorType.UNARY,
                        Expression.operatorAbs,
                        Expression.operatorParen
                ),

                new GenericOperatorGroup(OperatorType.BINARY, Expression.operatorAdd, Expression.operatorSub),
                new GenericOperatorGroup(OperatorType.BINARY, Expression.operatorMul, Expression.operatorDiv),
                new GenericOperatorGroup(OperatorType.UNARY,  Expression.operatorNeg, Expression.operatorPos),
                new GenericOperatorGroup(OperatorType.BINARY, Expression.operatorPow, Expression.operatorIPo),
                new GenericOperatorGroup(OperatorType.BINARY, Expression.operatorRot, Expression.operatorIRo),

                new GenericOperatorGroup(OperatorType.UNARY,  Expression.operatorSqr, Expression.operatorISq,
                                                              Expression.operatorSin, Expression.operatorCos,
                                                              Expression.operatorTan, Expression.operatorL10,
                                                              Expression.operatorLo2, Expression.operatorLon)

        );
        //√
//        Parser parser1 = new Parser("-2^2", new ValueProvider<>(), UnaryOperatorGroup.UNARY_OPERATOR_GROUP, binaryOperatorStack);

        while (true) {
            try {
                String instr = new Scanner(System.in).nextLine();
                var start = System.currentTimeMillis();

                ExpressionParser parser1 = new ExpressionParser<Fraction>(instr, new MyValueProvider<>(), operatorStack, (BinaryOperator) Expression.operatorMul, SymbolTable.defaultTable, false);
                System.out.println(parser1.getLexer());
                var tree = parser1.parse();
                var reduced = tree.reduce();

                var end = System.currentTimeMillis();

                System.out.println(tree.toString());
                System.out.println("Result: " + reduced);
                System.out.println("in decimal " + reduced.fractionValue().toDecimal());
                System.out.println("took " + (end - start) + " ms");

            } catch (MathError e) {
                System.out.println(e.getResult());
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
