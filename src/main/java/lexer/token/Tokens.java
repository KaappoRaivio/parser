package lexer.token;

import misc.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Tokens {
    public static final Tokens DEFAULT_TOKENS = new Tokens(
            Token.Default.ELLIPSIS,
            Token.Default.SYMBOL,

            Token.Default.SIN,
            Token.Default.COS,
            Token.Default.TAN,
            Token.Default.LOG10,
            Token.Default.LOG2,
            Token.Default.LN,

            Token.Default.PLUS,
            Token.Default.MINUS,
            Token.Default.ASTERISK,
            Token.Default.SLASH,

            Token.Default.LEFT_PARENTHESIS,
            Token.Default.RIGHT_PARENTHESIS,
            Token.Default.PIPE,

            Token.Default.SQUARE_ROOT,
            Token.Default.NTH_ROOT,
            Token.Default.EXCLAMATION,
            Token.Default.POWER,
            Token.Default.NUMBER
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

    public void addToken (Token token) {
        tokens.add(token);
    }
}
