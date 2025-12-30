package geometry;

import tools.Tools;

import java.util.List;
/**
 * The {@code Line} class represents a line segment defined by two points: a start and an end point.
 * It provides methods for calculating the line's properties, such as length, middle point,
 * intersections with other lines, and distances from points.
 */
public class Line {
    private final Point start;
    private final Point end;
    /**
     * Constructs a line segment using two {@code Point} objects.
     *
     * @param start the start point of the line
     * @param end the end point of the line
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructs a line segment using coordinates for the start and end points.
     *
     * @param x1 the x-coordinate of the start point
     * @param y1 the y-coordinate of the start point
     * @param x2 the x-coordinate of the end point
     * @param y2 the y-coordinate of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Returns the length of the line.
     *
     * @return the length of the line
     */
    public double length() {
        return this.start.distance(end);
    }

    /**
     * Finds the middle point of the line.
     *
     * @return the middle point of the line
     */
    public Point middle() {
        if (this.start.equals(this.end())) {
            return new Point(this.start.getX(), this.start.getY());
        }
        double xm = (this.start.getX() + this.end.getX()) / 2;
        double ym = (this.start.getY() + this.end.getY()) / 2;
        return new Point(xm, ym);
    }

    /**
     * Returns the start point of the line.
     *
     * @return the start point of the line
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the end point of the line.
     *
     * @return the end point of the line
     */
    public Point end() {
        return this.end;
    }

    /**
     * Checks if this line has overlap with another line.
     *
     * @param other the other line to check for overlap
     * @return true if the lines overlap, false otherwise
     */
    private boolean hasOverlap(Line other) {
        return Math.max(start.getX(), end.getX()) >= Math.min(other.start.getX(), other.end.getX())
                && Math.min(start.getX(), end.getX()) <= Math.max(other.start.getX(), other.end.getX())
                && Math.max(start.getY(), end.getY()) >= Math.min(other.start.getY(), other.end.getY())
                && Math.min(start.getY(), end.getY()) <= Math.max(other.start.getY(), other.end.getY());
    }

    /**
     * Checks if this line intersects with two other lines.
     *
     * @param other1 the first line to check for intersection
     * @param other2 the second line to check for intersection
     * @return true if this line intersects both lines, false otherwise
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return isIntersecting(other1) && isIntersecting(other2)
                && intersectionWith(other1) != null && intersectionWith(other2) != null;
    }

    /**
     * Checks if this line intersects with another line.
     *
     * @param other the other line to check for intersection
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        if (Double.isNaN(this.lineSlope()) && Double.isNaN(other.lineSlope())
                && Tools.doubleEquals(this.start.getX(), other.start.getX())) {
            double thisMinY = Math.min(this.start.getY(), this.end.getY());
            double thisMaxY = Math.max(this.start.getY(), this.end.getY());
            double otherMinY = Math.min(other.start.getY(), other.end.getY());
            double otherMaxY = Math.max(other.start.getY(), other.end.getY());
            return (thisMaxY >= otherMinY) && (thisMinY <= otherMaxY);
        }
        if (Tools.doubleEquals(this.lineSlope(), other.lineSlope())
                && Tools.doubleEquals(this.lineIntercept(), other.lineIntercept())) {
            return hasOverlap(other);
        }
        Point intersection = this.intersectionWith(other);
        return intersection != null;
    }

    /**
     * Calculates the slope of the line.
     *
     * @return the slope of the line, or {@code Double.NaN} if the line is vertical
     */
    public double lineSlope() {
        if (Tools.doubleEquals(end.getX(), start.getX())) {
            return Double.NaN;
        }
        return (start.getY() - end.getY()) / (start.getX() - end.getX());
    }

    /**
     * Calculates the y-intercept of the line.
     *
     * @return the y-intercept of the line, or {@code Double.NaN} if the line is vertical
     */
    public double lineIntercept() {
        double m = this.lineSlope();
        if (Double.isNaN(m)) {
            return Double.NaN;
        }
        return start.getY() - m * start.getX();
    }

