package sprites;

import biuoop.DrawSurface;
import collision.Collidable;
import collision.Velocity;
import game.Game;
import geometry.Point;
import geometry.Rectangle;
import observe.HitListener;
import observe.HitNotifier;
import tools.Tools;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Block} class represents a rectangular block in the game
 * that can be drawn, collide with balls, and interact as a sprite and collidable object.
 */
public class Block extends Rectangle implements Collidable, Sprite, HitNotifier {
    private Color color;
    private List<HitListener> hitListeners = new ArrayList<>();
    private boolean bottomBlock = false;

    /**
     * Constructs a block with a given position, size, and color.
     *
     * @param upperLeft the upper-left point of the block
     * @param width the width of the block
     * @param height the height of the block
     * @param color the color of the block
     * @param isBorder if the block is a paddle
     */
    public Block(Point upperLeft, int width, int height, Color color, boolean isBorder) {
        super(upperLeft, width, height, isBorder);
        this.color = color;
    }
    /**
     * Constructs a block with a given position, size, and color.
     *
     * @param upperLeft the upper-left point of the block
     * @param width the width of the block
     * @param height the height of the block
     * @param color the color of the block
     */
    public Block(Point upperLeft, int width, int height, Color color) {
        super(upperLeft, width, height);
    }
    /**
     * Returns the rectangle that defines the block's collision area.
     *
     * @return the block's collision rectangle
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this;
    }

    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
    /**
     * Handles the collision with a ball. Reverses velocity based on the side of collision.
     *
     * @param collisionPoint the point where the collision occurred
     * @param currentVelocity the ball's velocity before the collision
     * @return the new velocity after the collision
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        boolean changedVelocity = false;
        double safety = 0.3;

        if (Tools.doubleEquals(collisionPoint.getX(), this.getUpperLeft().getX(), safety)
                || Tools.doubleEquals(collisionPoint.getX(), (this.getUpperLeft().getX() + getWidth()), safety)) {
            dx = -dx;
            changedVelocity = true;
        }

        if (Tools.doubleEquals(collisionPoint.getY(), this.getUpperLeft().getY(), safety)
                || Tools.doubleEquals(collisionPoint.getY(), (this.getUpperLeft().getY() + getHeight()), safety)) {
            dy = -dy;
            changedVelocity = true;
        }

        if (!changedVelocity) {
            dx = -dx;
            dy = -dy;
        }
        if (bottomBlock) {
            this.notifyHit(hitter);
        } else if (!this.isBorder() && !ballColorMatch(hitter)) {
            hitter.setColor(color);
            this.notifyHit(hitter);
        }
        return new Velocity(dx, dy);
    }

    /**
     * Draws the block on the given drawing surface.
     *
     * @param d the drawing surface to draw the block on
     */
    @Override
    public void drawOn(DrawSurface d) {
        if (color == null) {
            color = Color.BLACK;
        }
        d.setColor(this.color);
        int pointX = (int) getUpperLeft().getX();
        int pointY = (int) getUpperLeft().getY();
        d.fillRectangle(pointX, pointY, (int) this.getWidth(), (int) this.getHeight());
        d.setColor(Color.BLACK);
        d.drawRectangle(pointX, pointY, (int) this.getWidth(), (int) this.getHeight());
    }

    /**
     * Notifies the block that time has passed. No action needed for static blocks.
     */
    @Override
    public void timePassed() {
        // Static block does not change over time
    }

    /**
     * Adds the block to the game as both a collidable and a sprite.
     *
     * @param g the game to add the block to
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
    /**
     * Removes the block from the game as both a collidable and a sprite.
     *
     * @param game the game to remove the block from
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * check if this block color equal to the ball color.
     * @param ball check with this color ball
     * @return true if they have the same color
     */
    public boolean ballColorMatch(Ball ball) {
        return ball.getColor() == this.color;
    }
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
    @Override
    public Boolean isBlock() {
        return true;
    }
    /**
     * Sets whether this block is the bottom block in the game (used for ball removal logic).
     *
     * @param bottomBlock {@code true} if this is the bottom block, {@code false} otherwise
     */
    public void setBottomBlock(boolean bottomBlock) {
        this.bottomBlock = bottomBlock;
    }

    /**
     * Checks if this block is designated as the bottom block.
     *
     * @return {@code true} if this is the bottom block, {@code false} otherwise
     */
    public boolean isBottomBlock() {
        return bottomBlock;
    }

    /**
     * Returns the color of the block.
     *
     * @return the {@link Color} of the block
     */
    public Color getColor() {
        return color;
    }

}
