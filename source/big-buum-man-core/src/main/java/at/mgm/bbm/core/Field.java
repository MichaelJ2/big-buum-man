package at.mgm.bbm.core;

import at.mgm.bbm.core.map.Map;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public abstract class Field {

    protected FieldType fieldType = null;

    private Shape hitBox;
    public int x;
    public int y;

    public Field(final FieldType paramFieldType, final int paramX, final int paramY) {
        fieldType = paramFieldType;
        x = paramX;
        y = paramY;
        hitBox = new Rectangle(paramX, paramY, Map.FIELD_SIZE, Map.FIELD_SIZE);
    }

    public boolean checkCollision(final int paramX, final int paramY) {
        return hitBox.contains(paramX, paramY);
    }

    public FieldType getFieldType() {
        return fieldType;
    }
}
