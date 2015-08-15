package at.mgm.bbm.core.fields;

import at.mgm.bbm.core.Field;
import at.mgm.bbm.core.FieldType;

public class Wall extends Field {

    public static final int ID = 1;
    public static final String TEXTURE_PATH = "textures/wall.png";
    public static final boolean LOCKED = false;
    public static final boolean DESTROYABLE = true;

    protected Wall(final int paramX, final int paramY) {
        super(FieldType.WALL, paramX, paramY);
    }
}
