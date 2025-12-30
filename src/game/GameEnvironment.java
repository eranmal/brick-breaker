package game;

import collision.Collidable;
import collision.CollisionInfo;
import geometry.Line;
import geometry.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code GameEnvironment} class holds a collection of collidable objects.
 * It is responsible for handling collision detection between a moving object
 * (defined by a trajectory line) and the objects in the environment.
 */
public class GameEnvironment {
    private final List<Collidable> collidablesObj = new ArrayList<>();
    /**
     * get the callable objects list.
     * @return the list
     */
    public List<Collidable> getCollidablesObj() {
        return collidablesObj;
    }
    /**
     * Adds a collidable object to the environment.
     *
     * @param c the collidable to add
     */
    public void addCollidable(Collidable c) {
        if (c != null) {
            collidablesObj.add(c);
        }
    }
    /**
     * Removes a collidable object to the environment.
     *
     * @param c the collidable to remove
     */
    public void removeCollidable(Collidable c) {
        collidablesObj.remove(c);
    }
    /**
     * Returns information about the closest collision that is going to occur
     * if an object moves along the given trajectory.
     *
     * @param trajectory the path along which an object is moving
     * @return a {@link CollisionInfo} object describing the closest collision,
     *         or {@code null} if no collision will occur
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestPoint = null;
        Collidable closestCollidable = null;
        double minDistance = 400; // Arbitrary large distance to start comparison

        for (Collidable collidable : collidablesObj) {
            Point intersection = trajectory.closestIntersectionToStartOfLine(collidable.getCollisionRectangle());
            if (intersection != null) {
                double distance = trajectory.start().distance(intersection);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPoint = intersection;
                    closestCollidable = collidable;
                }
            }
        }

        if (closestPoint == null) {
            return null;
        }
        return new CollisionInfo(closestCollidable, closestPoint);
    }
}
