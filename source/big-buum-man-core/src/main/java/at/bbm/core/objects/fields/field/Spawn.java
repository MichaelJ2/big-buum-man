package at.bbm.core.objects.fields.field;

import at.bbm.core.events.EventType;
import at.bbm.core.objects.fields.Field;
import at.bbm.core.objects.fields.FieldType;
import at.bbm.core.objects.players.Player;

public class Spawn extends Field {

    public static final int ID = 2;
    public static final String TEXTURE = "textures/spawn.png";
    public static final boolean LOCKED = false;
    public static final int DURABILITY = -1;
    public static final boolean ACCEPT_PLAYER = true;
    public static final boolean ACCEPT_FIRE = true;
    public static final double SPEED = 1.0;

    public Spawn() {
        super(FieldType.SPAWN, null);
    }

    @Override
    public void updatePlayer(Player paramPlayer) {

    }

    @Override
    public void handleEvent(EventType paramEventType) {

    }
}
