package at.bbm.core.objects.extras;

import at.bbm.core.objects.GameObject;
import at.bbm.core.objects.GameObjectType;
import at.bbm.core.map.Location;

public abstract class Extra extends GameObject implements ExtraInterface {

    Extra(final Location paramLocation) {
        super(GameObjectType.EXTRA);
    }

}
