package at.bbm.core.objects.fields;

import at.bbm.core.objects.fields.field.*;

public enum FieldType {

    BORDER(Border.ID, Border.TEXTURE),
    SPAWN(Spawn.ID, Spawn.TEXTURE),
    GROUND(Ground.ID, Ground.TEXTURE),
    WALL(Wall.ID, Wall.TEXTURE),
    FENCE(Fence.ID, Fence.TEXTURE),
    WATER(Water.ID, Water.TEXTURE),
    ICE(Ice.ID, Ice.TEXTURE),
    BARRIER(Barrier.ID, Barrier.TEXTURE);

    public final int ID;
    public final String TEXTURE;

    FieldType(final int paramId, final String paramTexture) {
        ID = paramId;
        TEXTURE = paramTexture;
    }
}
