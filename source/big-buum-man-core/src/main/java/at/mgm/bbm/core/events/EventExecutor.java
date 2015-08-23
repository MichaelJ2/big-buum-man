package at.mgm.bbm.core.events;

import at.mgm.bbm.core.objects.DisplayObject;
import at.mgm.bbm.core.objects.gameobjects.GameObjectType;
import at.mgm.bbm.core.objects.gameobjects.Player;
import at.mgm.bbm.core.objects.gameobjects.PowerDown;
import at.mgm.bbm.core.objects.gameobjects.PowerUp;

public enum EventExecutor {

    INSTANCE;

    public void createEvent(final Player paramPlayer, final DisplayObject paramDisplayObject) {
        final GameObjectType gameObjectType = paramDisplayObject.objectType;
        System.out.println(String.format("Executing new Event for: %s", gameObjectType.name()));
        switch (gameObjectType) {
            case POWERUP:
                new GrabPowerUp(paramPlayer, (PowerUp) paramDisplayObject);
                break;
            case POWERDOWN:
                new GrabPowerDown(paramPlayer, (PowerDown) paramDisplayObject);
                break;
        }
    }
}
