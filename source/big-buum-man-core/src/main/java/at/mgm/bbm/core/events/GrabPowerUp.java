package at.mgm.bbm.core.events;

import at.mgm.bbm.core.objects.gameobjects.*;

public class GrabPowerUp extends Event {

    public final Player player;
    public final PowerUp powerUp;

    public GrabPowerUp(final Player paramPlayer, final PowerUp paramPowerUp) {
        super(EventType.GRAB_POWERDOWN, paramPlayer.x, paramPlayer.y, 0, EventShape.SQUARE);
        this.player = paramPlayer;
        this.powerUp = paramPowerUp;
        this.execute();
    }

    @Override
    public void execute() {
        // TODO: update player and remove object from map
        this.player.bombTimer = Bomb.TIMER_MIN;
        this.player.bombRange = Bomb.RANGE_MAX;
        final Player player = this.player;
        // update player's stats
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // wait until the duration is over
                    Thread.sleep(PowerUp.DEFAULT_DURATION);

                    // reset player stats
                    player.bombTimer = Bomb.TIMER_DEFAULT;
                    player.bombRange = Bomb.RANGE_DEFAULT;
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                    // reset player immediately stats if delayed reset was interrupted
                    player.bombTimer = Bomb.TIMER_DEFAULT;
                    player.bombRange = Bomb.RANGE_DEFAULT;
                }
            }
        }).start();
    }
}
