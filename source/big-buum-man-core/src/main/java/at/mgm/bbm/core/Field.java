package at.mgm.bbm.core;

public abstract class Field {

    protected FieldType fieldType = null;

    public int x;
    public int y;

    public Field(final FieldType paramFieldType, final int paramX, final int paramY) {
        fieldType = paramFieldType;
        x = paramX;
        y = paramY;
    }

    public FieldType getFieldType() {
        return fieldType;
    }
}
