package at.mgm.bbm.gui.entries;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public abstract class Entry {

    private final Shape hitBox;
    public final int ID;
    public final Object object;

    protected Entry(final int paramX, final int paramY, final int paramWidth, final int paramHeight, final int paramID, final Object paramObject) {
        hitBox = new Rectangle(paramX, paramY, paramWidth, paramHeight);
        ID = paramID;
        object = paramObject;
    }

    public boolean checkCollision(final int paramX, final int paramY) {
        return hitBox.contains(paramX, paramY);
    }

    public Object getObject() {
        return object;
    }
}
