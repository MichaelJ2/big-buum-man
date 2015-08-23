package at.mgm.bbm.core.objects.gameobjects;

import at.mgm.bbm.core.objects.DisplayObject;

public class PowerDown extends DisplayObject {

    public static final String TEXTURE = "textures/power.png";

    public static final int DEFAULT_DURATION = 10000;

    public PowerDown(final int paramX, final int paramY) {
        super(GameObjectType.POWERDOWN, paramX, paramY, 0);
    }
}
