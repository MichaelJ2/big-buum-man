package at.bbm.core.objects.fields;

import at.bbm.core.objects.fields.field.*;

public class FieldFactory {

    private FieldFactory() {
        // defeat instantiation
    }

    public static Field getField(final FieldType paramType) {
        Field field = null;
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
            case BORDER:
                field = new Border(null);
                break;
            case WATER:
                field = new Water();
                break;
            case ICE:
                field = new Ice(null);
                break;
            case FENCE:
                field = new Fence();
                break;
            case BARRIER:
                field = new Barrier(null);
                break;
            default:
                field = new Wall();
                break;
        }

        return field;
    }

    public static Field getField(final int paramID) {
        Field field = null;


        switch (paramID) {
            case Ground.ID:
                field = new Ground();
                break;
            case Wall.ID:
                field = new Wall();
                break;
            case Spawn.ID:
                field = new Spawn();
                break;
            case Border.ID:
                field = new Border(null);
                break;
            case Water.ID:
                field = new Water();
                break;
            case Ice.ID:
                field = new Ice(null);
                break;
            case Fence.ID:
                field = new Fence();
                break;
            case Barrier.ID:
                field = new Barrier(null);
                break;
            default:
                field = new Wall();
                break;
        }

        return field;
    }
}
