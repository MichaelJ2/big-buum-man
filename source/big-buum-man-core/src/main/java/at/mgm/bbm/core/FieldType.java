package at.mgm.bbm.core;

import at.mgm.bbm.core.fields.Border;
import at.mgm.bbm.core.fields.Ground;
import at.mgm.bbm.core.fields.Spawn;
import at.mgm.bbm.core.fields.Wall;

public enum FieldType {
    GROUND(Ground.ID, Ground.TEXTURE_PATH, Ground.LOCKED, Ground.DESTROYABLE),
    WALL(Wall.ID, Wall.TEXTURE_PATH, Wall.LOCKED, Wall.DESTROYABLE),
    SPAWN(Spawn.ID, Spawn.TEXTURE_PATH, Spawn.LOCKED, Spawn.DESTROYABLE),
    BORDER(Border.ID, Border.TEXTURE_PATH, Border.LOCKED, Border.DESTROYABLE);

    public final int ID;
    public final String TEXTURE_PATH;
    public final boolean LOCKED;
    public final boolean DESTROYABLE;

    FieldType(final int paramID, final String paramTexture, final boolean paramLocked, final boolean paramDestroyable) {
        ID = paramID;
        TEXTURE_PATH = paramTexture;
        LOCKED = paramLocked;
        DESTROYABLE = paramDestroyable;
    }
}
