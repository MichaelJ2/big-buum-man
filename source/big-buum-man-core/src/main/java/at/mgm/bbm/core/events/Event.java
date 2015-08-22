package at.mgm.bbm.core.events;

import at.mgm.bbm.core.GameObject;
import at.mgm.bbm.core.objects.gameobjects.EventShape;

public abstract class Event extends GameObject {


    public final EventType eventType;
    public final EventShape eventShape;
    public int eventRange;

    protected abstract void execute();

    protected Event(final EventType paramEventType, final int paramX, final int paramY, final int paramEventRange, final EventShape paramEventShape) {
        super(paramX, paramY);
        this.eventType = paramEventType;
        this.x = paramX;
        this.y = paramY;
        this.eventRange = paramEventRange;
        this.eventShape = paramEventShape;
    }
}
