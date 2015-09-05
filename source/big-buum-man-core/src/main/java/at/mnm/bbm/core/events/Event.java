package at.mnm.bbm.core.events;

import at.mnm.bbm.core.events.shapes.EventShape;
import at.mnm.bbm.core.objects.DisplayObject;
import at.mnm.bbm.core.objects.GameObjectType;
import at.mnm.bbm.core.objects.effects.EffectType;

public abstract class Event extends DisplayObject {

    public static final int DEFAULT_RANGE = 0;
    public static final EffectType DEFAULT_EFFECT_TYPE = EffectType.NONE;
    public static final EventShape DEFAULT_EVENT_SHAPE = EventShape.NONE;

    public final EventType eventType;

    protected EffectType effectType = DEFAULT_EFFECT_TYPE;
    protected EventShape eventShape = DEFAULT_EVENT_SHAPE;
    protected int eventRange = DEFAULT_RANGE;

    protected abstract void initEvent();
    protected abstract void execute();

    protected Event(final EventType paramEventType, final int paramX, final int paramY) {
        super(GameObjectType.EVENT, paramX, paramY);
        this.eventType = paramEventType;
        this.initEvent();
        this.execute();
    }
}
