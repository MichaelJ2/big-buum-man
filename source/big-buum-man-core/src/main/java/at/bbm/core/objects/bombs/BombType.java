package at.bbm.core.objects.bombs;

public enum BombType {

    /**
     * Default bomb.
     */
    DEFAULT,

    /**
     * Freezes players.
     */
    ICE,

    /**
     * Players can fly over massive fields.
     */
    FLY,

    /**
     * Speeds up player movements.
     */
    SPEED,

    /**
     * Players get teleported to random locations.
     */
    TELEPORT,

    /**
     * Players can move through massive fields.
     */
    GHOST,

    /**
     * Places water on map.
     */
    WATER,

    /**
     * Won't hurt it's owner.
     */
    FRIENDLY,

    /**
     * Owner instantly wins the game.
     */
    NUKE;

}
