package at.mgm.bbm.gui.entries;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public abstract class Entry {

    private Shape hitBox;

    public final int ID;

    protected Entry(final int paramX, final int paramY, final int paramWidth, final int paramHeight, final int paramID) {
        hitBox = new Rectangle(paramX, paramY, paramWidth, paramHeight);
        ID = paramID;
    }

    public boolean checkCollision(final int paramX, final int paramY) {
        return hitBox.contains(paramX, paramY);
    }
}
