package at.mgm.bbm.core.objects;

import at.mgm.bbm.core.GameObject;
import at.mgm.bbm.core.objects.gameobjects.GameObjectType;

public abstract class DisplayObject extends GameObject {

    public final GameObjectType objectType;
    public final String texture;
    public int eventRange;
    public long timerInMillis;

    protected DisplayObject(final GameObjectType paramGameObjectType, final int paramX, final int paramY, final int paramEventRange) {
        super(paramX, paramY);
        this.objectType = paramGameObjectType;
        this.x = paramX;
        this.y = paramY;
        this.texture = paramGameObjectType.TEXTURE;
        this.eventRange = paramEventRange;
    }
}
