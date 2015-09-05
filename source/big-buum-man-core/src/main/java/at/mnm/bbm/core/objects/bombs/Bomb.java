package at.mnm.bbm.core.objects.bombs;

import at.mnm.bbm.core.events.DetonateBomb;
import at.mnm.bbm.core.events.shapes.EventShape;
import at.mnm.bbm.core.map.ObjectMap;
import at.mnm.bbm.core.objects.DisplayObject;
import at.mnm.bbm.core.objects.effects.EffectType;
import at.mnm.bbm.core.objects.GameObjectType;
import at.mnm.bbm.core.objects.characters.Player;

public abstract class Bomb extends DisplayObject {

    /**
     * Bomb constants.
     */
    public static final int DEFAULT_RANGE = 2;
    public static final long DEFAULT_TIMER = 2000;
    public static final int RANGE_MIN = 1;
    public static final int RANGE_MAX = 7;
    public static final long TIMER_MIN = 500;
    public static final long TIMER_MAX = 5000;

    /**
     * Bomb type vars.
     */
    public final BombType bombType;

    /**
     * Modifiable bomb type vars.
     */
    protected int range = DEFAULT_RANGE;
    protected long timer = DEFAULT_TIMER;

    protected EffectType effectType = EffectType.NONE;
    protected EventShape eventShape = EventShape.NONE;

    /**
     * Protected constructor only accessible for BombFactory.
     *
     * @param paramBombType The type of bomb.
     */
    protected Bomb(final BombType paramBombType, int paramX, final int paramY) {
        super(GameObjectType.BOMB, paramX, paramY);
        this.bombType = paramBombType;
        this.initBomb();
    }

    /**
     * Initialise the bomb with its type specific values.
     */
    protected abstract void initBomb();

    /**
     * Activate bomb using player's stats.
     *
     * @param paramPlayer
     */
    public final void activate(final Player paramPlayer) {
        if (null != paramPlayer) {
            this.timer = paramPlayer.getBombTimer();
            this.range = paramPlayer.getBombRange();
        }
        this.activate();
    }

    /**
     * Activate bomb using the bomb type's defaults.
     */
    public final void activate() {
        final Bomb innerBomb = this;
        // create new thread which executes the timer and detonation in background
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // wait until the bomb timer is over
                    Thread.sleep(innerBomb.timer);
                    // create new detonate event
                    new DetonateBomb(innerBomb, innerBomb.getX(), innerBomb.getY());
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    ObjectMap.INSTANCE.removeObject(innerBomb);
                }
            }
        }).start();
    }

    /**
     * Getter for the range.
     *
     * @return Range of fields.
     */
    public final int getRange() {
        return this.range;
    }

    /**
     * Setter for the range.
     *
     * @param paramRange Range of fields.
     */
    public final void setRange(int paramRange) {
        if (Bomb.RANGE_MIN >= paramRange && paramRange <= Bomb.RANGE_MAX) {
            this.range = paramRange;
        }
    }

    /**
     * Getter for the timer.
     *
     * @return Time in milliseconds.
     */
    public final long getTimer() {
        return this.timer;
    }

    /**
     * Update the timer.
     *
     * @param paramTimer Time in milliseconds.
     */
    public final void setTimer(long paramTimer) {
        if (Bomb.TIMER_MIN >= paramTimer && paramTimer <= Bomb.RANGE_MAX) {
            this.timer = paramTimer;
        }
    }

    public final void setXY(final int paramX, final int paramY) {
        this.setCoordinates(paramX, paramY);
    }

    public EffectType getEffectType() {
        return this.effectType;
    }

    public EventShape getEventShape() {
        return this.eventShape;
    }
}
