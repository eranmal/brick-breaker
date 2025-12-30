package sprites;

import collision.Counter;
import game.Game;
import observe.HitListener;

/**
 * The {@code BlockRemover} class is responsible for removing blocks from the game when they are hit,
 * and for updating the count of remaining blocks.
 * It implements the {@link HitListener} interface to respond to hit events.
 */
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    /**
     * Constructs a {@code BlockRemover} with a reference to the game and a counter
     * that tracks the number of remaining blocks.
     *
     * @param game           the game from which blocks will be removed
     * @param remainingBlocks a counter tracking how many blocks are left in the game
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * Called when a block is hit. If the block is not a designated bottom block,
     * it is removed from the game and the remaining block count is decremented.
     *
     * @param beingHit the block that was hit
     * @param hitter   the ball that hit the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (!beingHit.isBottomBlock()) {
            beingHit.removeHitListener(this);
            game.removeCollidable(beingHit);
            game.removeSprite(beingHit);
            remainingBlocks.decrease(1);
        }
    }
}
