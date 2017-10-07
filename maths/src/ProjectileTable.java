// This program computes the trajectory of a projectile.
// Second, structured version.

import java.text.DecimalFormat;
import java.util.Scanner;
import java.awt.Point;

public class ProjectileTable {
    // constant for Earth acceleration in meters/second^2
    public static final double ACCELERATION = -9.81;

    public static double x = 0.0;
    public static double y = 0.0;
    public static double t = 0.0;

    public static double velocity;
    public static double angle;
    public static int steps;

    public static void main(String[] args) {
        giveIntro();
        promptForData();
        printTable();
    }

    // gives a brief introduction to the user
    public static void giveIntro() {
        System.out.println("This program computes the trajectory of a projectile given");
        System.out.println("its initial velocity and its angle relative to the horizontal.");
        System.out.println();
    }

    public static void promptForData() {
        Scanner console = new Scanner(System.in);
        System.out.print("Velocity (in meters/second)? ");
        velocity = console.nextDouble();
        System.out.print("Angle (in degrees)? ");
        angle = Math.toRadians(console.nextDouble());
        System.out.print("Number of steps to display? ");
        steps = console.nextInt();
        System.out.println();
    }

    public static void setPositionVector() {
        if (velocity == 0.0) { // pg. 277
            throw new IllegalArgumentException("This object must be moving.");
        }

        // x = v*cos(theta)
        double xVelocity = velocity * Math.cos(angle);
        // y = v*sin(theta)
        double yVelocity = velocity * Math.sin(angle);

        double totalTime = - 2.0 * yVelocity / ACCELERATION;
        double deltaT = totalTime / steps;
        double deltaX = xVelocity * deltaT;

        t += deltaT;
        x += deltaX;
        y = displacement(yVelocity, t, ACCELERATION);
    }

    // returns the horizontal displacement for a body given
    // initial velocity v, elapsed time t and acceleration a
    public static double displacement(double v, double t, double a) {
        // deltaX = vt+(1/2)at^2
        return v * t + 0.5 * a * t * t;
    }

    // rounds n to 2 digits after the decimal point
    public static double round2(double n) {
        return (int) (n * 100.0 + 0.5) / 100.0;
    }

    public static void printRow(String format, int step_number) {
        setPositionVector();
        DecimalFormat formatter = new DecimalFormat("00.00");
        System.out.printf(format,
                step_number,
                formatter.format(round2(x)),
                formatter.format(round2(y)),
                formatter.format(round2(t)));
    }

    // prints a table showing the trajectory of an object given
    // its initial velocity and angle and including the given
    // number of steps in the table
    public static void printTable() {

        System.out.println("step | x\t\t | y\t\t | time");
        System.out.println("------------------------------------");
        System.out.println("0\t | 0.00\t\t | 0.00\t\t | 0.00");
        for (int i = 1; i <= steps; i++) {
            printRow("%d\t | %s\t | %s\t | %s\n", i);
        }
    }
}
