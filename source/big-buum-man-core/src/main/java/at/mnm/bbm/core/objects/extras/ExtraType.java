package at.mnm.bbm.core.objects.extras;

import at.mnm.bbm.core.objects.bombs.Bomb;

public enum ExtraType {

    /**
     * Nothing happens.
     */
    NONE,

    //
    // # # #   P O W E R   U P S   # # #
    //

    /**
     * Player moves faster.
     */
    SPEEDUP,

    /**
     * Bombs won't hurt their owner.
     */
    FRIENDLY_BOMBS,

    /**
     * Player becomes invincible.
     */
    INVINCIBILITY,

    /**
     * Bombs do more damage on fields.
     */
    BOMBS_DAMAGE,

    /**
     * Bombs have higher ranges.
     */
    BOMBS_RANGE,

    /**
     * Player reloads bombs faster.
     */
    FAST_RELOADING,

    /**
     * Airstrike at random location, won't kill owner.
     */
    AIRSTRIKE,

    /**
     * Player becomes invisible.
     */
    INVISIBILITY,

    /**
     * Player gains one life point.
     */
    EXTRA_LIFE,

    /**
     * Player's next bomb will be a claymore.
     */
    CLAYMORE,

    /**
     * Player gains a shield.
     */
    SHIELD,

    /**
     * Player can control a random enemy's character (while being invincible).
     */
    MASTERMIND,

    /**
     * Player's next bomb will win the game.
     */
    NUKE,

    //
    // # # #   P O W E R   D O W N S   # # #
    //

    /**
     * Slows down player.
     */
    SLOWDOWN,

    /**
     * Bomb immediately detonates and kills it's owner.
     */
    MISFIRE,

    /**
     * Bomb does not explode.
     */
    FAKE,

    /**
     * Player can't reload bombs.
     */
    BOMBS_WEAR,

    /**
     * Player gets stunned.
     */
    STUN,

    /**
     * Bombs are creating a wall on detonation.
     */
    WALL_BOMBS,

    /**
     * The Player gets confused and runs in random directions.
     */
    CONFUSION;

}
