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
        // remove PowerUp from map
        ObjectMap.INSTANCE.removeObject(this.powerDown);
        System.out.println(String.format("Player %s picked up PowerDown at %d x %d", this.player.toString(), this.powerDown.x, this.powerDown.y));
        // update player's stats
        this.player.setBombTimer(Bomb.TIMER_MAX);
        this.player.setBombRange(Bomb.RANGE_MIN);
        final Player player = this.player;
        // handle duration
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // wait until the duration is over
                    Thread.sleep(PowerDown.DEFAULT_DURATION);

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
