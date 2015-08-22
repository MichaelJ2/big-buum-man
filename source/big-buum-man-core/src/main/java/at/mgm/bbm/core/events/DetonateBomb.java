package at.mgm.bbm.core.events;

import at.mgm.bbm.core.map.VMap;
import at.mgm.bbm.core.map.ObjectMap;
import at.mgm.bbm.core.objects.gameobjects.Bomb;

public class DetonateBomb extends Event {

    private final Bomb bomb;

    public DetonateBomb(final Bomb paramBomb) {
        super(EventType.DETONATE_BOMB, paramBomb.x, paramBomb.y, paramBomb.eventRange, paramBomb.bombDetonationShape);
        this.bomb = paramBomb;
        this.execute();
    }

    @Override
    public void execute() {
        System.out.println(String.format("Bomb detonated at %d x %d", this.bomb.x, this.bomb.y));
        VMap.INSTANCE.triggerEvents(this);
        ObjectMap.INSTANCE.removeObject(this.bomb);
    }
}
