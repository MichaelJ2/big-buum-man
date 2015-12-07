package at.bbm.core.events;

public interface EventExecutorInterface extends Runnable {

    void start();

    void pause();

    void resume();

    void stop();

    void addEvent(final BaseEvent paramBaseEvent);

    void executeEvents();

}
