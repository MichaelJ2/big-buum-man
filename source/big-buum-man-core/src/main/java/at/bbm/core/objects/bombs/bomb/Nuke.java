package at.bbm.core.objects.bombs.bomb;


import at.bbm.core.events.EventType;
import at.bbm.core.objects.bombs.Bomb;
import at.bbm.core.objects.bombs.BombType;

public class Nuke extends Bomb {

    public static final String TEXTURE = "textures/nuke.png";

    private static final int RANGE = Integer.MAX_VALUE;
    private static final long TIMER = 10000;

    /**
     * Protected constructor only accessible for BombFactory.
     */
    protected Nuke(final int paramX, final int paramY) {
        super(BombType.NUKE);
    }

    /**
     * Protected constructor only accessible for BombFactory.
     */
    Nuke() {
        super(BombType.NUKE);
    }

    @Override
    public void detonate() {

    }

    @Override
    public void handleEvent(EventType paramEventType) {

    }
}
