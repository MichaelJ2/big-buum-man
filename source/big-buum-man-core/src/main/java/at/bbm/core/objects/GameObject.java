package at.bbm.core.objects;

public abstract class GameObject implements GameObjectInterface {

    protected final GameObjectType gameObjectType;

    public GameObject(final GameObjectType paramGameObjectType) {
        this.gameObjectType = paramGameObjectType;
    }

}
