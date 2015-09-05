package at.mnm.bbm.core.events;

import at.mnm.bbm.core.objects.DisplayObject;
import at.mnm.bbm.core.objects.extras.Extra;
import at.mnm.bbm.core.objects.GameObjectType;
import at.mnm.bbm.core.objects.characters.Player;

public enum EventExecutor {

    INSTANCE;

    public void createEvent(final Player paramPlayer, final DisplayObject paramDisplayObject) {
        final GameObjectType gameObjectType = paramDisplayObject.objectType;
        System.out.println(String.format("Executing new Event for: %s", gameObjectType.name()));
        switch (gameObjectType) {
            case EXTRA:
                new GrabExtra(paramPlayer, (Extra) paramDisplayObject);
                break;
        }
    }
}
