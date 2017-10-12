import java.util.function.*;

public class LambdaExample {
    private int info;

    /**
     * Lambda function
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
     * Approximate derivative
     *
     * @param f
     * @param a
     * @param x
     * @param n
     * @return double
     */
    public double trapezoidalRule(Function<Double, Double> f, double a, double x, double n) {
        double integral = 0.0;

        double h = (x - a) / n;
        double F = 0.5 * f.apply(a);
        for (int i=1; i<=n; i++) {
            integral += f.apply(a + i*h);
        }
        integral += 0.5 * f.apply(x);
        integral *= h;
        return integral;
    }

    public static double derivative(Function<Double, Double> f, double x, double h) {
        return (f.apply(x+h) - f.apply(x-h)) / (2*h);
    }

    public static void main(String[] args) {

//        new LambdaExample().trapezoidalRule(f, );
        double dydx = derivative(f(), 3, 0.5);
        System.out.println(dydx);

        Function<Double, Double> g = f();
        System.out.println(g.apply(2.0));
    }
}