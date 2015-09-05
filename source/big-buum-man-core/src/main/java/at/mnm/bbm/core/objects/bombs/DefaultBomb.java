package at.mnm.bbm.core.objects.bombs;

import at.mnm.bbm.core.events.shapes.EventShape;
import at.mnm.bbm.core.objects.effects.EffectType;

public class DefaultBomb extends Bomb {

    public static final String TEXTURE = "textures/bomb.png";

    private static final int RANGE = Bomb.DEFAULT_RANGE;
    private static final long TIMER = Bomb.DEFAULT_TIMER;
    private static final EffectType EFFECT_TYPE = EffectType.FIRE;
    private static final EventShape EVENT_SHAPE = EventShape.CROSS;

    /**
     * Protected constructor only accessible for BombFactory.
     */
    protected DefaultBomb(final int paramX, final int paramY) {
        super(BombType.DEFAULT, paramX, paramY);
    }

    /**
     * Protected constructor only accessible for BombFactory.
     */
    protected DefaultBomb() {
        super(BombType.DEFAULT, 0, 0);
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
