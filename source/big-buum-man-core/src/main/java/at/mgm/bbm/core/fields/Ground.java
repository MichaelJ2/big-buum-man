package at.mgm.bbm.core.fields;

import at.mgm.bbm.core.Field;
import at.mgm.bbm.core.FieldType;

public class Ground extends Field {

    private static final int ID = 0;

    protected Ground() {
        super(FieldType.GROUND, ID);
    }
}
