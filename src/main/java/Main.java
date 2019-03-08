import lexer.Lexer;

public class Main {
    public static void main(String[] args) {
        System.out.println(new Lexer("3 + 2"));
        System.out.println(new Lexer("-0.2 + (12,45 - 5 / 2 * 4.02 --23) - 8"));

    }
}
