package at.mgm.bbm.core.fields;

import at.mgm.bbm.core.Field;
import at.mgm.bbm.core.FieldType;

public class Border extends Field {

    public static final int ID = 0;
    public static final String TEXTURE = "textures/border.png";
    public static final boolean LOCKED = true;
    public static final int DURABILITY = -1;
    public static final boolean ACCEPT_PLAYER = false;
    public static final boolean ACCEPT_FIRE = false;
    public static final double SPEED = 0.0;

    protected Border(final int paramX, final int paramY) {
        super(FieldType.BORDER, TEXTURE, LOCKED, DURABILITY, ACCEPT_PLAYER, ACCEPT_FIRE, SPEED, paramX, paramY);
    }
}
