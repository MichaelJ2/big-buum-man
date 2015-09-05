package at.mnm.bbm.core.events;

import at.mnm.bbm.core.map.ObjectMap;
import at.mnm.bbm.core.events.shapes.EventShape;
import at.mnm.bbm.core.objects.effects.EffectType;
import at.mnm.bbm.core.objects.extras.Extra;
import at.mnm.bbm.core.objects.extras.ExtraFactory;
import at.mnm.bbm.core.objects.extras.ExtraType;

public class PlaceExtra extends Event {

    public static final EffectType EFFECT_TYPE = EffectType.GLITTER;

    public final Extra extra;

    public PlaceExtra(final Extra paramExtra) {
        super(EventType.PLACE_EXTRA, -1, -1);
        this.extra = paramExtra;
    }

    public PlaceExtra(final Extra paramExtra, final int paramX, final int paramY) {
        super(EventType.PLACE_EXTRA, paramX, paramY);
        this.extra = paramExtra;
    }

    @Override
    protected void initEvent() {
        this.effectType = EFFECT_TYPE;
    }

    @Override
    protected final void execute() {
        // TODO: place power-down on map
        if (ObjectMap.INSTANCE.addObject(this.extra)) {
            System.out.println(String.format("PowerDown placed at %d x %d", this.getX(), this.getY()));
        }
    }
}
