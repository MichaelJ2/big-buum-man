package at.mgm.bbm.core.fields;

import at.mgm.bbm.core.Field;
import at.mgm.bbm.core.FieldType;

public class Border extends Field {

    public static final int ID = 9;
    public static final String TEXTURE_PATH = "textures/border.png";
    public static final boolean LOCKED = true;
    public static final boolean DESTROYABLE = false;

    protected Border(final int paramX, final int paramY) {
        super(FieldType.BORDER, paramX, paramY);
    }
}
