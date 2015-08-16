package at.mgm.bbm.core.fields;

import at.mgm.bbm.core.Field;

import at.mgm.bbm.core.FieldType;

public class FieldFactory {

    public static Field getField(final FieldType paramType, final int paramX, final int paramY) {
        Field field;

        switch (paramType) {
            case GROUND:
                field = new Ground(paramX, paramY);
                break;
            case WALL:
                field = new Wall(paramX, paramY);
                break;
            case SPAWN:
                field = new Spawn(paramX, paramY);
                break;
            case BORDER:
                field = new Border(paramX, paramY);
                break;
            case WATER:
                field = new Water(paramX, paramY);
                break;
            case ICE:
                field = new Ice(paramX, paramY);
                break;
            case FENCE:
                field = new Fence(paramX, paramY);
                break;
            case BARRIER:
                field = new Barrier(paramX, paramY);
                break;
            default:
                field = new Wall(paramX, paramY);
                break;
        }
        return field;
    }

    public static Field getField(final int paramID, final int paramX, final int paramY) {
        Field field;

        switch (paramID) {
            case Ground.ID:
                field = new Ground(paramX, paramY);
                break;
            case Wall.ID:
                field = new Wall(paramX, paramY);
                break;
            case Spawn.ID:
                field = new Spawn(paramX, paramY);
                break;
            case Border.ID:
                field = new Border(paramX, paramY);
                break;
            case Water.ID:
                field = new Water(paramX, paramY);
                break;
            case Ice.ID:
                field = new Ice(paramX, paramY);
                break;
            case Fence.ID:
                field = new Fence(paramX, paramY);
                break;
            case Barrier.ID:
                field = new Barrier(paramX, paramY);
                break;
            default:
                field = new Wall(paramX, paramY);
                break;
        }
        return field;
    }
}
