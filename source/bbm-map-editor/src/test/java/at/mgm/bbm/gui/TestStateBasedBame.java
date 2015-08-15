package at.mgm.bbm.gui;

import at.mgm.bbm.gui.screens.Application;
import org.junit.Test;
import org.newdawn.slick.AppGameContainer;

public class TestStateBasedBame {

    @Test
    public void testSTateBasedGame() {
        try {
            AppGameContainer container = new AppGameContainer(new Application("Map Editor"));
            container.setDisplayMode(1920, 1080, false);
            container.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
