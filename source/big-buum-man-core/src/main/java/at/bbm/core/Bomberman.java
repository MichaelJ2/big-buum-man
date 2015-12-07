package at.bbm.core;

/**
 * Main Game Object - Singleton
 *
 * The game is started from this instance!
 */
public class Bomberman {

    /** Singleton instance */
    private static Bomberman instance = null;

    /** Private constructor to prevent multiple instantiations */
    private Bomberman() {
        //
    }

    /** Singleton instance getter */
    public static Bomberman getInstance() {
        if (null == instance) {
            instance = new Bomberman();
        }
        return instance;
    }

    /** The game loop logic */
    private final Engine engine = new Engine();

    /** The thread in which the game loop will run */
    private final Thread engineThread = new Thread(this.engine);

    /** Starts the game loop execution */
    public void startGame() {
        this.engineThread.start();
    }

    /** Pauses the game loop */
    public void pauseGame() {
        this.engine.pause();
    }

    /** Resumes the game loop */
    public void resumeGame() {
        this.engine.resume();
    }

    /** Stops the game */
    public void stopGame() {
        this.engineThread.interrupt();
    }
}
