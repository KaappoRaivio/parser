package math;

public class Calculator<T extends Fraction> {
    public Fractionatable add (Fractionatable t1, Fractionatable t2) {
        return t1.fractionValue().add(t2.fractionValue());
    }

    public Fractionatable subtract (Fractionatable t1, Fractionatable t2) {
        return t1.fractionValue().subtract(t2.fractionValue());
    }

    public Fractionatable multiply (Fractionatable t1, Fractionatable t2) {
        return t1.fractionValue().multiply(t2.fractionValue());
    }

    public Fractionatable divide (Fractionatable t1, Fractionatable t2) {
        return t1.fractionValue().divide(t2.fractionValue());
    }

    public Fractionatable add (Fractionatable t1, int t2) {
        return t1.fractionValue().add(t2);
    }

    public Fractionatable subtract (Fractionatable t1, int t2) {
        return t1.fractionValue().subtract(t2);
    }

    public Fractionatable multiply (Fractionatable t1, int t2) {
        return t1.fractionValue().multiply(t2);
    }

    public Fractionatable divide (Fractionatable t1, int t2) {
        return t1.fractionValue().divide(t2);
    }

    public Fractionatable abs(Fractionatable t1) {
        return t1.fractionValue().abs();
    }

    public Fractionatable root (Fractionatable t1, int n) {
        return t1.fractionValue().root(n);
    }

    public Fractionatable valueOf (String string, boolean repeatingDecimal) {
        return T.fromDecimal(string, repeatingDecimal);
    }
}
