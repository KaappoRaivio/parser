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
import parser.MyNumberParser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GenericOperatorStack operatorStack = new GenericOperatorStack(
                new GenericOperatorGroup(OperatorType.UNARY,
                        Operator.operatorEllipsis,
                        Operator.operatorFactorial
                ),

                new GenericOperatorGroup(OperatorType.BOUNDARY,
                        Operator.operatorAbs,
                        Operator.operatorParen
                ),

                new GenericOperatorGroup(OperatorType.BINARY, Operator.operatorAdd, Operator.operatorSubtract),
                new GenericOperatorGroup(OperatorType.BINARY, Operator.operatorMultiply, Operator.operatorDivide),
                new GenericOperatorGroup(OperatorType.UNARY,  Operator.operatorNegate, Operator.operatorPosite),
                new GenericOperatorGroup(OperatorType.BINARY, Operator.operatorPower, Operator.operatorRoot),

                new GenericOperatorGroup(OperatorType.UNARY,  Operator.operatorSqrt,
                                                              Operator.operatorSin, Operator.operatorCos,
                                                              Operator.operatorTan, Operator.operatorLog10,
                                                              Operator.operatorLog2, Operator.operatorLn)

        );
        //√

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                String inputString = scanner.nextLine();

                long start = System.currentTimeMillis();

                ExpressionParser parser1 = new ExpressionParser<Fraction>(inputString, new MyNumberParser<>(), operatorStack, (BinaryOperator) Operator.operatorMultiply, SymbolTable.defaultTable, false);
                System.out.println("Lexer: " + parser1.getLexer());
                Expression expression = parser1.parse();
                Fractionable reduced = expression.reduce();

                long end = System.currentTimeMillis();

                System.out.println("Parse tree: " + expression);
                System.out.println("Result: " + reduced);
                System.out.println("in decimal: " + reduced.fractionValue().toDecimal());
                System.out.println("Calculating took " + (end - start) + " milliseconds.");

            } catch (MathError e) {
                System.out.println(e.getResult());
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
