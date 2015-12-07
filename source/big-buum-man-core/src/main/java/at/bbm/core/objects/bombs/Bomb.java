package at.bbm.core.objects.bombs;

import at.bbm.core.objects.players.Player;
import at.bbm.core.events.FutureEventExecutor;
import at.bbm.core.events.FutureEvent;
import at.bbm.core.objects.GameObject;
import at.bbm.core.objects.GameObjectType;

public abstract class Bomb extends GameObject implements BombInterface {

    public static final long DEFAULT_TIMER = 2000000000l;
    public static final int DEFAULT_RANGE = 3;

    private long bombTimerInNanos = DEFAULT_TIMER;
    private int bombRange = DEFAULT_RANGE;
    private Player owner;

    private BombType bombType;

    Bomb() {
        super(GameObjectType.BOMB);
    }

    public Bomb(final BombType paramBombType) {
        super(GameObjectType.BOMB);
        this.bombType = paramBombType;
    }

    public void setOwner(final Player paramPlayer) {
        this.owner = paramPlayer;
    }

    public long getBombTimerInNanos() {
        return this.bombTimerInNanos;
    }

    public final void activate() {
        // TODO: FutureEvent event = new DetonateBomb(this);
        // FutureEventExecutor.getInstance().addFutureEvent(event);
    }

}
