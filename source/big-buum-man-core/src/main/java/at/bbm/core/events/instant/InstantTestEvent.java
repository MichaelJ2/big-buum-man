package at.bbm.core.events.instant;

import at.bbm.core.events.InstantEvent;

public class InstantTestEvent extends InstantEvent {

    @Override
    public boolean execute() {
        return false;
    }
}
