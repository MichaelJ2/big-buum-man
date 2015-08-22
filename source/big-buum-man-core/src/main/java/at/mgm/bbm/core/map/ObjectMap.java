package at.mgm.bbm.core.map;

import at.mgm.bbm.core.GameObject;
import at.mgm.bbm.core.objects.DisplayObject;
import at.mgm.bbm.core.objects.fields.Field;
import at.mgm.bbm.core.objects.gameobjects.Player;

import java.util.ArrayList;
import java.util.List;

public enum ObjectMap {

    INSTANCE;

    ObjectMap() {
        displayObjectList = new ArrayList<DisplayObject>();
    }

    private final List<DisplayObject> displayObjectList;

    public int getObjectCount() {
        return this.displayObjectList.size();
    }

    public boolean addObject(final DisplayObject paramDisplayObject) {
        boolean success = false;
        if (null != paramDisplayObject && !this.isTaken(paramDisplayObject.x, paramDisplayObject.y)) {
            this.displayObjectList.add(paramDisplayObject);
            success = true;
        }
        return success;
    }

    public DisplayObject getObject(final int paramX, final int paramY) {
        for (final DisplayObject displayObject : this.displayObjectList) {
            if (displayObject.x == paramX && displayObject.y == paramY) {
                return displayObject;
            }
        }
        return null;
    }

    public void removeObject(final int paramX, final int paramY) {
        for (final DisplayObject displayObject : this.displayObjectList) {
            if (displayObject.x == paramX && displayObject.y == paramY) {
                this.displayObjectList.remove(displayObject);
            }
        }
    }

    public boolean isTaken(final int paramX, final int paramY) {
        for (final DisplayObject displayObject : this.displayObjectList) {
            if (displayObject.x == paramX && displayObject.y == paramY) {
                return true;
            }
        }
        return false;
    }

    public synchronized void removeObject(final DisplayObject paramDisplayObject) {
        this.displayObjectList.remove(paramDisplayObject);
    }

    public void movePlayer(final Player paramPlayer, final int paramHorizontalSteps, final int paramVerticalSteps) {
        final int newX = paramPlayer.x + paramHorizontalSteps;
        final int newY = paramPlayer.y + paramVerticalSteps;
        final Field field = VMap.INSTANCE.getField(newX, newY);
        if (null != field && field.acceptPlayer) {
            paramPlayer.x = newX;
            paramPlayer.y = newY;
        }
    }
}
