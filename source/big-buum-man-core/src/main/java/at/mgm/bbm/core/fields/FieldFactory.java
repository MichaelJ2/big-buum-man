package at.mgm.bbm.core.fields;

import at.mgm.bbm.core.Field;
import at.mgm.bbm.core.FieldType;

public class FieldFactory {

    public static Field getField(FieldType paramType) {
        Field field;

        switch (paramType) {
            case GROUND:
                field = new Ground();
                break;
            case WALL:
                field = new Wall();
                break;
            case SPAWN:
                field = new Spawn();
                break;
            default:
                field = null;
                break;
        }

        return field;
    }

}
