package at.mgm.bbm.core.events;

import at.mgm.bbm.core.map.ObjectMap;
import at.mgm.bbm.core.objects.gameobjects.*;

public class GrabPowerDown extends Event {

    public final Player player;
    public final PowerDown powerDown;

    public GrabPowerDown(final Player paramPlayer, final PowerDown paramPowerDown) {
        super(EventType.GRAB_POWERDOWN, paramPlayer.x, paramPlayer.y, 0, EventShape.SQUARE);
        this.player = paramPlayer;
        this.powerDown = paramPowerDown;
        this.execute();
    }

    @Override
    public void execute() {
        // update player's stats
        this.player.bombTimer = Bomb.TIMER_MAX;
        this.player.bombRange = Bomb.RANGE_MIN;
        final Player player = this.player;

        // reset player's stats
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // wait until the duration is over
                    Thread.sleep(PowerDown.DEFAULT_DURATION);

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

        // remove object from map
        ObjectMap.INSTANCE.removeObject(this.powerDown);
    }
}
