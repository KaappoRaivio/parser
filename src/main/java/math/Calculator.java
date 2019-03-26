package math;

import javax.xml.stream.FactoryConfigurationError;

public class Calculator<T extends Fraction> {
    public T add (T t1, T t2) {
        return (T) t1.add(t2);
    }

    public T subtract (T t1, T t2) {
        return (T) t1.subtract(t2);
    }

    public T multiply (T t1, T t2) {
        return (T) t1.multiply(t2);
    }

    public T divide (T t1, T t2) {
        return (T) t1.divide(t2);
    }

    public T pow (T t1, int t2) {
        return (T) t1.pow(t2);
    }

    public T add (T t1, int t2) {
        return (T) t1.add(t2);
    }

    public T subtract (T t1, int t2) {
        return (T) t1.subtract(t2);
    }

    public T multiply (T t1, int t2) {
        return (T) t1.multiply(t2);
    }

    public T divide (T t1, int t2) {
        return (T) t1.divide(t2);
    }

    public T abs(T t1) {
        return (T) t1.abs();
    }

    public T root (T t1, int n) {
        return (T) t1.root(n);
    }

    public T valueOf (String string, boolean repeatingDecimal) {
        return (T) T.fromDecimal(string, repeatingDecimal);
    }
}
