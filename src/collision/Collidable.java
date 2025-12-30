package collision;

import geometry.Point;
import geometry.Rectangle;
import sprites.Ball;

/**
 * The {@code Collidable} interface should be implemented by any object
 * that can be collided with in the game, such as blocks or borders.
 */
public interface Collidable {

    /**
     * Returns the shape that defines the object's collision boundaries.
     *
     * @return the collision rectangle of the object
     */
    Rectangle getCollisionRectangle();

    /**
     * Notifies the object that a collision has occurred at a specific point with a given velocity.
     * The method should return the new velocity expected after the hit (e.g., reflecting off a surface).
     * @param hitter the ball that hit
     * @param collisionPoint the point at which the collision occurred
     * @param currentVelocity the current velocity of the object that hit this one
     * @return the new velocity after the collision
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
    /**
     *
     * @return default false and only the block class do override to true
     */
    default Boolean isBlock() {
        return false;
    }
}
