package at.mgm.bbm.gui;

import at.mgm.bbm.gui.screens.EditorScreen;
import org.junit.Test;
import org.newdawn.slick.AppGameContainer;
import java.lang.Exception;

public class TestGui {

    @Test
    public void testGui() {
        try {
            AppGameContainer container = new AppGameContainer(new EditorScreen());
            container.setDisplayMode(1920, 1080, false);
            container.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
