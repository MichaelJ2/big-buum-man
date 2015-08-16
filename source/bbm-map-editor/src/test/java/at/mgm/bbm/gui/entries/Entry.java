package at.mgm.bbm.gui.entries;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import java.io.File;

public class Entry {

    public int ID = -1;
    public int x = -1;
    public int y = -1;
    public Object object = null;
    public String text = "";

    private Shape hitBox;

    private Entry() {
        // not allowed
    }

    public Entry(final int paramID, final int paramX, final int paramY, final int paramWidth, final int paramHeight, final Object paramObject) {
        ID = paramID;
        x = paramX;
        y = paramY;
        hitBox = new Rectangle(paramX, paramY, paramWidth, paramHeight);
        object = paramObject;

        if (paramObject instanceof File) {
            text = ((File) paramObject).getName();
        } else if (paramObject instanceof String) {
            text = paramObject.toString();
        }
    }

    public boolean checkCollision(final int paramX, final int paramY) {
        return hitBox.contains(paramX, paramY);
    }

    public Object getObject() {
        return object;
    }
}
