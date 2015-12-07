package at.bbm.core.events;

interface FutureEventInterface extends BaseEvent {

    /** Execute command */
    boolean execute(final long delta);
}
