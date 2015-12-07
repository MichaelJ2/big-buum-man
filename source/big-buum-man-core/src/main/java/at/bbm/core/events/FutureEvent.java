package at.bbm.core.events;

public abstract class FutureEvent implements FutureEventInterface {

    private final long nanosInFuture;
    public final EventType eventType = EventType.FUTURE;

    public FutureEvent(final long paramNanosInFuture) {
        this.nanosInFuture = paramNanosInFuture;
    }

}
