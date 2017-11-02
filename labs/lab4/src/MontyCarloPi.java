import java.awt.*;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;

/**
 * Brian Teachman
 * CS 140: Whatcom Community College
 * 10/17/2017
 *
 * lab3: Pi Approximation using the Monty Carlo Method
 *
 * The "Monte Carlo Method" is a method of solving problems using statistics.
 * Given the probability, P, that an event will occur in certain conditions,
 * a computer can be used to generate those conditions repeatedly. The number
 * of times the event occurs divided by the number of times the conditions
 * are generated should be approximately equal to Pi.
 *
 * https://en.wikipedia.org/wiki/Monte_Carlo_method
 */
public class MontyCarloPi {
    final static long NUM_OF_POINTS = (int) 1E10;
    final static int SIDE_LENGTH = 1; // is square (n x n)
    final static double RADIUS = SIDE_LENGTH / 2.0;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        //---------------------------------------------------------------------
        /*
         * Imagine we have a circle of radius R inscribed inside of a
         * square with side length 2R. This will mean that the circles
         * top, bottom and sides intersect with those of the square in
         * the middle of the square, as shown above. Thus the area of
         * the circle will be π*R^2 and the area of the square will be
         * (2R)^2. So the ratio of the area of the circle to the area
         * of the square will be π/4.
         *
         * This means that, if you pick N points at random inside the
         * square, approximately
         *
         * (N * π) / 4
         *
         * of those point fall in the circle.
         *
         * The program should pick points at random inside the square.
         * Then it checks to see if the point is inside the circle
         * (it knows it's inside the circle if x^2 + y^2 < R^2, where
         * x and y are the coordinates of the point picked and R is the
         * radius of the circle). The program should keep track of how
         * many points it has picked so far (N) and how many of those
         * chosen points fell inside the circle (M). With these numbers
         * we can use the following equation.
         *
         * pi is approximated using:
         *
         * pi = (4*M)/N
         *
         * In a comment block at the bottom of the program, place a note
         * that indicates the approximate amount of points needed to get
         * an accurate approximation of pi to 4 decimal places.
         */
        int M = 0; // Number of points that landed in circle
        int N = 0; // Current iteration
        for (int i = 0; i < NUM_OF_POINTS; i++) {
            double x = Math.random()*RADIUS;
            double y = Math.random()*RADIUS;
//            print("<"+x+", "+y+">\n");
            boolean isPointInCircle = (x*x + y*y) < (RADIUS * RADIUS);
            if (isPointInCircle) {
                M++;
            }
            N++;
        }
        System.out.printf("PI = %.4f\n", calcPI(M, N));

        //---------------------------------------------------------------------
        print("\n== DONE =========================\nRuntime: ");
        printRuntime(startTime);
    }

    private static void printRuntime(long startTime) {
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        String units = "ms";
        if (elapsedTime > 1E3) {
            elapsedTime /= 1E3;
            units = "s";
        }
        print("".valueOf(elapsedTime)+units+"\n");
    }

    private static double calcPI(int m, int n) {
        return (4.0 * m) / n; // 3.1416
    }

    public static void print(double num) {
        System.out.println(num);
    }

    public static void print(String stuff) {
        System.out.print(stuff);
    }
}
