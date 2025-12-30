import biuoop.GUI;
import game.Game;

/**
 * The {@code Ass3Game} class serves as the entry point for the game application.
 * It initializes the game window, sets up the game, and starts the game loop.
 */
public class Ass5Game {

    /**
     * The main method is the entry point of the program.
     * It creates a {@link Game} instance, sets its GUI, initializes game components, and starts the game.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.setGui(new GUI("ass5", 800, 600));
        game.initialize();
        game.run();
    }
}
