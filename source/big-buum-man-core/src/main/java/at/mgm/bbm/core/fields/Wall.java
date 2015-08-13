package at.mgm.bbm.core.fields;

import at.mgm.bbm.core.Field;
import at.mgm.bbm.core.FieldType;

public class Wall extends Field {

    private static final int ID = 1;

    protected Wall() {
        super(FieldType.GROUND, ID);
    }
}
