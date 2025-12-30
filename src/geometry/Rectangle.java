package geometry;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Rectangle} class represents a rectangular area defined by its minimum and maximum X and Y coordinates,
 * or by its upper-left corner and dimensions (width and height).
 * This class includes methods for getting the boundaries of the rectangle, checking overlap with a circle,
 * generating the edges of the rectangle as lines, and determining intersection points with lines.
 */
public class Rectangle {
    private int minX, maxX, minY, maxY;
    private Point upperLeft;
    private double width;
    private double height;
    private Color color;
    private boolean isBorder;

    /**
     * Constructs a rectangle using the given minimum and maximum X and Y coordinates.
     *
     * @param minX The minimum X coordinate of the rectangle.
     * @param maxX The maximum X coordinate of the rectangle.
     * @param minY The minimum Y coordinate of the rectangle.
     * @param maxY The maximum Y coordinate of the rectangle.
     */
    public Rectangle(int minX, int maxX, int minY, int maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    /**
     * Constructs a rectangle using the upper-left corner and the width and height.
     *
     * @param upperLeft The upper-left corner of the rectangle.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param isBorder indicate if the block is border
     */
    public Rectangle(Point upperLeft, double width, double height, boolean isBorder) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.isBorder = isBorder;

        this.minX = (int) upperLeft.getX();
        this.maxX = (int) (upperLeft.getX() + width);
        this.minY = (int) upperLeft.getY();
        this.maxY = (int) (upperLeft.getY() + height);
    }
    /**
     * Constructs a rectangle using the upper-left corner and the width and height.
     *
     * @param upperLeft The upper-left corner of the rectangle.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;

        this.minX = (int) upperLeft.getX();
        this.maxX = (int) (upperLeft.getX() + width);
        this.minY = (int) upperLeft.getY();
        this.maxY = (int) (upperLeft.getY() + height);
    }

    /**
     * Constructs a square using the given minimum and maximum X and Y coordinates.
     * The square is created by assuming the given X coordinates represent both the minimum and maximum for the
     * square's sides.
     *
     * @param minX The minimum X coordinate of the square.
     * @param maxX The maximum X coordinate of the square.
     */
    public Rectangle(int minX, int maxX) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minX;
        this.maxY = maxX;
    }

    /**
     *
     * @return true if the rectangle is border.
     */
    public boolean isBorder() {
        return isBorder;
    }
    /**
     * Getter for the minimum X coordinate.
     *
     * @return The minimum X coordinate of the rectangle.
     */
    public int getMinX() {
        return this.minX;
    }

    /**
     * Getter for the maximum X coordinate.
     *
     * @return The maximum X coordinate of the rectangle.
     */
    public int getMaxX() {
        return this.maxX;
    }

    /**
     * Getter for the minimum Y coordinate.
     *
     * @return The minimum Y coordinate of the rectangle.
     */
    public int getMinY() {
        return this.minY;
    }

    /**
     * Getter for the maximum Y coordinate.
     *
     * @return The maximum Y coordinate of the rectangle.
     */
    public int getMaxY() {
        return this.maxY;
    }

    /**
     * Checks if a circle with the given center and radius overlaps with the rectangle.
     *
     * @param centerX The X coordinate of the circle's center.
     * @param centerY The Y coordinate of the circle's center.
     * @param radius The radius of the circle.
     * @return {@code true} if the circle overlaps with the rectangle, {@code false} otherwise.
     */
    public boolean isOverLap(double centerX, double centerY, int radius) {
        return centerX - radius < this.getMaxX()
                && centerX + radius > this.getMinX()
                && centerY - radius < this.getMaxY()
                && centerY + radius > this.getMinY();
    }

    /**
     * Creates and returns the left edge line of the rectangle.
     *
     * @return The left edge line of the rectangle.
     */
    public Line leftLine() {
        return new Line(this.getMinX(), this.getMinY(), this.getMinX(), this.getMaxY());
    }

    /**
     * Creates and returns the right edge line of the rectangle.
     *
     * @return The right edge line of the rectangle.
     */
    public Line rightLine() {
        return new Line(this.getMaxX(), this.getMinY(), this.getMaxX(), this.getMaxY());
    }

    /**
     * Creates and returns the top edge line of the rectangle.
     *
     * @return The top edge line of the rectangle.
     */
    public Line topLine() {
        return new Line(this.getMaxX(), this.getMaxY(), this.getMinX(), this.getMaxY());
    }

    /**
     * Creates and returns the bottom edge line of the rectangle.
     *
     * @return The bottom edge line of the rectangle.
     */
    public Line bottomLine() {
        return new Line(this.getMinX(), this.getMinY(), this.getMaxX(), this.getMinY());
    }

    /**
     * Getter for the width of the rectangle.
     *
     * @return The width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Getter for the height of the rectangle.
     *
     * @return The height of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Getter for the upper-left corner of the rectangle.
     *
     * @return The upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Sets the rectangle's upper-left corner, width, and height.
     *
     * @param upperLeft The new upper-left point of the rectangle.
     * @param width The new width of the rectangle.
     * @param height The new height of the rectangle.
     */
    public void setRect(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns a list of intersection points where a given line intersects with the rectangle's edges.
     *
     * @param line The line to check for intersection with the rectangle's edges.
     * @return A list of intersection points (can be empty if there are no intersections).
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersectionPoints = new ArrayList<>();
        Point a = line.intersectionWith(this.rightLine());
        if (a != null) {
            intersectionPoints.add(a);
        }
        Point b = line.intersectionWith(this.leftLine());
        if (b != null) {
            intersectionPoints.add(b);
        }
        Point c = line.intersectionWith(this.topLine());
        if (c != null) {
            intersectionPoints.add(c);
        }
        Point d = line.intersectionWith(this.bottomLine());
        if (d != null) {
            intersectionPoints.add(d);
        }
        return intersectionPoints;
    }

    /**
     * Sets the color of the rectangle.
     *
     * @param color The color to set for the rectangle.
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
