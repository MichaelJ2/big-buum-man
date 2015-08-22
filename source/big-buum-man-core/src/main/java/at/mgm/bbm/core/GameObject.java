package at.mgm.bbm.core;

import at.mgm.bbm.core.events.EventType;

public abstract class GameObject {
    public int x;
    public int y;

    protected GameObject(final int paramX, final int paramY) {
        this.x = paramX;
        this.y = paramY;
    }
}
