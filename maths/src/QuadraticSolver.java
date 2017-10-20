import java.text.DecimalFormat;

/**
 * Quadratic Equation Root Solver
 *
 * Brian Teachman
 * 10/19/2017
 */
/*
Example output:

    quadratic(1, -7, 12) = {3, 4}
    quadratic(1, -3, 2) = (1, 2)
    quadratic(2, -4, -3) = (-0.58, 2.58)
    quadratic(1, 3, -4) = (-4, 1}

Example usage:

    try {
        QuadraticSolver qs = new QuadraticSolver(1, -7, 12);
        double[] roots = qs.getRoots();
        System.out.printf("Given: (%.2f)x^2 + (%.2f)x + (%.2f) = 0\n", a, b, c);
        System.out.printf("x = %.2f, %.2f\n\n", x1, x2);
    }
    catch (ArithmeticException e) {
        System.out.print(e.getMessage());
    }
*/
public class QuadraticSolver {
    protected Double a, b, c;
    private int precision = 2;
    private DecimalFormat df;

    QuadraticSolver(double a, double b, double c, int precision) {
        setCoefficients(a, b, c);
        this.precision = precision;
    }

    QuadraticSolver(double a, double b, double c) {
        setCoefficients(a, b, c);
        setStringFormatPrecision(2);
    }
    QuadraticSolver() {
        setStringFormatPrecision(2);
    }

    public double[] findRoots(double a, double b, double c) throws ArithmeticException {
        // https://en.wikipedia.org/wiki/Quadratic_equation#Discriminant
        double discriminant = b*b -  4*a*c;
        double x1 = (-b - Math.sqrt(discriminant)) / (2*a);
        double x2 = (-b + Math.sqrt(discriminant)) / (2*a);
        if (discriminant >= 0) {
            return rootsWithCorrectPrecision(x1, x2);
        }
        else { // im ignoring the double root case as the above covers it
            throw new ArithmeticException("The roots are imaginary");
            //TODO: implement imaginary roots
        }
    }

    public double[] getRoots() {
        double[] roots = {};
        try {
            checkCoefficients();
            roots = findRoots(this.a, this.b, this.c);
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return roots;
    }

    private void checkCoefficients() throws RuntimeException {
        if ( this.a == null || this.b == null || this.c == null) {
            throw new RuntimeException("a, b, and c need to be set.");
        }
    }

    public void setCoefficients(double a, double b, double c) {
        this.a = a; this.b = b; this.c = c;
    }

    /**
     * Set two decimals to a given precision and returns them in an array,
     * in the order received
     *
     * @param x1
     * @param x2
     * @return double[]
     */
    public double[] rootsWithCorrectPrecision(double x1, double x2) {
        double scalar = Math.pow(10, this.precision);
        x1 = ((long) (x1 * scalar)) / scalar; // round to precision
        x2 = ((long) (x2 * scalar)) / scalar;
        return new double[]{x1, x2};
    }
    public double[] rootsWithCorrectPrecision(double x1, double x2, int precision) {
        double scalar = Math.pow(10, precision);
        x1 = ((long) (x1 * scalar)) / scalar; // round to precision
        x2 = ((long) (x2 * scalar)) / scalar;
        return new double[]{x1, x2};
    }

    private void setStringFormatPrecision(int precision) {
        String pattern = "#";
        if (precision > 0) pattern += ".";
        for (int i=0; i < precision; i++) {
            pattern += "#";
        }
        df = new DecimalFormat(pattern);
    }
}
