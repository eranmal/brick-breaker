package geometry;

import tools.Tools;

/**
 * this class create point object.
 */
public class Point {
    private double x; // x point
    private double y; // y point
    /**
     * constructor.
     * @param x x point
     * @param y y point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * distance -- return the distance of this point to the other point.
     * @param other check the distance from the other point to this point
     * @return the distance from the other point
     */
    public double distance(Point other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    /**
     * check if the points are equal.
     * @param other check equal with this point
     * @return true if the points equal
     */
    public boolean equals(Point other) {
        if (other == null) {
            return false;
        }
        return Tools.doubleEquals(this.getX(), other.getX())
                && Tools.doubleEquals(this.getY(), other.getY());
    }

    /**
     * return the x value.
     * @return this x
     */
    public double getX() {
        return this.x; }
    /**
     * return the y value.
     * @return this y
     */
    public double getY() {
        return this.y; }

    /**
     *
     * @param x set the x value
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     *
     * @param y set the y value
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * adding to point the next position.
     * @param dx x velocity
     * @param dy y velocity
     * @return the new point with the velocity
     */
    public Point add(double dx, double dy) {
        return new Point(this.x + dx, this.y + dy);
    }
}