package collision;

import geometry.Point;

/**
 * The {@code CollisionInfo} class holds information about a collision that is about to occur.
 * It contains both the point of collision and the object involved.
 */
public class CollisionInfo {
    private final Collidable nextCollidable;
    private final Point nextCollidablePoint;

    /**
     * Constructs a new {@code CollisionInfo} with the specified collidable object and collision point.
     *
     * @param nextCollidable the object that the collision will occur with
     * @param nextCollidablePoint the point at which the collision is expected to happen
     */
    public CollisionInfo(Collidable nextCollidable, Point nextCollidablePoint) {
        this.nextCollidable = nextCollidable;
        this.nextCollidablePoint = nextCollidablePoint;
    }

    /**
     * Returns the point at which the collision occurs.
     *
     * @return the collision point
     */
    public Point collisionPoint() {
        return this.nextCollidablePoint;
    }

    /**
     * Returns the collidable object involved in the collision.
     *
     * @return the object that will be collided with
     */
    public Collidable collisionObject() {
        return this.nextCollidable;
    }
}
