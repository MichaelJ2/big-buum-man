package at.mgm.bbm.core.objects.gameobjects;

import at.mgm.bbm.core.events.DetonateBomb;
import at.mgm.bbm.core.events.EventType;
import at.mgm.bbm.core.events.PlaceBomb;
import at.mgm.bbm.core.map.ObjectMap;
import at.mgm.bbm.core.objects.DisplayObject;

public class Player extends DisplayObject {

    public static final String TEXTURE = "textures/player.png";
    public static final long DEFAULT_COOLDOWN = 5000;

    public boolean alive = true;
    public int bombRange = Bomb.RANGE_DEFAULT;
    public int bombTimer = Bomb.TIMER_DEFAULT;
    public EventShape bombDetonationShape = EventShape.CROSS;

    private boolean hasBomb = true;

    private boolean walks = false;

    public Player(final int paramX, final int paramY) {
        super(GameObjectType.PLAYER, paramX, paramY, 0);
    }

    public void move(final int paramHorizontalSteps, final int paramVerticalSteps, final boolean paramIsSingleStep) {
        if (paramIsSingleStep) {
            ObjectMap.INSTANCE.movePlayer(this, paramHorizontalSteps, paramVerticalSteps);
        } else if (!this.walks){
            this.walks = true;
        }
    }

    public void placeBomb() {
        if (this.hasBomb) {
            this.hasBomb = false;
            new PlaceBomb(this);
            final Player player = this;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // wait until the player gets a new bomb
                        Thread.sleep(DEFAULT_COOLDOWN);

                        // enable bombs for this player
                        player.hasBomb = true;
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public void updateBombRange(final int paramBombRange) {
        if (paramBombRange > 2) {
            this.bombRange = paramBombRange;
        }
    }

    public void updateBombTimer(final int paramBombTimer) {
        if (paramBombTimer > 500) {
            this.bombTimer = paramBombTimer;
        }
    }
}
