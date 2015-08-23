package at.mgm.bbm.core.objects.gameobjects;

import at.mgm.bbm.core.events.DetonateBomb;
import at.mgm.bbm.core.map.ObjectMap;
import at.mgm.bbm.core.objects.DisplayObject;

public class Bomb extends DisplayObject {

    public static final String TEXTURE = "textures/bomb.png";

    public static final int RANGE_MIN = 1;
    public static final int RANGE_DEFAULT = 2;
    public static final int RANGE_MAX = 5;
    public static final long TIMER_MIN = 500;
    public static final long TIMER_DEFAULT = 2000;
    public static final long TIMER_MAX = 5000;

    public EventShape bombDetonationShape = EventShape.CROSS;

    public Bomb(final Player paramPlayer) {
        super(GameObjectType.BOMB, paramPlayer.x, paramPlayer.y, paramPlayer.getBombRange());
        this.timerInMillis = paramPlayer.getBombTimer();
        this.bombDetonationShape = paramPlayer.bombDetonationShape;
    }

    public void activate() {
        final Bomb bomb = this;
        // create new thread which executes the timer and detonation in background
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // wait until the bomb timer is over
                    Thread.sleep(timerInMillis);

                    // create new detonate event
                    new DetonateBomb(bomb);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                    ObjectMap.INSTANCE.removeObject(bomb);
                }
            }
        }).start();
    }
}
