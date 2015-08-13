package at.mgm.bbm.core;

public abstract class Field {

    private FieldType fieldType = null;
    private int fieldId = -1;

    public Field(FieldType paramFieldType, int paramFieldId) {
        fieldType = paramFieldType;
        fieldId = paramFieldId;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public int getFieldID() {
        return fieldId;
    }
}
