package at.mgm.bbm.core.objects.gameobjects;

import at.mgm.bbm.core.objects.DisplayObject;

public class PowerUp extends DisplayObject {

    public static final String TEXTURE = "textures/power.png";

    public static final int DEFAULT_DURATION = 5000;

    protected PowerUp(final int paramX, final int paramY) {
        super(GameObjectType.POWERUP, paramX, paramY, 0);
    }
}
