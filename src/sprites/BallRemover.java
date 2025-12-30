package sprites;

import collision.Counter;
import game.Game;
import observe.HitListener;

/**
 * A HitListener that removes balls from the game when they hit certain blocks.
 * Also updates the count of remaining balls.
 */
public class BallRemover implements HitListener {
    private final Game game;
    private final Counter remainingBalls;

    /**
     * Constructs a BallRemover.
     *
     * @param game           the game from which balls should be removed
     * @param remainingBalls a counter tracking the number of remaining balls
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    /**
     * Called when a ball hits a block. Removes the ball from the game and updates the ball counter.
     * Also removes this listener if necessary.
     *
     * @param beingHit the block that was hit
     * @param hitter   the ball that hit the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        game.removeSprite(hitter);
        remainingBalls.decrease(1);
    }
}
