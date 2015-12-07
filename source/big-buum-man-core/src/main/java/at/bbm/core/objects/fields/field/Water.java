package at.bbm.core.objects.fields.field;

import at.bbm.core.events.EventType;
import at.bbm.core.objects.fields.Field;
import at.bbm.core.objects.fields.FieldType;
import at.bbm.core.objects.players.Player;

public class Water extends Field {

    public static final int ID = 5;
    public static final String TEXTURE = "textures/water.png";
    public static final boolean LOCKED = false;
    public static final int DURABILITY = 3;
    public static final boolean ACCEPT_PLAYER = true;
    public static final boolean ACCEPT_FIRE = true;
    public static final double SPEED = 0.5;

    public Water() {
        super(FieldType.WATER, null);
    }

    @Override
    public void updatePlayer(Player paramPlayer) {

    }

    @Override
    public void handleEvent(EventType paramEventType) {

    }
}
