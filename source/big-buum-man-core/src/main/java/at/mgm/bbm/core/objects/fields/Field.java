package at.mgm.bbm.core.objects.fields;

import at.mgm.bbm.core.GameObject;

public abstract class Field {

    public final FieldType fieldType;
    public final int ID;
    public final String texture;
    public final boolean isLocked;
    public final boolean acceptPlayer;
    public final boolean acceptFire;
    public final double walkSpeed;

    public int durability;

    protected Field(final FieldType paramFieldType, final String paramTexture, final boolean paramLocked, final int paramDurability, final boolean paramAcceptPlayer, final boolean paramAcceptFire, final double paramWalkSpeed) {
        this.fieldType = paramFieldType;
        this.ID = paramFieldType.ID;
        this.texture = paramTexture;
        this.isLocked = paramLocked;
        this.durability = paramDurability;
        this.acceptPlayer = paramAcceptPlayer;
        this.acceptFire = paramAcceptFire;
        this.walkSpeed = paramWalkSpeed;
    }

    public final int decreaseDurability() {
        if (this.durability > 0) {
            this.durability--;
        }
        return this.durability;
    }
}
