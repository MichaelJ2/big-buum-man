package at.mgm.bbm.core.fields;

import at.mgm.bbm.core.Field;
import at.mgm.bbm.core.FieldType;

public class Fence extends Field {

    public static final int ID = 4;
    public static final String TEXTURE = "textures/fence.png";
    public static final boolean LOCKED = false;
    public static final int DURABILITY = 5;
    public static final boolean ACCEPT_PLAYER = false;
    public static final boolean ACCEPT_FIRE = true;
    public static final double SPEED = 0.0;

    protected Fence(final int paramX, final int paramY) {
        super(FieldType.FENCE, TEXTURE, LOCKED, DURABILITY, ACCEPT_PLAYER, ACCEPT_FIRE, SPEED, paramX, paramY);
    }
}