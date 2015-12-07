package at.bbm.core.objects.fields;

import at.bbm.core.GlobalProperties;
import at.bbm.core.objects.GameObject;
import at.bbm.core.objects.GameObjectType;
import at.bbm.core.map.Location;

public abstract class Field extends GameObject implements FieldInterface {

    /**
     * Durability:
     *
     * 1    =   after first explosion the field is "destroyed"
     * -1   =   the field can not be "destroyed"
     *
     */

    protected final FieldType fieldType;
    protected String texture = GlobalProperties.TEXTURE_FIELD;
    protected boolean locked = false;
    protected int durability = 1;
    protected boolean acceptsPlayer = true;
    protected boolean acceptsFire = true;
    protected double speed = 1.0d;

    public Field (final FieldType paramFieldType, final Location paramLocation) {
        super(GameObjectType.FIELD);
        this.fieldType = paramFieldType;
    }

    public Field (final FieldType paramFieldType, final Location paramLocation, final String paramTexture,
                  final boolean paramLocked, final int paramDurability, final boolean paramAcceptsPlayer,
                  final  boolean paramAcceptsFire,  final double paramSpeed) {
        super(GameObjectType.FIELD);
        this.fieldType = paramFieldType;
        this.texture = paramTexture;
        this.locked = paramLocked;
        this.durability = paramDurability;
        this.acceptsPlayer = paramAcceptsPlayer;
        this.acceptsFire = paramAcceptsFire;
        this.speed = paramSpeed;
    }

    public FieldType getFieldType() {
        return this.fieldType;
    }

    public int decreaseDurability() {
        if (this.durability > 0) {
            this.durability--;
        }
        return this.durability;
    }

}
