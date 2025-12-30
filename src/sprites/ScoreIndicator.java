package sprites;

import biuoop.DrawSurface;
import collision.Counter;
import geometry.Point;

import java.awt.Color;

/**
 * The {@code ScoreIndicator} class is a UI element that displays the current score on the screen.
 * It extends {@link Block} for visual structure and implements {@link Sprite} for game rendering.
 */
public class ScoreIndicator extends Block implements Sprite {
    private Counter score;
    private Color color;

    /**
     * Constructs a {@code ScoreIndicator} at the specified position, with given dimensions and color.
     *
     * @param upperLeft the upper-left corner of the score indicator
     * @param width     the width of the score indicator
     * @param height    the height of the score indicator
     * @param color     the background color of the score indicator
     */
    public ScoreIndicator(Point upperLeft, int width, int height, Color color) {
        super(upperLeft, width, height, color);
        this.color = color;
    }

    /**
     * Sets the counter that holds the current score.
     *
     * @param counter the score counter to display
     */
    public void setCounter(Counter counter) {
        this.score = counter;
    }

    /**
     * Draws the score indicator on the given {@link DrawSurface}.
     *
     * @param d the drawing surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        int pointX = (int) getUpperLeft().getX();
        int pointY = (int) getUpperLeft().getY();
        d.fillRectangle(pointX, pointY, (int) this.getWidth(), (int) this.getHeight());
        d.setColor(Color.BLACK);
        d.drawRectangle(pointX, pointY, (int) this.getWidth(), (int) this.getHeight());
        d.drawText(400, 20, "Score: " + this.score.getValue(), 18);
    }

    /**
     * Called every frame to update the state of the score indicator.
     * This implementation does nothing.
     */
    @Override
    public void timePassed() {
    }

    /**
     * Returns the current score counter.
     *
     * @return the score counter
     */
    public Counter getCounter() {
        return this.score;
    }
}
