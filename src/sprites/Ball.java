package sprites;

import biuoop.DrawSurface;
import collision.Collidable;
import collision.CollisionInfo;
import collision.Velocity;
import game.GameEnvironment;
import game.Game;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import observe.HitListener;

import java.awt.Color;

/**
 * The {@code Ball} class represents a 2D ball that can move with a given velocity
 * inside a defined rectangular area. It can detect collisions and interact with the environment.
 */
public class Ball implements Sprite, HitListener {
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity velocity;
    private Line[] forbiddenLines;
    private GameEnvironment gameEnvir;
    private Point initialPosition;
    private int screenWidth;
    private int screenHeight;
    private int borderWidth;

    /**
     * Constructs a new Ball with a center point, radius, and color.
     *
     * @param center the center point of the ball
     * @param r the radius of the ball
     * @param color the color of the ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.initialPosition = center;
    }

    /**
     * Constructs a new Ball with x, y coordinates, radius, and color.
     *
     * @param x the x-coordinate of the ball's center
     * @param y the y-coordinate of the ball's center
     * @param r the radius of the ball
     * @param color the color of the ball
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        this.center = new Point(x, y);
        this.radius = r;
        this.color = color;
    }

    /**
     * Sets the game environment for collision detection.
     *
     * @param gameEnvir the game environment
     */
    public void setGameEnvironment(GameEnvironment gameEnvir) {
        this.gameEnvir = gameEnvir;
    }

    /**
     * Sets the borders for the area in which the ball is allowed to move.
     *
     * @param width the width of the screen
     * @param height the height of the screen
     * @param borderWidth the width of the borders to respect
     */
    public void setBorders(int width, int height, int borderWidth) {
        this.screenWidth = width;
        this.screenHeight = height;
        this.borderWidth = borderWidth;
    }

    /**
     * Returns the x-coordinate (rounded) of the ball's center.
     *
     * @return the x-coordinate as an integer
     */
    public int getX() {
        double x = Math.round(center.getX());
        return (int) x;
    }

    /**
     * Returns the y-coordinate (rounded) of the ball's center.
     *
     * @return the y-coordinate as an integer
     */
    public int getY() {
        double y = Math.round(center.getY());
        return (int) y;
    }

    /**
     * Returns the radius of the ball.
     *
     * @return the ball's radius
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * Returns the color of the ball.
     *
     * @return the ball's color
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Returns the line representing the ball's current movement vector.
     *
     * @return the ball's projected movement as a line
     */
    public Line getGuidedLine() {
        Point nextCenter = new Point(center.getX() + this.velocity.getDx(),
                center.getY() + this.velocity.getDy());
        return new Line(this.center, nextCenter);
    }

    /**
     * Draws the ball on the given drawing surface.
     *
     * @param surface the drawing surface to draw on
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        int x = (int) Math.round(this.center.getX());
        int y = (int) Math.round(this.center.getY());
        surface.fillCircle(x, y, this.radius);
    }

    /**
     * Updates the ball's state. Called once per frame.
     */
    @Override
    public void timePassed() {
        moveOneStep();
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param v the new velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Sets the velocity of the ball using dx and dy components.
     *
     * @param dx the change in x
     * @param dy the change in y
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Returns the velocity of the ball.
     *
     * @return the ball's velocity
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Moves the ball one step according to its velocity.
     * Handles collisions with objects and screen borders.
     */
    public void moveOneStep() {
        if (this.velocity == null) {
            return;
        }

        int subSteps = 50;
        double dxStep = this.velocity.getDx() / subSteps;
        double dyStep = this.velocity.getDy() / subSteps;

        for (int i = 0; i < subSteps; i++) {
            Point nextPosition = new Point(this.center.getX() + dxStep, this.center.getY() + dyStep);
            Line nextStepLine = new Line(this.center, nextPosition);
            CollisionInfo collision = this.gameEnvir.getClosestCollision(nextStepLine);

            if (collision == null) {
                this.center = nextPosition;
            } else {
                Point collisionPoint = collision.collisionPoint();
                Collidable object = collision.collisionObject();
                Rectangle rect = object.getCollisionRectangle();

                // Place the ball at the collision point
                this.center = collisionPoint;

                // Update velocity
                this.velocity = object.hit(this, collisionPoint, this.velocity);

                double left = rect.getUpperLeft().getX();
                double right = left + rect.getWidth();
                double top = rect.getUpperLeft().getY();
                double bottom = top + rect.getHeight();

                double ballX = this.center.getX();
                double ballY = this.center.getY();
                double offset = this.radius + 0.1;

                if (Math.abs(ballX - left) <= 1.0) {
                    this.center = new Point(left - offset, ballY); // hit from left
                } else if (Math.abs(ballX - right) <= 1.0) {
                    this.center = new Point(right + offset, ballY); // hit from right
                } else if (Math.abs(ballY - top) <= 1.0) {
                    this.center = new Point(ballX, top - offset); // hit from top
                } else if (Math.abs(ballY - bottom) <= 1.0) {
                    this.center = new Point(ballX, bottom + offset); // hit from bottom
                }

                break;
            }
        }

        // Check for screen boundaries
        if (this.center.getX() < this.borderWidth
                || this.center.getX() > this.screenWidth - this.borderWidth
                || this.center.getY() < this.borderWidth
                || this.center.getY() > this.screenHeight - this.borderWidth) {
            this.center = new Point(initialPosition.getX(), initialPosition.getY());
        }
    }
    /**
     * Sets the array of forbidden lines that the ball must avoid.
     *
     * @param forbiddenLines an array of lines to avoid
     */
    public void setForbiddenLines(Line[] forbiddenLines) {
        this.forbiddenLines = forbiddenLines;
    }

    /**
     * Returns the array of forbidden lines that the ball must avoid.
     *
     * @return array of forbidden lines
     */
    public Line[] getForbiddenLines() {
        return this.forbiddenLines;
    }

    /**
     * Sets the color of the ball.
     *
     * @param color new ball color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Adds the ball to the game as a sprite.
     *
     * @param g the game to add this ball to
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }
    /**
     * Removes the ball from the game as a sprite.
     *
     * @param g the game to remove this ball from
     */
    public void removeFromGame(Game g) {
        g.removeSprite(this);
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.gameEnvir.removeCollidable(beingHit);
    }
}
