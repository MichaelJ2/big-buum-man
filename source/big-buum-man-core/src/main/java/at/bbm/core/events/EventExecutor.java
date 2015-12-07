package at.bbm.core.events;

public abstract class EventExecutor {

    /** Global Singleton Instance */
    private static EventExecutor instance;

    private InstantEventExecutor instantEventExecutor = InstantEventExecutor.getInstance();
    private Thread instantEventExecutorThread = new Thread(instantEventExecutor);
    private FutureEventExecutor futureEventExecutor = FutureEventExecutor.getInstance();
    private Thread futureEventExecutorThread = new Thread(futureEventExecutor);

    public void start() {
        this.instantEventExecutor.start();
        this.futureEventExecutor.start();
    }

    public void pause() {
        this.instantEventExecutor.pause();
        this.futureEventExecutor.pause();
    }

    public void resume() {
        this.instantEventExecutor.resume();
        this.futureEventExecutor.resume();
    }

    public void stop() {
        this.instantEventExecutor.stop();
        this.futureEventExecutor.stop();
    }

    public void addEvent(final BaseEvent paramBaseEvent) {
        if (paramBaseEvent instanceof InstantEvent) {

        }
    }

    public void executeEvents() {

    }
}
