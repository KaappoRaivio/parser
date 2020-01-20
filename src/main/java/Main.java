import expression.SymbolTable;
import math.error.MathError;
import math.fraction.fraction.Fraction;
import math.fraction.fraction.Fractionable;
import operator.binaryoperator.BinaryOperator;
import operator.genericoperator.GenericOperatorGroup;
import operator.genericoperator.GenericOperatorStack;
import operator.genericoperator.Operator;
import operator.genericoperator.OperatorType;
import parser.ExpressionParser;
import expression.Expression;
import parser.MyValueProvider;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GenericOperatorStack operatorStack = new GenericOperatorStack(
                new GenericOperatorGroup(
                        OperatorType.UNARY,
                        Operator.operatorEllipsis,
                        Operator.operatorFactorial
                ),

                new GenericOperatorGroup(
                        OperatorType.UNARY,
                        Operator.operatorAbs,
                        Operator.operatorParen
                ),

                new GenericOperatorGroup(OperatorType.BINARY, Operator.operatorAdd, Operator.operatorSubtract),
                new GenericOperatorGroup(OperatorType.BINARY, Operator.operatorMultiply, Operator.operatorDivide),
                new GenericOperatorGroup(OperatorType.UNARY,  Operator.operatorNegate, Operator.operatorPosite),
                new GenericOperatorGroup(OperatorType.BINARY, Operator.operatorPower, Expression.operatorInversePower),
                new GenericOperatorGroup(OperatorType.BINARY, Operator.operatorRoot, Expression.operatorInverseRoot),

                new GenericOperatorGroup(OperatorType.UNARY,  Operator.operatorSqrt, Expression.operatorISq,
                                                              Operator.operatorSin, Operator.operatorCos,
                                                              Operator.operatorTan, Operator.operatorLog10,
                                                              Operator.operatorLog2, Operator.operatorLn)

        );
        //√

        while (true) {
            try {
                String instr = new Scanner(System.in).nextLine();
                long start = System.currentTimeMillis();

                ExpressionParser parser1 = new ExpressionParser<Fraction>(instr, new MyValueProvider<>(), operatorStack, (BinaryOperator) Operator.operatorMultiply, SymbolTable.defaultTable, false);
                System.out.println(parser1.getLexer());
                Expression tree = parser1.parse();
                Fractionable reduced = tree.reduce();

                long end = System.currentTimeMillis();

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
