package at.bbm.core.map;

import at.bbm.core.events.EventType;
import at.bbm.core.objects.GameObject;
import at.bbm.core.objects.fields.Field;
import at.bbm.core.objects.fields.FieldFactory;
import at.bbm.core.objects.fields.FieldType;
import at.bbm.core.objects.players.Player;

/**
 * This object represents a single field on the map.
 * It contains:
 *
 * Field:   the Field this MapField represents
 * Object:  the GameObject this MapField holds
 * Player:  the Player currently standing on this MapField
 */
public class MapField {

    private Field field;
    private GameObject object;
    private Player player;

    public volatile boolean hasPlayer = false;
    public volatile boolean hasObject = false;

    /** Constructors */
    public MapField() {
        // not allowed
    }

    public MapField(final Field paramField) {
        this.field = paramField;
    }

    /** Field Accessors */
    public void setField(final Field paramField) {
        this.field = paramField;
    }

    public Field getField() {
        return this.field;
    }

    /** Object Accessors */
    public void setObject(final GameObject paramObject) {
        this.object = paramObject;
    }

    public GameObject getObject() {
        return this.object;
    }

    /** Player Accessors */
    public void setPlayer(final Player paramPlayer) {
        this.player = paramPlayer;
    }

    public Player getPlayer() {
        return this.player;
    }

    /** Handle Events */
    public boolean handleEvent(final EventType paramEventType) {
        boolean updated = false;
        switch (paramEventType) {
            case FIRE:
                if (this.field.decreaseDurability() == 0) {
                    // TODO: set to default
                    this.setField(FieldFactory.getField(FieldType.GROUND));
                    updated = true;
                }
                break;
            default:
                break;
        }
        return updated;
    }
}
