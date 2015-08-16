package at.mgm.bbm.core.fields;

import at.mgm.bbm.core.Field;
import at.mgm.bbm.core.FieldType;

public class Water extends Field {

    public static final int ID = 5;
    public static final String TEXTURE = "textures/water.png";
    public static final boolean LOCKED = false;
    public static final int DURABILITY = 3;
    public static final boolean ACCEPT_PLAYER = true;
    public static final boolean ACCEPT_FIRE = true;
    public static final double SPEED = 0.5;

    protected Water(final int paramX, final int paramY) {
        super(FieldType.WATER, TEXTURE, LOCKED, DURABILITY, ACCEPT_PLAYER, ACCEPT_FIRE, SPEED, paramX, paramY);
    }
}
