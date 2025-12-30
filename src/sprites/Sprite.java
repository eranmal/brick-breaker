package sprites;

import biuoop.DrawSurface;

/**
 * The {@code Sprite} interface represents a drawable object that is part of a game.
 * It provides methods for drawing the sprite on the screen and updating it over time.
 */
public interface Sprite {

    /**
     * Draws the sprite to the given {@code DrawSurface}.
     * The {@code drawOn} method will be called every time the screen is updated to render the sprite.
     *
     * @param d The {@code DrawSurface} to draw the sprite on.
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies the sprite that time has passed.
     * This method can be used to update the sprite's state over time,
     * such as moving, animating, or checking for collisions.
     */
    void timePassed();
}
