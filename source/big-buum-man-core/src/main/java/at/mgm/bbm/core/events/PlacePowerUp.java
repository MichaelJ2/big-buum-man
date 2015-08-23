package at.mgm.bbm.core.events;

import at.mgm.bbm.core.map.ObjectMap;
import at.mgm.bbm.core.objects.gameobjects.EventShape;
import at.mgm.bbm.core.objects.gameobjects.PowerUp;

public class PlacePowerUp extends Event {

    private final PowerUp powerUp;

    public PlacePowerUp(final PowerUp paramPowerUp) {
        super(EventType.PLACE_POWERUP, paramPowerUp.x, paramPowerUp.y, 0, EventShape.SQUARE);
        this.powerUp = paramPowerUp;
        this.execute();
    }

    @Override
    public void execute() {
        // TODO: place power-up on map
        if (ObjectMap.INSTANCE.addObject(this.powerUp)) {
            System.out.println(String.format("PowerUp placed at %d x %d", this.powerUp.x, this.powerUp.y));
        }
    }
}
