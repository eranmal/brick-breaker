package sprites;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

/**
 * The {@code SpriteCollection} class is responsible for managing a collection of sprites in the game.
 * It provides methods to add sprites to the collection, notify all sprites of time passing,
 * and draw all the sprites onto a {@code DrawSurface}.
 */
public class SpriteCollection {
    private List<Sprite> sprites = new LinkedList<>();

    /**
     * Adds a sprite to the collection.
     * If the sprite is not null, it will be added to the list of sprites.
     *
     * @param s The sprite to add to the collection.
     */
    public void addSprite(Sprite s) {
        if (s != null) {
            sprites.add(s);
        }
    }
    /**
     * Removes a sprite from the collection.
     * @param s The sprite to remove from the collection.
     */
    public void removeSprite(Sprite s) {
        sprites.remove(s);
    }

    /**
     * Notifies all sprites in the collection that time has passed.
     * This method is typically called to update the state of all sprites at regular intervals,
     * such as for movement, animation, or game logic updates.
     */
    public void notifyAllTimePassed() {
        List<Sprite> copy = new ArrayList<>(this.sprites);
        for (Sprite sprite : copy) {
            sprite.timePassed();
        }
    }

    /**
     * Draws all sprites onto the given {@code DrawSurface}.
     * This method iterates through the collection of sprites and calls their {@code drawOn} method
     * to render them on the screen.
     *
     * @param d The {@code DrawSurface} to draw all sprites on.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : sprites) {
            s.drawOn(d);
        }
    }
}
