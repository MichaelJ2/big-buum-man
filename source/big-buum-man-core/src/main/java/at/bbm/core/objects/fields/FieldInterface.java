package at.bbm.core.objects.fields;

import at.bbm.core.events.EventType;
import at.bbm.core.objects.players.Player;

public interface FieldInterface {

    void handleEvent(final EventType paramEventType);

    void updatePlayer(final Player paramPlayer);
}
