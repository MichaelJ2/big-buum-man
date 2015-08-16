package at.mgm.bbm.gui;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;

public enum Resources {

    INSTANCE;

    public Font font = null;

    {
        try {
            font = new AngelCodeFont("fonts/font.fnt", new Image("fonts/font.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
