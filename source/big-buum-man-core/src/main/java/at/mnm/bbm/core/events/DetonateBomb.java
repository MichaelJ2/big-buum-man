package at.mnm.bbm.core.events;

import at.mnm.bbm.core.map.VMap;
import at.mnm.bbm.core.map.ObjectMap;
import at.mnm.bbm.core.objects.bombs.Bomb;

public class DetonateBomb extends Event {

    private final Bomb bomb;

    public DetonateBomb(final Bomb paramBomb, final int paramX, final int paramY) {
        super(EventType.DETONATE_BOMB, paramX, paramY);
        this.bomb = paramBomb;
    }

    @Override
    protected final void initEvent() {
        this.eventRange = this.bomb.getRange();
        this.eventShape = this.bomb.getEventShape();
        this.effectType = this.bomb.getEffectType();
    }

    @Override
    protected final void execute() {
        System.out.println(String.format("Bomb \"\" detonated at %d x %d", this.bomb.bombType, this.getX(), this.getY()));
        VMap.INSTANCE.triggerEvent(this);
        ObjectMap.INSTANCE.removeObject(this.bomb);
    }
}
