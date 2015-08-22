package at.mgm.bbm.core.events;

import at.mgm.bbm.core.objects.gameobjects.Bomb;
import at.mgm.bbm.core.map.ObjectMap;
import at.mgm.bbm.core.objects.gameobjects.Player;

public class PlaceBomb extends Event {

    private final Bomb bomb;

    public PlaceBomb(final Player paramPlayer) {
        super(EventType.PLACE_BOMB, paramPlayer.x, paramPlayer.y, paramPlayer.eventRange, paramPlayer.bombDetonationShape);
        this.bomb = new Bomb(paramPlayer);
        this.execute();
    }

    @Override
    protected void execute() {
        System.out.println(String.format("Bomb placed at %d x %d", bomb.x, bomb.y));
        // place bomb on map
        if (!ObjectMap.INSTANCE.addObject(this.bomb)) {
            return;
        }
        // activate bomb
        bomb.activate();
    }
}