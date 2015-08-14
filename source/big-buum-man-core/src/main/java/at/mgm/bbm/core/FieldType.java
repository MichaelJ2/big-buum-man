package at.mgm.bbm.core;

import at.mgm.bbm.core.fields.Border;
import at.mgm.bbm.core.fields.Ground;
import at.mgm.bbm.core.fields.Spawn;
import at.mgm.bbm.core.fields.Wall;

public enum FieldType {
    GROUND(Ground.ID, Ground.TEXTURE_PATH, Ground.LOCKED),
    WALL(Wall.ID, Wall.TEXTURE_PATH, Wall.LOCKED),
    SPAWN(Spawn.ID, Spawn.TEXTURE_PATH, Spawn.LOCKED),
    BORDER(Border.ID, Border.TEXTURE_PATH, Border.LOCKED);

    public final int ID;
    public final String TEXTURE_PATH;
    public final boolean LOCKED;

    FieldType(final int paramID, final String paramTexture, final boolean paramLocked) {
        ID = paramID;
        TEXTURE_PATH = paramTexture;
        LOCKED = paramLocked;
    }
}
