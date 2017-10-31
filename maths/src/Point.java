public class Point {

    private double x;
    private double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    Point(int x, int y) {
        this.x = (double) x;
        this.y = (double) y;
    }
    Point() {
        this.x = 0.0;
        this.y = 0.0;
    }

    public void translate(double dx, double dy) {
        this.x += dx;
        this.y += dy;

    }

    public double distanceFromOrigin() {
        return Math.sqrt(this.x*this.x + this.y*this.y);
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String toString() {
        return "("+this.getX()+", "+this.getY()+")";
    }
}
