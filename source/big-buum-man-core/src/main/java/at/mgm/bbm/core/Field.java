package at.mgm.bbm.core;

import at.mgm.bbm.core.map.Map;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public abstract class Field {

    public FieldType fieldType;
    public final int ID;
    public final String texture;
    public final boolean isLocked;
    public int durability;
    public final boolean acceptPlayer;
    public final boolean acceptFire;
    public final double walkSpeed;

    private Shape hitBox;
    public final int x;
    public final int y;

    protected Field(final FieldType paramFieldType, final String paramTexture, final boolean paramLocked, final int paramDurability, final boolean paramAcceptPlayer, final boolean paramAcceptFire, final double paramWalkSpeed, final int paramX, final int paramY) {
        fieldType = paramFieldType;
        ID = paramFieldType.ID;
        texture = paramTexture;
        isLocked = paramLocked;
        durability = paramDurability;
        acceptPlayer = paramAcceptPlayer;
        acceptFire = paramAcceptFire;
        walkSpeed = paramWalkSpeed;
        x = paramX;
        y = paramY;
        hitBox = new Rectangle(paramX, paramY, Map.FIELD_SIZE, Map.FIELD_SIZE);
    }

    public boolean checkCollision(final int paramX, final int paramY) {
        return hitBox.contains(paramX, paramY);
    }

    public void setDurability(final int paramDurability) {
        durability = paramDurability;
    }

    public int decreaseDurability() {
        if (durability > 0) {
            durability--;
        }
        return durability;
    }
}
