package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import collision.Collidable;
import collision.Counter;
import geometry.Point;
import sprites.SpriteCollection;
import sprites.Sprite;
import sprites.Ball;
import sprites.Paddle;
import sprites.BlockRemover;
import sprites.Block;
import sprites.ScoreIndicator;
import sprites.BallRemover;

import java.awt.Color;

/**
 * The {@code Game} class is responsible for initializing, managing, and running the game.
 * It maintains a collection of sprites and collidables, and handles the game loop.
 * It also renders joyful visual elements such as bright colors and a smiling face.
 */
public class Game {
    private final SpriteCollection sprites = new SpriteCollection();
    private final GameEnvironment environment = new GameEnvironment();
    private Ball[] balls;
    private GUI gui;
    private final Counter blockCounter = new Counter();
    private final Counter ballCounter = new Counter();
    private final Counter scoreCounter = new Counter();
    private BlockRemover blockRemover;
    private BallRemover ballRemover;
    private ScoreIndicator scoreIndicator;
    private ScoreTrackingListener scoreTrackingListener;

    /**
     * Initializes the game environment by adding the paddle, walls, internal blocks,
     * balls, and a decorative smiley face.
     */
    public void initialize() {
        int firstBlockHeight = 150;
        int blockWidth = 50;
        int blockHeight = 30;
        createScoreIndicator(blockHeight);
        createBalls();
        addPaddle();
        addWalls(blockHeight);
        addInsideBlocks(firstBlockHeight, blockWidth, blockHeight);
        for (Ball ball : balls) {
            ball.setGameEnvironment(environment);
        }

        addBalls();
        ballRemover = new BallRemover(this, ballCounter);
        blockRemover = new BlockRemover(this, blockCounter);
        scoreTrackingListener = new ScoreTrackingListener(scoreCounter);

        for (Collidable c : environment.getCollidablesObj()) {
            if (c.isBlock()) {
                Block block = (Block) c;
                if (block.isBottomBlock()) {
                    block.addHitListener(ballRemover);
                } else {
                    block.addHitListener(blockRemover);
                    addListeners(block);
                    block.addHitListener(scoreTrackingListener);
                }
            }
        }
    }

    /**
     * Starts the game loop. It repeatedly draws all game elements, updates them,
     * and refreshes the screen at a fixed frame rate.
     */
    public void run() {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        boolean bonusGiven = false;

        while (ballCounter.getValue() != 0) {
            long startTime = System.currentTimeMillis();
            DrawSurface d = gui.getDrawSurface();
            d.setColor(new Color(240, 255, 255)); // Azure background
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

            if (blockCounter.getValue() == 0) {
                scoreTrackingListener.getCurrentScore().increase(100);
                bonusGiven = true;
            }
            scoreIndicator.setCounter(scoreTrackingListener.getCurrentScore());
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
            if (bonusGiven) {
                System.out.println("You Win!");
                System.out.println("Your score is: " + scoreTrackingListener.getCurrentScore().getValue());
                DrawSurface finalScreen = gui.getDrawSurface();
                finalScreen.setColor(new Color(240, 255, 255));
                finalScreen.fillRectangle(0, 0, finalScreen.getWidth(), finalScreen.getHeight());
                this.sprites.drawAllOn(finalScreen);
                gui.show(finalScreen);
                sleeper.sleepFor(2000);
                break;
            }
        }
        if (!bonusGiven) {
            System.out.println("Game Over.");
            System.out.println("Your score is: " + scoreTrackingListener.getCurrentScore().getValue());
        }

        gui.close();
    }

    /**
     * Creates the balls used in the game with initial position, color, size and velocity.
     */
    public void createBalls() {
        int numOfBalls = 3;
        Ball[] balls1 = new Ball[numOfBalls];
        int m = 350;
        for (int i = 0; i < numOfBalls; i++) {
            Ball ball = new Ball(new Point(400, 450 - m), 5, new Color(255, 230, 200));
            ball.setVelocity(6 + i / 10, 5);
            ball.setBorders(800, 600, 30);
            balls1[i] = ball;
        }
        ballCounter.increase(numOfBalls);
        this.balls = balls1;
    }

    /**
     * Adds all created balls to the game and sets their environment.
     */
    public void addBalls() {
        for (Ball ball : this.balls) {
            ball.addToGame(this);
            ball.setGameEnvironment(environment);
        }
    }

    /**
     * Adds four wall blocks (top, bottom, left, right) to the game to prevent
     * balls and other elements from exiting the screen.
     *
     * @param wallThick the thickness of the wall blocks
     */
    public void addWalls(int wallThick) {
        Color wallColor = new Color(173, 216, 230);
        Block topBorder = new Block(new Point(0, wallThick), 800, wallThick, wallColor, true);
        topBorder.addToGame(this);

        Block rightBorder = new Block(new Point(800 - wallThick, 0), wallThick, 600, wallColor, true);
        rightBorder.addToGame(this);

        Block leftBorder = new Block(new Point(0, 0), wallThick, 600, wallColor, true);
        leftBorder.addToGame(this);

        Block bottomBorder = new Block(new Point(0, 600 - wallThick), 800, wallThick, wallColor, true);
        bottomBorder.addToGame(this);
        bottomBorder.setBottomBlock(true);
    }

    /**
     * Creates and adds a score indicator block at the top of the screen.
     *
     * @param wallThick the height of the score indicator block
     */
    public void createScoreIndicator(int wallThick) {
        scoreIndicator = new ScoreIndicator(new Point(0, 0), 800, wallThick, Color.YELLOW);
        scoreIndicator.addToGame(this);
    }

    /**
     * Adds multiple rows of blocks arranged in a triangular pattern
     * using a bright and cheerful palette.
     *
     * @param blockYPointPlace the initial Y coordinate of the first row
     * @param width            the width of each block
     * @param height           the height of each block
     */
    public void addInsideBlocks(int blockYPointPlace, int width, int height) {
        Color[] rowColor = {
                new Color(232, 8, 94),
                new Color(195, 156, 22),
                new Color(152, 43, 152),
                new Color(56, 122, 56),
                new Color(91, 195, 106),
                new Color(227, 154, 227)
        };

        for (int i = 0; i < 6; i++) {
            int blockXPointPlace = 720;
            for (int j = 0; j < 12 - i; j++) {
                Point p = new Point(blockXPointPlace, blockYPointPlace);
                Block block = new Block(p, width, height, rowColor[i], false);
                addListeners(block);
                block.addToGame(this);
                blockXPointPlace -= width;
                blockCounter.increase(1);
            }
            blockYPointPlace += height;
        }
    }

    /**
     * Adds a paddle to the game, which is controlled by the keyboard.
     */
    public void addPaddle() {
        KeyboardSensor keyboard = this.gui.getKeyboardSensor();
        Paddle paddle = new Paddle(new Point(350, 560), 150, 10, keyboard, false);
        paddle.addToGame(this);
    }

    /**
     * Adds hit listeners (balls) to the given block.
     *
     * @param block the block to attach listeners to
     */
    public void addListeners(Block block) {
        for (Ball ball : this.balls) {
            block.addHitListener(ball);
        }
    }

    /**
     * Adds a {@link Collidable} to the game environment.
     *
     * @param c the collidable to add
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * Removes a {@link Collidable} from the game environment.
     *
     * @param c the collidable to remove
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Removes a {@link Sprite} from the sprite collection.
     *
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Adds a {@link Sprite} to the sprite collection.
     *
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Sets the GUI used for rendering the game.
     *
     * @param gui the GUI instance to use
     */
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
