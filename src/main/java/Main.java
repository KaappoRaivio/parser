import lexer.Lexer;
import parser.Parser;

public class Main {
    public static void main(String[] args) {
//        System.out.println(new Lexer("3 + 2"));
//        System.out.println(new Lexer("-0.2 + (12,45 - 5 / 2 * 4.02 --23) - 8"));
//        System.out.println(new Parser("-0.2 + (12,45 - 5 / 2 * 4.02 --23) - 8").parse());
//        Parser parser = new Parser("3 * (4 + 2) * 5");
        Parser parser1 = new Parser("");
        System.out.println(parser1.getLexer());
        System.out.println(parser1.parse());

    }
}
