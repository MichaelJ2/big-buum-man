package at.bbm.core.events.future;

import at.bbm.core.events.FutureEvent;

public class FutureTestEvent extends FutureEvent {

    FutureTestEvent(final long paramNanosInFuture) {
        super(paramNanosInFuture);
    }

    @Override
    public boolean execute(long delta) {
        return false;
    }
}
