package at.bbm.core.objects.players;

import at.bbm.core.events.EventType;
import at.bbm.core.objects.GameObject;
import at.bbm.core.objects.GameObjectType;
import at.bbm.core.objects.bombs.Bomb;
import at.bbm.core.objects.bombs.BombFactory;
import at.bbm.core.objects.bombs.BombType;
import at.bbm.core.map.Location;
import at.bbm.core.map.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player extends GameObject {

    public static final Logger LOGGER = LogManager.getLogger(Player.class.getName());

    private int bombRange = Bomb.DEFAULT_RANGE;
    private double bombTimeAmplifier = 1.0;
    private BombType bombType = BombType.DEFAULT;
    protected double speed = 1.0;
    private Location location = new Location();
    private final String name;

    public Player() {
        super(GameObjectType.PLAYER);
        this.name = "unknown";
    }

    public Player(final String paramName) {
        super(GameObjectType.PLAYER);
        this.name = paramName;
    }

    public Player(final Location paramLocation, final String paramName) {
        super(GameObjectType.PLAYER);
        this.location = paramLocation;
        this.name = paramName;
    }

    public void setLocation(final Location paramLocation, final String paramName) {
        if (null != paramLocation) {
            this.location = paramLocation;
        }
    }

    public final void updateSpeed(final double paramSpeed) {
        this.speed = paramSpeed;
    }

    void updateBombType(final BombType paramBombType) {
        this.bombType = paramBombType;
    }

    void updateBombTime(final double paramBombTimeAmplifier) {
        this.bombTimeAmplifier = paramBombTimeAmplifier;
    }

    void updateBombRange(final int paramBombRange) {
        this.bombRange = paramBombRange;
    }

    public synchronized void placeBomb() {
        final Bomb bomb = BombFactory.getBomb(this.bombType);
        Map.getInstance().placeObject(this.location, bomb);
        // TODO
    }

    public Location getLocation() {
        return this.location;
    }

    public void takeExtra() {

    }

    public synchronized void die() {

    }

    public synchronized void move(final String paramDirection) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Moved player {} to direction {}", this.name, paramDirection);
        }
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void handleEvent(EventType paramEventType) {

    }
}
