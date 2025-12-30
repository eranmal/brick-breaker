package game;

import collision.Counter;
import observe.HitListener;
import sprites.Ball;
import sprites.Block;

/**
 * The {@code ScoreTrackingListener} is a listener that tracks the player's score.
 * It listens for hit events between balls and blocks, and increases the score when such an event occurs.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Constructs a ScoreTrackingListener with a given score counter.
     *
     * @param scoreCounter the counter that tracks the score
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * This method is called whenever a block is hit by a ball.
     * It increases the score by a fixed amount (5 points).
     *
     * @param beingHit the block that was hit
     * @param hitter   the ball that hit the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        currentScore.increase(5);
    }

    /**
     * Returns the current score counter.
     *
     * @return the score counter
     */
    public Counter getCurrentScore() {
        return currentScore;
    }
}
