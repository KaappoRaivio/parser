package math.symbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Polynomial {
    private List<Term> terms;

    public List<Term> getTerms (int stretch) {
        var sorted = getTerms();

        for (int i = stretch - 1; i >= 0; i--) {
            if (sorted.stream().anyMatch((term) -> term.isLikeIn(this))) {

            }
        }

        return null;
    }

    List<Term> getTerms() {
        return terms
                .stream()
                .sorted(Comparator.comparingInt(Term::getN))
                .collect(Collectors.toList());
    }

    Polynomial (Term... terms) {
        this.terms = Arrays.asList(terms);
    }

    public Polynomial plus (Polynomial othr) {
        List<Term> newTerms  = new ArrayList<>();

        int loopLength = Math.max(this.size(), othr.size());

        for (int i = 0; i < loopLength; i++) {

        }

        return null;
    }

    public int size () {
        return terms.size();
    }

    public int degree () {
        return largestTerm().getN();
    }

    public Term largestTerm () {
        return getTerms()
                .stream()
                .max(Comparator.comparingInt(Term::getN))
                .orElseThrow();
    }

    @Override
    public String toString() {
        return terms.stream().map(Term::toString).collect(Collectors.joining(" "));
    }


}
