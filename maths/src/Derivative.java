import java.util.function.Function;

public class Derivative {

    /**
     * f(x) = Square root function
     *
     * @return Function<Double, Double>
     */
    public static Function<Double, Double> f() {
        // Outside the scope of the returned function:
        Function<Double, Double> f = new Function<Double, Double>() {
            @Override
            public Double apply(Double x) {
                return x*x;
            }
        };
        return f;
    }

    /**
     * Returns a numerical differentiation
     *
     * @link https://en.wikipedia.org/wiki/Numerical_differentiation
     *
     * @param f
     * @param x
     * @param h
     * @return
     */

    public static double differentiate(Function<Double, Double> f, double x, double h) {
        return (f.apply(x+h) - f.apply(x-h)) / (2*h);
    }

    public static void main(String[] args) {
        double dydx = differentiate(f(), 3, 0.5);
        System.out.println(dydx);
    }
}
