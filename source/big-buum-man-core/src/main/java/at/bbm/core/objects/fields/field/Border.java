package at.bbm.core.objects.fields.field;

import at.bbm.core.events.EventType;
import at.bbm.core.objects.fields.Field;
import at.bbm.core.objects.fields.FieldType;
import at.bbm.core.objects.players.Player;
import at.bbm.core.map.Location;

public class Border extends Field {

    public static final int ID = 0;
    public static final String TEXTURE = "textures/border.png";
    public static final boolean LOCKED = true;
    public static final int DURABILITY = -1;
    public static final boolean ACCEPT_PLAYER = false;
    public static final boolean ACCEPT_FIRE = false;
    public static final double SPEED = 0.0;

    public Border(final Location paramLocation) {
        super(FieldType.BORDER, paramLocation, TEXTURE, LOCKED, DURABILITY, ACCEPT_PLAYER, ACCEPT_FIRE, SPEED);
    }

    public void updatePlayer(Player paramPlayer) {

    }

    @Override
    public void handleEvent(EventType paramEventType) {

    }
}
