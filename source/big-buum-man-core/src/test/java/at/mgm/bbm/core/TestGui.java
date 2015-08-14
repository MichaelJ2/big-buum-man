package at.mgm.bbm.core;

import at.mgm.bbm.core.screens.EditorScreen;
import org.junit.Test;
import org.newdawn.slick.AppGameContainer;

public class TestGui {

    @Test
    public void testGui() {
        try {
            AppGameContainer container = new AppGameContainer(new EditorScreen());
            container.setDisplayMode(1920, 1080, false);
            //container.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
