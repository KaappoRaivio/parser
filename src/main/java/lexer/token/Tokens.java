package lexer.token;

import misc.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Tokens {
    public static final Tokens DEFAULT_TOKENS = new Tokens(
            new Token ("\\.\\.\\.", "Ellipsis", -2),
            new Token ("[a-z\\%]", "Symbol", -1),

            new Token ("sin", "Sin", 0),
            new Token ("cos", "Cos", 0),
            new Token ("tan", "Tan", 0),
            new Token ("log10", "Log10", 0),
            new Token ("log2", "Log2", 0),
            new Token ("ln", "Ln", 0),

            new Token ("\\+", "Plus", 1),
            new Token ("\\-", "Minus", 1),
            new Token ("\\*", "Asterisk", 1),
            new Token ("\\/", "Slash", 1),
            new Token ("\\(", "Left parenthesis", 1),
            new Token ("\\)", "Right parenthesis", 1),
            new Token ("\\|", "Pipe", 1),
            new Token ("(\\√|sqrt)", "Square root", 1),
            new Token ("root", "nth root", 1),
            new Token ("\\!", "Factorial", 1),
            new Token ("(\\*\\*|\\^)", "Power", 2),
            Token.NUMBER
    );

    private List<Token> tokens;
    private Set<Token> implicitTokens;
    private Map<String, Token> tokenMap = new HashMap<>();

    public Tokens (Token... tokens) {
        this(Arrays.asList(tokens), new HashSet<>());
    }

    public Tokens (List<Token> tokens, Set<Token> implicitTokens) {
        this.tokens = tokens;
        this.implicitTokens = implicitTokens;

        tokens.forEach(item -> tokenMap.put(item.toString().toLowerCase(), item));
    }

    private List<Token> sortedValues () {
        return tokens.stream().sorted((token1, token2) -> token2.getPrecedence() - token1.getPrecedence()).collect(Collectors.toList());
    }

    public Token getToken (String pattern) {
        return sortedValues().stream()
                .filter(token -> token.matches(pattern))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Unrecognized pattern " + pattern + "!"));
    }

    public Token getByName (String name) {
        return Optional.ofNullable(tokenMap.get(name.toLowerCase())).orElseThrow(() -> new NullPointerException("Unknown name " + name + "!" + tokenMap));
    }

    public boolean needsImplicitOperator (FoundToken previousToken, FoundToken latestToken) {
        return implicitTokens.contains(previousToken.getTokenType())
            && implicitTokens.contains(latestToken.getTokenType());
    }
}
