import lexer.Lexer;
import math.Calculator;
import math.Fraction;
import parser.Parser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        System.out.println(new Lexer("3 + 2"));
//        System.out.println(new Lexer("-0.2 + (12,45 - 5 / 2 * 4.02 --23) - 8"));
//        System.out.println(new Parser("-0.2 + (12,45 - 5 / 2 * 4.02 --23) - 8").parse());
//        Parser parser1 = new Parser<>("-0.2 + (12,45 - 5 / 2 * 4.02 --23) - 8", new Calculator<>());
        Parser<Fraction> parser1 = new Parser<>(new Scanner(System.in).nextLine(), new Calculator<>());
//        Parser parser = new Parser<>("", new Calculator<Fraction>());
        System.out.println(parser1.getLexer());
        System.out.println(parser1.parse());

    }
}
