package at.mgm.bbm.core.map;

import at.mgm.bbm.core.events.EventExecutor;
import at.mgm.bbm.core.objects.DisplayObject;
import at.mgm.bbm.core.objects.fields.Field;
import at.mgm.bbm.core.objects.gameobjects.Bomb;
import at.mgm.bbm.core.objects.gameobjects.Player;

import java.util.*;

public enum ObjectMap {

    INSTANCE;

    public static final long UPDATE_INTERVAL = 20;

    ObjectMap() {
        this.displayObjectList = new ArrayList<DisplayObject>();
        this.playerList = new ArrayList<Player>();
        this.startObjectExecutor();
    }

    private final List<DisplayObject> displayObjectList;
    private final List<Player> playerList;

    private boolean enabled = false;

    public synchronized int getObjectCount() {
        return this.displayObjectList.size();
    }

    public synchronized boolean addObject(final DisplayObject paramDisplayObject) {
        boolean success = false;
        if (null != paramDisplayObject) {
            if (paramDisplayObject instanceof Player) {
                this.playerList.add((Player)paramDisplayObject);
                success = true;
            } else if (!this.isTaken(paramDisplayObject.x, paramDisplayObject.y)) {
                this.displayObjectList.add(paramDisplayObject);
                success = true;
            }
        }
        return success;
    }

    public synchronized boolean addPlayer(final Player paramPlayer) {
        boolean success = false;
        if (null != paramPlayer) {
            this.playerList.add(paramPlayer);
            success = true;
        }
        return success;
    }

    public synchronized DisplayObject getObject(final int paramX, final int paramY) {
        for (final DisplayObject displayObject : this.displayObjectList) {
            if (displayObject.x == paramX && displayObject.y == paramY) {
                return displayObject;
            }
        }
        return null;
    }

    public synchronized boolean isTaken(final int paramX, final int paramY) {
        for (final DisplayObject displayObject : this.displayObjectList) {
            if (displayObject.x == paramX && displayObject.y == paramY) {
                return true;
            }
        }
        return false;
    }

    public synchronized void removeObject(final int paramX, final int paramY) {
        for (final Iterator<DisplayObject> objectIterator = this.displayObjectList.iterator(); objectIterator.hasNext();) {
            final DisplayObject displayObject = objectIterator.next();
            if (displayObject.x == paramX && displayObject.y == paramY) {
                this.displayObjectList.remove(displayObject);
            }
        }
    }

    public synchronized void removeObject(final DisplayObject paramDisplayObject) {
        if (null != paramDisplayObject && !(paramDisplayObject instanceof Player)) {
            this.displayObjectList.remove(paramDisplayObject);
        }
    }

    public synchronized void removePlayer(final Player paramPlayer) {
        if (null != paramPlayer) {
            this.playerList.remove(paramPlayer);
        }
    }

    public synchronized void movePlayer(final Player paramPlayer, final int paramHorizontalSteps, final int paramVerticalSteps) {
        final int newX = paramPlayer.x + paramHorizontalSteps;
        final int newY = paramPlayer.y + paramVerticalSteps;
        final Field field = VMap.INSTANCE.getField(newX, newY);
        if (null != field && field.acceptPlayer) {
            paramPlayer.x = newX;
            paramPlayer.y = newY;
        }
    }

    public synchronized void startObjectExecutor() {
        if (!this.enabled) {
            System.out.println("Starting new object executor!");
            this.enabled = true;
            this.startExecutor();
        }
    }

    public synchronized void stopObjectExecutor() {
        System.out.println("Stopping current object executor!");
        this.enabled = false;
    }

    private void startExecutor() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("New object executor started!");
                final Map<Player, DisplayObject> events = new HashMap<Player, DisplayObject>();
                while (enabled) {
                    if (!displayObjectList.isEmpty() && !playerList.isEmpty()) {
                        // handle player-object-collision
                        for (Iterator<DisplayObject> objectIterator = displayObjectList.iterator(); objectIterator.hasNext();) {
                            final DisplayObject displayObject = objectIterator.next();
                            for (Iterator<Player> playerIterator = playerList.iterator(); playerIterator.hasNext();) {
                                final Player player = playerIterator.next();
                                if (!(displayObject instanceof Bomb) && displayObject.x == player.x && displayObject.y == player.y) {
                                    events.put(player, displayObject);
                                }
                            }
                        }

                        // create events for each collision
                        final Iterator iterator = events.entrySet().iterator();
                        while (iterator.hasNext()) {
                            final Map.Entry pair = (Map.Entry)iterator.next();
                            EventExecutor.INSTANCE.createEvent((Player)pair.getKey(), (DisplayObject)pair.getValue());
                            iterator.remove();
                        }
                    }

                    // wait until next check
                    try {
                        Thread.sleep(UPDATE_INTERVAL);
                    } catch (final InterruptedException e) {
                        // ignore exceptions
                    }
                }
                System.out.println("Current object executor stopped!");
            }
        }).start();
    }
}
