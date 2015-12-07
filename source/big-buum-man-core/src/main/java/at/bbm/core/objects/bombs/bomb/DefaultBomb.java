package at.bbm.core.objects.bombs.bomb;

import at.bbm.core.events.EventType;
import at.bbm.core.map.Location;
import at.bbm.core.objects.bombs.Bomb;
import at.bbm.core.objects.bombs.BombType;

public class DefaultBomb extends Bomb {

    public static final String TEXTURE = "textures/bomb.png";

    private static final int RANGE = DEFAULT_RANGE;
    private static final long TIMER = DEFAULT_TIMER;

    /**
     * Protected constructor only accessible for BombFactory.
     */
    protected DefaultBomb() {
        super(BombType.DEFAULT);
    }

    /**
     * Protected constructor only accessible for BombFactory.
     */
    protected DefaultBomb(final Location paramLocation) {
        super(BombType.DEFAULT);
    }

    @Override
    public void detonate() {

    }

    @Override
    public void handleEvent(EventType paramEventType) {

    }
}
