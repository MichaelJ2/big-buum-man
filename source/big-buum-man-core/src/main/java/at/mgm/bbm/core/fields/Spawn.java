package at.mgm.bbm.core.fields;

import at.mgm.bbm.core.Field;
import at.mgm.bbm.core.FieldType;

public class Spawn extends Field {

    public static final int ID = 2;
    public static final String TEXTURE_PATH = "textures/ground.png";
    public static final boolean LOCKED = true;

    protected Spawn(final int paramX, final int paramY) {
        super(FieldType.SPAWN, paramX, paramY);
    }
}
