package collision;

/**
 * A simple counter class that can be increased or decreased.
 * Typically used to keep track of game objects like remaining balls or blocks.
 */
public class Counter {

    private int collidableNum = 0;

    /**
     * Increases the counter by a specified number.
     *
     * @param number the amount to increase the counter by
     */
    public void increase(int number) {
        collidableNum += number;
    }

    /**
     * Decreases the counter by a specified number.
     *
     * @param number the amount to decrease the counter by
     */
    public void decrease(int number) {
        collidableNum -= number;
    }

    /**
     * Gets the current value of the counter.
     *
     * @return the counter's value
     */
    public int getValue() {
        return collidableNum;
    }
}
