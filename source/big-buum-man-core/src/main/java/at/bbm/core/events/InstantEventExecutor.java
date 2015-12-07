package at.bbm.core.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class InstantEventExecutor implements Runnable {

    private static final Logger logger = LogManager.getLogger(InstantEventExecutor.class.getName());

    /** Global Singleton Instance */
    private static InstantEventExecutor instance;

    /** List of InstantEvents */
    private final List<InstantEvent> events = new ArrayList<>();
    private final List<InstantEvent> tmp = new ArrayList<>();

    /** @TODO */
    private volatile boolean idle = true;

    /** @TODO */
    private volatile boolean running = false;

    /** Constructor */
    private InstantEventExecutor() {
        // defeat instantiation
    }

    /** get Singleton instance */
    public static InstantEventExecutor getInstance() {
        if (null == instance) {
            instance = new InstantEventExecutor();
        }
        return instance;
    }

    /** Add command for execution */
    public synchronized void addEvent(final InstantEvent paramEvent) {
        if (this.idle) {
            this.events.add(paramEvent);
        } else {
            this.tmp.add(paramEvent);
        }
    }

    /** Execute each command */
    public synchronized void executeEvents() {
        // lock
        this.idle = false;

        // execute all events
        final Iterator<InstantEvent> iterator = this.events.iterator();
        while (iterator.hasNext()) {
            final InstantEvent event = iterator.next();
            try {
                if (event.execute()) {
                    iterator.remove();
                }
            } catch (final Exception e) {

            }
        }

        // move events
        synchronized (this.tmp) {
            this.events.addAll(this.tmp);
            this.tmp.clear();
        }

        // unlock
        this.idle = true;
    }

    public void start() {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void stop() {

    }

    @Override
    public void run() {
        while (this.running) {
            this.executeEvents();
        }
    }
}
