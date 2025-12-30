package observe;

import sprites.Ball;
import sprites.Block;

/**
 * The {@code HitListener} interface should be implemented by any class that wants to be notified
 * of hit events occurring between a {@link Ball} and a {@link Block}.
 * When a block is hit, the {@code hitEvent} method is called to perform any appropriate actions.
 */
public interface HitListener {

    /**
     * This method is called whenever a block is hit by a ball.
     *
     * @param beingHit the {@link Block} that was hit
     * @param hitter   the {@link Ball} that caused the hit
     */
    void hitEvent(Block beingHit, Ball hitter);
}