    /**
     * Calculates the intersection point of this line with another line.
     *
     * @param other the other line to find the intersection with
     * @return the intersection point, or {@code null} if the lines do not intersect
     */
    public Point intersectionWith(Line other) {
        double m1 = this.lineSlope(), m2 = other.lineSlope();

        if ((Tools.doubleEquals(m1, m2) && Tools.doubleEquals(this.lineIntercept(), other.lineIntercept()))
                || (Double.isNaN(m1) && Double.isNaN(m2)
                && Tools.doubleEquals(this.start.getX(), other.start.getX()))) {
            return null;
        }

        double b1 = this.lineIntercept(), b2 = other.lineIntercept();
        double intersectX, intersectY;

        if (Double.isNaN(m1)) {
            intersectX = this.start.getX();
            intersectY = m2 * intersectX + b2;
        } else if (Double.isNaN(m2)) {
            intersectX = other.start.getX();
            intersectY = m1 * intersectX + b1;
        } else {
            if (Tools.doubleEquals(m1, m2)) {
                return null;
            }
            intersectX = (b2 - b1) / (m1 - m2);
            intersectY = m1 * intersectX + b1;
        }

        Point intersection = new Point(intersectX, intersectY);
        if (isNotInBounds(intersection, other)
                || isNotInBounds(intersection, this)) {
            return null;
        }
        return intersection;
    }

    /**
     * Checks if the given point is within the bounds of the line segment.
     *
     * @param p the point to check
     * @param line the line to check against
     * @return true if the point is within bounds, false otherwise
     */
    public boolean isNotInBounds(Point p, Line line) {
        double minX = Math.min(line.start().getX(), line.end().getX());
        double maxX = Math.max(line.start().getX(), line.end().getX());
        double minY = Math.min(line.start().getY(), line.end().getY());
        double yMax = Math.max(line.start().getY(), line.end().getY());

        return (!Tools.doubleEquals(p.getX(), minX) && !Tools.doubleEquals(p.getX(), maxX)
                && (!(p.getX() > minX) || !(p.getX() < maxX)))
                || (!Tools.doubleEquals(p.getY(), minY) && !Tools.doubleEquals(p.getY(), yMax)
                && (!(p.getY() > minY) || !(p.getY() < yMax)));
    }

    /**
     * Checks if this line is equal to another line.
     *
     * @param other the other line to compare
     * @return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        return (this.start.equals(other.start) && this.end.equals(other.end))
                || (this.start.equals(other.end) && this.end.equals(other.start));
    }

    /**
     * Checks if this line is collinear with another line.
     *
     * @param other the other line to check for collinearity
     * @return true if the lines are collinear, false otherwise
     */
    public boolean linesCollinear(Line other) {
        return Tools.doubleEquals(this.lineSlope(), other.lineSlope()) && hasOverlap(other);
    }

    /**
     * Calculates the distance from a point to this line.
     *
     * @param p the point to calculate the distance to
     * @return the distance from the point to the line
     */
    public double distanceFromPoint(Point p) {
        double x1 = this.start.getX();
        double y1 = this.start.getY();
        double x2 = this.end.getX();
        double y2 = this.end.getY();

        double a = y2 - y1;
        double b = x1 - x2;
        double c = (x2 * y1) - (y2 * x1);

        return Math.abs(a * p.getX() + b * p.getY() + c) / Math.sqrt(a * a + b * b);
    }

    /**
     * Finds the closest point on this line to a given point.
     *
     * @param p the point to find the closest point to
     * @return the closest point on the line to the given point
     */
    public Point closestPointTo(Point p) {
        double x1 = this.start().getX();
        double y1 = this.start().getY();
        double x2 = this.end().getX();
        double y2 = this.end().getY();

        double px = p.getX();
        double py = p.getY();
        double dx = x2 - x1;
        double dy = y2 - y1;

        if (dx == 0 && dy == 0) {
            return new Point(x1, y1);
        }
        double t = ((px - x1) * dx + (py - y1) * dy) / (dx * dx + dy * dy);
        t = Math.max(0, Math.min(1, t));
        double closestX = x1 + t * dx;
        double closestY = y1 + t * dy;

        return new Point(closestX, closestY);
    }

    /**
     * Finds the closest intersection point between this line and a rectangle.
     *
     * @param rect the rectangle to find the intersection with
     * @return the closest intersection point to the start of the line, or {@code null} if there is no intersection
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        double[] length = new double[2];
        List<Point> intersectionPoints = rect.intersectionPoints(this);
        if (intersectionPoints.isEmpty()) {
            return null;
        }
        if (intersectionPoints.size() == 1) {
            return intersectionPoints.get(0);
        }
        length[0] = this.start().distance(intersectionPoints.get(0));
        length[1] = this.start().distance(intersectionPoints.get(1));
        return length[0] < length[1] ? intersectionPoints.get(0) : intersectionPoints.get(1);
    }
}
