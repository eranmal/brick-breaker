package collision;

import geometry.Point;

/**
 * The {@code Velocity} class represents a 2D velocity vector (dx, dy),
 * and provides methods to manipulate and apply it. A velocity vector is typically
 * used to describe the motion of an object in 2D space.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructs a new Velocity object with the given change in the X direction (dx)
     * and the change in the Y direction (dy).
     *
     * @param dx the change in the X direction (horizontal component of velocity)
     * @param dy the change in the Y direction (vertical component of velocity)
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Creates a new Velocity from a given angle and speed. The angle is measured in degrees,
     * with 0 degrees being along the positive X-axis. The method calculates the corresponding
     * horizontal (dx) and vertical (dy) components based on the given angle and speed.
     *
     * @param angle the angle in degrees, measured from the positive X-axis
     * @param speed the speed (magnitude) of the velocity
     * @return a new Velocity object representing the given angle and speed
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        angle = Math.toRadians(angle); // Convert angle to radians
        double dx = speed * Math.cos(angle);
        double dy = speed * Math.sin(angle);
        return new Velocity(dx, dy);
    }

    /**
     * Returns the speed (magnitude) of the velocity. This is calculated using the Pythagorean theorem
     * as the square root of the sum of the squares of dx and dy.
     *
     * @return the speed (magnitude) of the velocity
     */
    public double getSpeed() {
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Returns the horizontal component (dx) of the velocity.
     *
     * @return the horizontal component of the velocity (dx)
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Returns the vertical component (dy) of the velocity.
     *
     * @return the vertical component of the velocity (dy)
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Reverses the direction of the horizontal velocity component (dx).
     * This method is useful when the object changes direction horizontally.
     */
    public void oppositeDx() {
        this.dx = (-1) * this.dx;
    }

    /**
     * Reverses the direction of the vertical velocity component (dy).
     * This method is useful when the object changes direction vertically.
     */
    public void oppositeDy() {
        this.dy = (-1) * this.dy;
    }

    /**
     * Applies the velocity to a given point, resulting in a new point.
     * The new point is obtained by adding the horizontal component (dx) to the X-coordinate
     * and the vertical component (dy) to the Y-coordinate of the original point.
     *
     * @param p the point to apply the velocity to
     * @return a new point with the updated position (x + dx, y + dy)
     */
    public Point applyToPoint(Point p) {
        return new Point((p.getX() + this.dx), (p.getY() + this.dy));
    }
}
