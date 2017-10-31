/*
 * Brian Teachman
 * 10/7/2017
 *
 * License: http://www.wtfpl.net/txt/copying/
 */

public class Point2DVector {

    public static void main(String[] args) {
        // declare variables and construct objects
        Point p1 = new Point(3, 8);
        Point p2 = new Point();
        Point p3 = p2;

        // see what is in each point initially
        showPointsType(p1, p2, p3);
        System.out.println();

        // three translations
        p1.translate(-1, -2);
        p2.translate(4, 8);
        p3.translate(2, 3);

        // see what is in each point now
        showPointsType(p1, p2, p3);

        System.out.println("-----------------");

        swapElements(p1);
        swapElementSign(p2);
        Point p4 = swapElementSign(p1);

        showPoint(p1);
        showPoint(p2);
        showPoint(p4);
    }

    public static void showPointsType(Point p1, Point p2, Point p3) {
        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);
        System.out.println("p3 = " + p3);
    }

    public static void showPoint(Point p) {
        System.out.printf("(%.2f, %.2f)\n", p.getX(), p.getY());
    }

    public static void swapElements(Point p) {
//        double temp = p.getX();
//        p.setX(p.getY());
//        p.setY(temp);
        p.setLocation(p.getY(), p.getX());
    }

    public static Point swapElementSign(Point p) {
        p = new Point(-1*p.getX(), -1*p.getY());
        return p;
    }
}
