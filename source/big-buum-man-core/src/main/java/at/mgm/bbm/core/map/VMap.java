package at.mgm.bbm.core.map;

import at.mgm.bbm.core.events.Event;
import at.mgm.bbm.core.objects.fields.Field;
import at.mgm.bbm.core.objects.fields.FieldType;
import at.mgm.bbm.core.objects.fields.FieldFactory;
import at.mgm.bbm.core.utils.FileUtils;

import java.io.File;

public enum VMap {

    INSTANCE;

    private String mapName = "DEFAULT";
    public volatile Field[][] virtualMap = new Field[0][0];

    VMap() {
        this.loadMap(9, 9);
    }

    public String getMapName() {
        return this.mapName;
    }

    public void setMapName(final String paramMapName) {
        this.mapName = paramMapName;
    }

    public void loadMap(final int paramWidth, final int paramHeigth) {
        this.virtualMap = new Field[paramHeigth][paramWidth];
        for (int y = 0; y < paramHeigth; y++) {
            for (int x = 0; x < paramWidth; x++) {
                this.virtualMap[y][x] = FieldFactory.getField(FieldType.GROUND);
            }
        }
    }

    public boolean loadMap(final File paramFile) {
        boolean result = false;

        Field[][] map = FileUtils.INSTANCE.loadMap(paramFile);

        if (null != map) {
            this.virtualMap = map;
            result = true;
        }

        return result;
    }

    public boolean saveMap(final String paramMapName) {
        this.mapName = paramMapName;
        return FileUtils.INSTANCE.saveMap(this.virtualMap);
    }


    public synchronized Field getField(final int paramX, final int paramY) {
        Field field = null;
        if (paramX >= 0 && paramX < this.virtualMap[0].length) {
            if (paramY >= 0 && paramY < this.virtualMap.length) {
                field = this.virtualMap[paramX][paramY];
            }
        }
        return field;
    }

    public synchronized void updateField(final int paramX, final int paramY, final Field paramField) {
        this.virtualMap[paramX][paramY] = paramField;
    }

    public synchronized boolean triggerEvents(final Event paramEvent) {
        return this.triggerEvent(paramEvent);
    }

    private boolean triggerEvent(final Event paramEvent) {
        boolean success = false;
        final int X = paramEvent.x;
        final int Y = paramEvent.y;
        switch (paramEvent.eventType) {
            case PLACE_BOMB:
                break;
            case DETONATE_BOMB:
                break;
            case FIRE:
                final Field field = this.getField(X, Y);
                if (this.fireOnField(X, Y)) {
                    success = true;
                    if (field.decreaseDurability() == 0) {
                    }
                }
                break;
        }
        return success;
    }

    private boolean fireOnField(final int paramX, final int paramY) {
        final Field field = this.getField(paramX, paramY);
        final boolean fire = field.acceptFire;
        if (fire) {
            if (field.decreaseDurability() == 0) {
                this.virtualMap[paramX][paramY] = FieldFactory.getField(FieldType.GROUND);
            }
        }
        return fire;
    }

    private boolean playerOnField(final int paramX, final int paramY) {
        final Field field = this.getField(paramX, paramY);
        final boolean player = field.acceptPlayer;
        return player;
    }
}
