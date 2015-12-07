package at.bbm.core.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FutureEventExecutor implements EventExecutorInterface {

    /** Global Singleton Instance */
    private static FutureEventExecutor instance;

    final List<FutureEvent> futureEvents = new ArrayList<>();
    final List<FutureEvent> tmp = new ArrayList<>();

    /** Executor time delta */
    private long lastExecutionTime;

    /** Executor time delta */
    private long timeDelta = 0;

    private boolean running = true;

    private boolean idle = true;

    // Constructor
    private FutureEventExecutor() {
        // defeat instantiation
        this.lastExecutionTime = System.nanoTime();
    }

    // get Singleton instance
    public static FutureEventExecutor getInstance() {
        if (null == instance) {
            instance = new FutureEventExecutor();
        }
        return instance;
    }

    public void addFutureEvent(final FutureEvent paramFutureEvent) {
        if (this.idle) {
            this.futureEvents.add(paramFutureEvent);
        } else {
            this.tmp.add(paramFutureEvent);
        }
    }

    @Override
    public void start() {

    }

    public void pause() {
        this.running = false;
    }

    public void resume() {
        this.running = true;
        this.lastExecutionTime = System.nanoTime();
        this.run();
    }

    @Override
    public void stop() {

    }

    @Override
    public void addEvent(BaseEvent paramBaseEvent) {

    }

    @Override
    public void executeEvents() {

    }

    private void executeCommands() {
        // lock
        this.idle = false;

        // execute all events
        final Iterator<FutureEvent> iterator = this.futureEvents.iterator();
        while (iterator.hasNext()) {
            final FutureEvent futureEvent = iterator.next();
            this.timeDelta = System.nanoTime() - this.lastExecutionTime;
            if (futureEvent.execute(timeDelta)) {
                iterator.remove();
            }
        }

        // move events
        synchronized (this.tmp) {
            this.futureEvents.addAll(this.tmp);
            this.tmp.clear();
        }

        // unlock
        this.idle = false;

        // time between command executions
        this.lastExecutionTime = System.nanoTime();

    }

    @Override
    public void run() {
        while (this.running) {
            this.executeCommands();
            // wait 10ms
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // nothing
            }
        }
    }
}