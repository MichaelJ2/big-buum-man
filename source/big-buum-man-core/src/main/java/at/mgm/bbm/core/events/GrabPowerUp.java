package at.mgm.bbm.core.events;

import at.mgm.bbm.core.map.ObjectMap;
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
        // remove PowerUp from map
        ObjectMap.INSTANCE.removeObject(this.powerUp);
        System.out.println(String.format("Player %s picked up PowerUp at %d x %d", this.player.toString(), this.powerUp.x, this.powerUp.y));
        // update player's stats
        this.player.setBombTimer(Bomb.TIMER_MIN);
        this.player.setBombRange(Bomb.RANGE_MAX);
        final Player player = this.player;
        // handle duration
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // wait until the duration is over
                    Thread.sleep(PowerUp.DEFAULT_DURATION);

                    // reset player stats
                    player.setBombTimer(Bomb.TIMER_DEFAULT);
                    player.setBombRange(Bomb.RANGE_DEFAULT);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                    // reset player immediately stats if delayed reset was interrupted
                    player.setBombTimer(Bomb.TIMER_DEFAULT);
                    player.setBombRange(Bomb.RANGE_DEFAULT);
                }
            }
        }).start();
    }
}
