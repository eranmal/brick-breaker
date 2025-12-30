package sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collision.Collidable;
import collision.Velocity;
import game.Game;
import geometry.Point;
import geometry.Rectangle;
import tools.Tools;

import java.awt.Color;

/**
 * The Paddle class represents a paddle in the game, which can be moved left or right by the player.
 * It implements the Sprite and Collidable interfaces, allowing it to be drawn on the screen and interact with
 * other game objects like balls. The paddle also handles collision detection and adjusts the ball's velocity
 * based on the area of impact.
 */
public class Paddle extends Rectangle implements Sprite, Collidable {
    private final KeyboardSensor keyboard;
    private final int speed = 9;
    private final int screenWidth = 800;

    /**
     * Constructs a Paddle object.
     *
     * @param upperLeft The upper-left corner of the paddle.
     * @param width The width of the paddle.
     * @param height The height of the paddle.
     * @param keyboard The keyboard sensor to detect player input.
     * @param isBorder indicate for the constructor
     */
    public Paddle(Point upperLeft, int width, int height, KeyboardSensor keyboard, boolean isBorder) {
        super(upperLeft, width, height, isBorder);
        this.keyboard = keyboard;
    }

    /**
     * Moves the paddle left by a fixed speed. The paddle wraps around to the right edge if it goes off the screen.
     */
    public void moveLeft() {
        double newX = this.getUpperLeft().getX() - speed;
        if (newX + this.getWidth() < 0) {
            newX = this.screenWidth;
        }
        Point newUpperLeft = new Point(newX, this.getUpperLeft().getY());
        this.setRect(newUpperLeft, this.getWidth(), this.getHeight());
    }

    /**
     * Moves the paddle right by a fixed speed. The paddle wraps around to the left edge if it goes off the screen.
     */
    public void moveRight() {
        double newX = this.getUpperLeft().getX() + speed;
        if (newX > this.screenWidth) {
            newX = -this.getWidth();
        }
        Point newUpperLeft = new Point(newX, this.getUpperLeft().getY());
        this.setRect(newUpperLeft, this.getWidth(), this.getHeight());
    }

    /**
     * Updates the paddle's position based on player input. If the left arrow key is pressed, the paddle moves left,
     * and if the right arrow key is pressed, the paddle moves right.
     */
    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * Draws the paddle on the given DrawSurface.
     *
     * @param d The DrawSurface object where the paddle will be drawn.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.DARK_GRAY);
        int x = (int) this.getUpperLeft().getX();
        int y = (int) this.getUpperLeft().getY();
        int width = (int) this.getWidth();
        int height = (int) this.getHeight();
        d.fillRectangle(x, y, width, height);
    }

    /**
     * Returns the rectangle that represents the paddle for collision detection.
     *
     * @return The collision rectangle of the paddle.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return new Rectangle(this.getUpperLeft(), this.getWidth(), this.getHeight(), this.isBlock());
    }

    /**
     * Calculates the new velocity after the ball hits the paddle.
     * The velocity change depends on the area of the paddle
     * that the ball hits. The paddle is divided into five regions,
     * and the angle of the ball's velocity is altered based
     * on the region of impact.
     *
     * @param collisionPoint The point at which the ball collides with the paddle.
     * @param currentVelocity The current velocity of the ball before the collision.
     * @return The new velocity of the ball after the collision.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double speed = currentVelocity.getSpeed();
        Rectangle rect = getCollisionRectangle();
        double epsilon = 0.1;
        if (Tools.doubleEquals(collisionPoint.getY(), rect.getUpperLeft().getY(), epsilon)) {
            double paddleStart = rect.getUpperLeft().getX();
            double relativeHitPoint = collisionPoint.getX() - paddleStart;
            double regionSize = rect.getWidth() / 5;
            int region = (int) (relativeHitPoint / regionSize);
            region = Math.max(0, Math.min(4, region));
            return switch (region) {
                case 0 -> Velocity.fromAngleAndSpeed(210, speed);
                case 1 -> Velocity.fromAngleAndSpeed(240, speed);
                case 2 -> new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
                case 3 -> Velocity.fromAngleAndSpeed(300, speed);
                case 4 -> Velocity.fromAngleAndSpeed(330, speed);
                default -> Velocity.fromAngleAndSpeed(currentVelocity.getDx(), currentVelocity.getDy());
            };
        }
        if (Tools.doubleEquals(collisionPoint.getY(), rect.getUpperLeft().getY() + rect.getHeight(), epsilon)) {
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }
        if (Tools.doubleEquals(collisionPoint.getX(), rect.getUpperLeft().getX(), epsilon)) {
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
        if (Tools.doubleEquals(collisionPoint.getX(), rect.getUpperLeft().getX() + rect.getWidth(), epsilon)) {
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
        return new Velocity(currentVelocity.getDx(), -Math.abs(currentVelocity.getDy()));
    }


    /**
     * Adds this paddle to the game, registering it as both a collidable and a sprite.
     *
     * @param g The game to which the paddle will be added.
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
}
