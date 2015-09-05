package at.mnm.bbm.core.objects.bombs;

import at.mnm.bbm.core.events.shapes.EventShape;
import at.mnm.bbm.core.objects.effects.EffectType;

public class Nuke extends Bomb {

    public static final String TEXTURE = "textures/nuke.png";

    private static final int RANGE = Integer.MAX_VALUE;
    private static final long TIMER = 10000;
    private static final EffectType EFFECT_TYPE = EffectType.NUKE;
    private static final EventShape EVENT_SHAPE = EventShape.GLOBAL;

    /**
     * Protected constructor only accessible for BombFactory.
     */
    protected Nuke(final int paramX, final int paramY) {
        super(BombType.NUKE, paramX, paramY);
    }

    /**
     * Protected constructor only accessible for BombFactory.
     */
    protected Nuke() {
        super(BombType.NUKE, 0, 0);
    }

    @Override
    protected final void initBomb() {
        this.range = RANGE;
        this.timer = TIMER;
        this.effectType = EFFECT_TYPE;
        this.eventShape = EVENT_SHAPE;
        this.texture = TEXTURE;
    }
}
