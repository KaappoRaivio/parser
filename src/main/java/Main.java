import lexer.token.Token;
import math.fraction.Calculator;
import operator.binaryoperator.BinaryOperator;
import operator.binaryoperator.EvaluatingOrder;
import operator.genericoperator.GenericOperatorGroup;
import operator.genericoperator.GenericOperatorStack;
import operator.genericoperator.Operator;
import operator.genericoperator.OperatorType;
import operator.unaryoperator.UnaryOperator;
import operator.unaryoperator.UnaryOperatorType;
import parser.GenericParser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        System.out.println(new Lexer("3 + 2"));
//        System.out.println(new Lexer("-0.2 + (12,45 - 5 / 2 * 4.02 --23) - 8"));
//        System.out.println(new Parser("-0.2 + (12,45 - 5 / 2 * 4.02 --23) - 8").parse());
        final var operatorAdd = new BinaryOperator(Token.ADD, (fractionatable, fractionatable2) -> fractionatable.fractionValue().add(fractionatable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
        final var operatorSub = new BinaryOperator(Token.SUBTRACT, (fractionatable, fractionatable2) -> fractionatable.fractionValue().subtract(fractionatable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
        final var operatorMul = new BinaryOperator(Token.MULTIPLY, (fractionatable, fractionatable2) -> fractionatable.fractionValue().multiply(fractionatable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
        final var operatorDiv = new BinaryOperator(Token.DIVIDE, (fractionatable, fractionatable2) -> fractionatable.fractionValue().divide(fractionatable2.fractionValue()), EvaluatingOrder.LEFT_TO_RIGHT);
        final var operatorPow = new BinaryOperator(Token.POWER, (fractionatable, fractionatable2) -> fractionatable.fractionValue().power(fractionatable2.fractionValue()), EvaluatingOrder.RIGHT_TO_LEFT);
        final var operatorSqr = new UnaryOperator(Token.SQRT, fractionatable -> fractionatable.fractionValue().root(2), UnaryOperatorType.PREFIX);
        final var operatorNeg = new UnaryOperator(Token.SUBTRACT, fractionatable -> fractionatable.fractionValue().negate(), UnaryOperatorType.PREFIX);

//        BinaryOperatorStack binaryOperatorStack = new BinaryOperatorStack(
//                new BinaryOperatorGroup(operatorAdd, operatorSub),
//                new BinaryOperatorGroup(operatorMul, operatorDiv),
////                new UnaryOperatorGroup(operatorNeg),
//                new BinaryOperatorGroup(operatorPow)
//        );

        GenericOperatorStack operatorStack = new GenericOperatorStack(
                new GenericOperatorGroup(OperatorType.BINARY, operatorAdd, operatorSub),
                new GenericOperatorGroup(OperatorType.BINARY, operatorMul, operatorDiv),
                new GenericOperatorGroup(OperatorType.UNARY, operatorNeg),
                new GenericOperatorGroup(OperatorType.UNARY, operatorNeg),
                new GenericOperatorGroup(OperatorType.BINARY, operatorPow),
                new GenericOperatorGroup(OperatorType.UNARY, operatorSqr)
        );
        //√
//        Parser parser1 = new Parser("-2^2", new Calculator<>(), UnaryOperatorGroup.UNARY_OPERATOR_GROUP, binaryOperatorStack);

        while (true) {
            try {
                String instr = new Scanner(System.in).nextLine();
                GenericParser parser1 = new GenericParser(instr, new Calculator<>(), operatorStack);
                System.out.println(parser1.parse());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
