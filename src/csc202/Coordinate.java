package csc202;

public class Coordinate {

    private double x;
    private double y;

    Coordinate() {
    }

    Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distanceTo(Coordinate other) {
        return Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
    }

    @Override
    public String toString() {
        return "\nX-Coordinate: " + x
                + "\nY-Coordinate: " + y + "\n";
    }
}
