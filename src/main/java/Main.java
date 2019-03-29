import lexer.token.Token;
import math.Calculator;
import math.Fraction;
import operator.OperatorGroup;
import operator.UnaryOperator;
import operator.UnaryOperatorType;
import parser.Parser;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
//        System.out.println(new Lexer("3 + 2"));
//        System.out.println(new Lexer("-0.2 + (12,45 - 5 / 2 * 4.02 --23) - 8"));
//        System.out.println(new Parser("-0.2 + (12,45 - 5 / 2 * 4.02 --23) - 8").parse());

        Parser parser1 = new Parser<>("3(2)", new Calculator<>(), OperatorGroup.UNARY_OPERATOR_GROUP);

//        Parser<Fraction> parser1 = new Parser<>(new Scanner(System.in).nextLine(), new Calculator<>());
//        Parser parser = new Parser<>("", new Calculator<Fraction>());
        System.out.println(parser1.getLexer());


        long a = System.currentTimeMillis();
        System.out.println(parser1.parse());
        long b = System.currentTimeMillis();

        System.out.println("Took " + (b - a) + " ms");

    }
}
