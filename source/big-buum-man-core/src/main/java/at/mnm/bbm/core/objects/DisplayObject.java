package at.mnm.bbm.core.objects;

public abstract class DisplayObject extends GameObject {

    public final GameObjectType objectType;
    protected String texture = "textures/null.png";

    protected DisplayObject(final GameObjectType paramGameObjectType, final int paramX, final int paramY) {
        super(paramX, paramY);
        this.objectType = paramGameObjectType;
    }
}
