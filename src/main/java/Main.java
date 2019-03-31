import lexer.token.Token;
import math.Calculator;
import operator.binaryoperator.BinaryOperator;
import operator.binaryoperator.BinaryOperatorGroup;
import operator.binaryoperator.BinaryOperatorStack;
import operator.binaryoperator.EvaluatingOrder;
import operator.unaryoperator.UnaryOperatorGroup;
import parser.Parser;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
//        System.out.println(new Lexer("3 + 2"));
//        System.out.println(new Lexer("-0.2 + (12,45 - 5 / 2 * 4.02 --23) - 8"));
//        System.out.println(new Parser("-0.2 + (12,45 - 5 / 2 * 4.02 --23) - 8").parse());
        final var operatorAdd = new BinaryOperator(Token.ADD, ((fractionatable, fractionatable2) -> fractionatable.fractionValue().add(fractionatable2.fractionValue())), EvaluatingOrder.LEFT_TO_RIGHT);
        final var operatorSub = new BinaryOperator(Token.SUBTRACT, ((fractionatable, fractionatable2) -> fractionatable.fractionValue().subtract(fractionatable2.fractionValue())), EvaluatingOrder.LEFT_TO_RIGHT);
        final var operatorMul = new BinaryOperator(Token.MULTIPLY, ((fractionatable, fractionatable2) -> fractionatable.fractionValue().multiply(fractionatable2.fractionValue())), EvaluatingOrder.LEFT_TO_RIGHT);
        final var operatorDiv = new BinaryOperator(Token.DIVIDE, ((fractionatable, fractionatable2) -> fractionatable.fractionValue().divide(fractionatable2.fractionValue())), EvaluatingOrder.LEFT_TO_RIGHT);

        BinaryOperatorStack binaryOperatorStack = new BinaryOperatorStack(Arrays.asList(
            new BinaryOperatorGroup(Arrays.asList(operatorAdd, operatorSub)),
            new BinaryOperatorGroup(Arrays.asList(operatorMul, operatorDiv))
        ));


        Parser parser1 = new Parser("(3 + 2) * 3", new Calculator<>(), UnaryOperatorGroup.UNARY_OPERATOR_GROUP, binaryOperatorStack);

//        Parser<Fraction> parser1 = new Parser<>(new Scanner(System.in).nextLine(), new Calculator<>());
//        Parser parser = new Parser<>("", new Calculator<Fraction>());
        System.out.println(parser1.getLexer());


        long a = System.currentTimeMillis();
        System.out.println(parser1.parse());
        long b = System.currentTimeMillis();

        System.out.println("Took " + (b - a) + " ms");

    }
}
