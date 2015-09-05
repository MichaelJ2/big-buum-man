package at.mnm.bbm.core.events;

import at.mnm.bbm.core.objects.bombs.Bomb;
import at.mnm.bbm.core.objects.bombs.BombFactory;
import at.mnm.bbm.core.map.ObjectMap;
import at.mnm.bbm.core.objects.bombs.BombType;
import at.mnm.bbm.core.objects.characters.Player;

public class PlaceBomb extends Event {

    private final Player player;
    private final Bomb bomb;

    public PlaceBomb(final BombType paramBombType, final int paramX, final int paramY) {
        super(EventType.PLACE_BOMB, paramX, paramY);
        this.player = new Player(paramX, paramY);
        this.bomb = BombFactory.getBomb(paramBombType);
    }

    public PlaceBomb(final Player paramPlayer) {
        super(EventType.PLACE_BOMB, paramPlayer.getX(), paramPlayer.getY());
        this.player = paramPlayer;
        this.bomb = BombFactory.getBomb(paramPlayer.bombType);
    }

    @Override
    protected final void initEvent() {
    }

    @Override
    protected void execute() {
        // try to place bomb on map
        if (!ObjectMap.INSTANCE.addObject(this.bomb)) {
            return;
        }
        // activate bomb
        bomb.activate(player);
        System.out.println(String.format("Bomb placed at %d x %d", this.getX(), this.getY()));
    }
}
