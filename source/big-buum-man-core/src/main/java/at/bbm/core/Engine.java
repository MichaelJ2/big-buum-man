package at.bbm.core;

import at.bbm.core.events.InstantEventExecutor;

class Engine implements Runnable {

    private final InstantEventExecutor instantEventExecutor = InstantEventExecutor.getInstance();

    private boolean running = true;

    final public void run() {
        while (true) {
            if (this.running) {
                this.instantEventExecutor.executeEvents();
            } else {
                try {
                    // sleep 100ms
                    Thread.sleep(100l);
                } catch (InterruptedException e) {
                    // ignore exceptions
                }
            }
        }
    }

    public void pause() {
        this.running = false;
    }

    public void resume() {

    }
}
