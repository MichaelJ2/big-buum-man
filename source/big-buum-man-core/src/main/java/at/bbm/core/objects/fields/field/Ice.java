package at.bbm.core.objects.fields.field;

import at.bbm.core.events.EventType;
import at.bbm.core.objects.fields.Field;
import at.bbm.core.objects.fields.FieldType;
import at.bbm.core.objects.players.Player;
import at.bbm.core.map.Location;

public class Ice extends Field {

    public static final int ID = 6;
    public static final String TEXTURE = "textures/ice.png";
    public static final boolean LOCKED = false;
    public static final int DURABILITY = 1;
    public static final boolean ACCEPT_PLAYER = true;
    public static final boolean ACCEPT_FIRE = true;
    public static final double SPEED = 2.0;

    public Ice(final Location paramLocation) {
        super(FieldType.ICE, paramLocation, TEXTURE, LOCKED, DURABILITY, ACCEPT_PLAYER, ACCEPT_FIRE, SPEED);
    }

    public final void updatePlayer(final Player paramPlayer) {

    }

    public final void handleEvent(final EventType paramEventType) {

    }
}
