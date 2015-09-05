package at.mnm.bbm.core.events;

import at.mnm.bbm.core.map.ObjectMap;
import at.mnm.bbm.core.objects.bombs.Bomb;
import at.mnm.bbm.core.objects.effects.EffectType;
import at.mnm.bbm.core.objects.extras.Extra;
import at.mnm.bbm.core.objects.characters.Player;

public class GrabExtra extends Event {

    public static final EffectType EFFECT_TYPE = EffectType.GLITTER;

    public final Player player;
    public final Extra extra;

    public GrabExtra(final Player paramPlayer, final Extra paramExtra) {
        super(EventType.GRAB_EXTRA, paramPlayer.getX(), paramPlayer.getY());
        this.player = paramPlayer;
        this.extra = paramExtra;
    }

    @Override
    protected void initEvent() {
        this.effectType = EFFECT_TYPE;
    }

    @Override
    public void execute() {
        // remove PowerUp from map
        ObjectMap.INSTANCE.removeObject(this.extra);
        System.out.println(String.format("Player %s picked up Extra \"%s\"at %d x %d", this.player.toString(), this.getX(), this.getY()));
        // update player's stats
        this.extra.updatePlayerStats(this.player);
        final Player player = this.player;
        // handle duration
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // wait until the duration is over
                    Thread.sleep(Extra.DEFAULT_DURATION);

                    // reset player stats
                    player.setBombTimer(Bomb.DEFAULT_TIMER);
                    player.setBombRange(Bomb.DEFAULT_RANGE);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                    // reset player immediately stats if delayed reset was interrupted
                    player.setBombTimer(Bomb.DEFAULT_TIMER);
                    player.setBombRange(Bomb.DEFAULT_RANGE);
                }
            }
        }).start();
    }
}
