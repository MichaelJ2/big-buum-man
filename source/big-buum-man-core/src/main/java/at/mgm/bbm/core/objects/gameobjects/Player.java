package at.mgm.bbm.core.objects.gameobjects;

import at.mgm.bbm.core.events.PlaceBomb;
import at.mgm.bbm.core.map.ObjectMap;
import at.mgm.bbm.core.objects.DisplayObject;

public class Player extends DisplayObject {

    public static final String TEXTURE = "textures/player.png";
    public static final long DEFAULT_BOMB_REFILL_COOLDOWN = 3500;
    public static final long STEP_DURATION = 100;

    public boolean alive = true;
    public int bombs = 3;

    private int bombRange = Bomb.RANGE_DEFAULT;
    private long bombTimer = Bomb.TIMER_DEFAULT;
    public EventShape bombDetonationShape = EventShape.CROSS;

    private boolean walks = false;

    public Player(final int paramX, final int paramY) {
        super(GameObjectType.PLAYER, paramX, paramY, 0);
        ObjectMap.INSTANCE.addPlayer(this);
    }

    public synchronized void move(final int paramHorizontalSteps, final int paramVerticalSteps, final boolean paramIsSingleStep) {
        if (paramIsSingleStep) {
            ObjectMap.INSTANCE.movePlayer(this, paramHorizontalSteps, paramVerticalSteps);
        } else if (!this.walks){
            this.walks = true;
            ObjectMap.INSTANCE.movePlayer(this, paramHorizontalSteps, paramVerticalSteps);
            final Player player = this;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // wait until the player took the step
                        Thread.sleep(STEP_DURATION);

                        // reset so the player can take it's next step
                        player.walks = false;
                    } catch (final InterruptedException e) {
                        player.walks = false;
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public synchronized void placeBomb() {
        if (this.bombs > 0) {
            new PlaceBomb(this);
            final Player player = this;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // wait until the player gets a new bomb
                        Thread.sleep(DEFAULT_BOMB_REFILL_COOLDOWN);

                        // refill one bomb to the player's inventory
                        player.bombs++;
                    } catch (final InterruptedException e) {
                        player.bombs++;
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public int getBombRange() {
        return this.bombRange;
    }

    public void setBombRange(final int paramBombRange) {
        if (paramBombRange >= Bomb.RANGE_MIN) {
            this.bombRange = paramBombRange;
        }
    }

    public long getBombTimer() {
        return this.bombTimer;
    }

    public void setBombTimer(final long paramBombTimer) {
        if (paramBombTimer >= Bomb.TIMER_MIN) {
            this.bombTimer = paramBombTimer;
        }
    }

    public void die() {
        this.alive = false;
        ObjectMap.INSTANCE.removePlayer(this);
    }
}
