package at.mnm.bbm.core.objects.fields;

public class FieldFactory {

    private FieldFactory() {
        // defeat instantiation
    }

    public static Field getField(final FieldType paramType) {
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
            case BORDER:
                field = new Border();
                break;
            case WATER:
                field = new Water();
                break;
            case ICE:
                field = new Ice();
                break;
            case FENCE:
                field = new Fence();
                break;
            case BARRIER:
                field = new Barrier();
                break;
            default:
                field = new Wall();
                break;
        }
        return field;
    }

    public static Field getField(final int paramID) {
        Field field;

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
                field = new Border();
                break;
            case Water.ID:
                field = new Water();
                break;
            case Ice.ID:
                field = new Ice();
                break;
            case Fence.ID:
                field = new Fence();
                break;
            case Barrier.ID:
                field = new Barrier();
                break;
            default:
                field = new Wall();
                break;
        }
        return field;
    }
}
