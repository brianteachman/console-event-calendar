import java.util.function.*;

/*
 * https://michaelcotterell.com/blog/2015/2/calculus-via-lambdas-in-java-8
 */

public class Integral {
    private int info;

    /**
     * Lambda function
     *
     * @return Function<Double, Double>
     */
    public static Function<Double, Double> square() {
        // Outside the scope of the returned function:
        Function<Double, Double> f = new Function<Double, Double>() {
            @Override
            public Double apply(Double x) {
                return x*x;
            }
        };
        return f;
    }

    public static double integrate() {
        // if n is odd use trapezodial method
        // else use simpsons rule
        return 0.0;
    }

    /**
     * Approximate derivative using the Trapezoidal method.
     *
     * @link https://en.wikipedia.org/wiki/Trapezoidal_rule
     *
     * @param f  Function to evaluate
     * @param b  Upper bound
     * @param a  Lower bound
     * @param n  Number of subintervals
     * @return   The approximate integral as a double
     */
    public static double trapezoidalRule(Function<Double, Double> f, double b, double a, double n) {
        double integral = 0.0;

        double h = (b - a) / n; // where, h is the step length
        double F = 0.5 * f.apply(a);
        for (int i = 1; i <= n; i++) {
            integral += f.apply(a + i*h);
        }
        integral += 0.5 * f.apply(b);
        integral *= h;
        return integral;
    }

    /**
     * Approximate derivative using Simpson's Rule.
     *
     * @link https://en.wikipedia.org/wiki/Simpson%27s_rule
     *
     * @param f  Function to evaluate
     * @param b  Upper bound
     * @param a  Lower bound
     * @param n  Number of subintervals (precision parameter)
     * @return   The approximate integral as a double
     */
    public static double simpsonsRule(Functions.sq f, double b, double a, double n) {
        if (n % 2 != 0) {
            throw new IllegalArgumentException("n must be even.");
        }
        double h = (b - a) / (n - 1);     // step size

        // 1/3 terms
        double approx = (1.0 / 3.0) * (f.apply(a) + f.apply(b));

        // 4/3 terms
        for (int i = 1; i < n - 1; i += 2) {
            double x = a + h * i;
            approx += (4.0 / 3.0) * f.apply(x);
        }

        // 2/3 terms
        for (int i = 2; i < n - 1; i += 2) {
            double x = a + h * i;
            approx += (2.0 / 3.0) * f.apply(x);
        }

        return approx * h;
    }

    /**
     *
     * @link https://en.wikipedia.org/wiki/Adaptive_Simpson%27s_method
     */
    public static double adaptiveSimpsonsRule() {
        double approx = 0.0;

        return approx;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("x^2");
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {

//        new Calculus().trapezoidalRule(f, );

        Function<Double, Double> g = square();
        Functions.sq f = new Functions.sq();

        System.out.println(g);
        System.out.println(g.apply(2.0));

        System.out.println(trapezoidalRule(square(), 0.0, 1.0,4));

//        System.out.println(simpsonsRule(square(), 0.0, 1.0,10000));
        System.out.println(simpsonsRule(f, 0.0, 1.0,10000));

        System.out.println(g.apply(3.0));
        System.out.println(g.apply(4.0));
    }
}