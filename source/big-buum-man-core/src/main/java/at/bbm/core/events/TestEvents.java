package at.bbm.core.events;

import at.bbm.core.Bomberman;
import org.junit.Test;

public class TestEvents {

    @Test
    public void testEvents() {
        final InstantEventExecutor instantEventExecutor = InstantEventExecutor.getInstance();
        final Bomberman game = Bomberman.getInstance();

        game.startGame();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        game.pauseGame();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        game.resumeGame();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        game.stopGame();
    }

}
