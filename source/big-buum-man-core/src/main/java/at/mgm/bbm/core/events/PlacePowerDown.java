package at.mgm.bbm.core.events;

import at.mgm.bbm.core.map.ObjectMap;
import at.mgm.bbm.core.objects.gameobjects.EventShape;
import at.mgm.bbm.core.objects.gameobjects.PowerDown;

public class PlacePowerDown extends Event {

    public PowerDown powerDown;

    public PlacePowerDown(final PowerDown paramPowerDown) {
        super(EventType.PLACE_POWERDOWN, paramPowerDown.x, paramPowerDown.y, 0, EventShape.SQUARE);
        this.powerDown = paramPowerDown;
        this.execute();
    }

    @Override
    public void execute() {
        // TODO: place power-down on map
        ObjectMap.INSTANCE.addObject(this.powerDown);
    }
}
