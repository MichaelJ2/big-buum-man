package at.bbm.core.map;

import at.bbm.core.objects.GameObject;
import at.bbm.core.objects.fields.Field;

public class Map {

    private static Map instance;

    private volatile MapObject map;
    private volatile boolean updated = false;

    /** Constructors */
    private Map() {
        this.map = new MapObject();
        this.map.loadDefaultMap();
    }

    /**Getters */
    public static Map getInstance() {
        if (null == instance) {
            instance = new Map();
        }
        return instance;
    }

    public void setMap(final MapObject paramMap) {
        this.map = paramMap;
    }

    public MapObject getMap() {
        return this.map;
    }

    public boolean isUpdated() {
        return this.updated;
    }

    /** Methods */
    public void recreateMap(final int paramHeight, final int paramWidth) {
        this.map.loadMap(paramHeight, paramWidth);
    }

    public void updateField(final Location paramLocation, final Field paramField) {
        if (null != paramField) {
            this.map.map[paramLocation.y][paramLocation.x].setField(paramField);
        }
    }

    public void placeObject(final Location paramLocation, final GameObject paramGameObject) {
        if (null != paramGameObject) {
            final MapField mapField = this.map.map[paramLocation.y][paramLocation.x];
            if (!mapField.hasObject) {
                mapField.setObject(paramGameObject);
            }
        }
    }

    public boolean hasObject(final Location paramLocation) {
        return this.map.map[paramLocation.y][paramLocation.x].hasObject;
    }

    public GameObject getObject(final Location paramLocation) {
        return this.map.map[paramLocation.y][paramLocation.x].getObject();
    }

    public void setObject(final Location paramLocation, final GameObject paramGameObject) {
        this.map.map[paramLocation.y][paramLocation.x].setObject(paramGameObject);
    }

    public void removeObject(final Location paramLocation) {
        this.map.map[paramLocation.y][paramLocation.x].setObject(null);
    }

    public MapField getMapField(final Location paramLocation) {
        return this.map.map[paramLocation.y][paramLocation.x];
    }
}
