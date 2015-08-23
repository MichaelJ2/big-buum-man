package at.mgm.bbm.core.map;

import at.mgm.bbm.core.events.EventExecutor;
import at.mgm.bbm.core.objects.DisplayObject;
import at.mgm.bbm.core.objects.fields.Field;
import at.mgm.bbm.core.objects.gameobjects.Bomb;
import at.mgm.bbm.core.objects.gameobjects.Player;

import java.util.ArrayList;
import java.util.List;

public enum ObjectMap {

    INSTANCE;

    public static final long UPDATE_INTERVAL = 50;

    ObjectMap() {
        this.displayObjectList = new ArrayList<DisplayObject>();
        this.playerList = new ArrayList<Player>();
        this.start();
    }

    private volatile List<DisplayObject> displayObjectList;
    private volatile List<Player> playerList;

    private boolean enabled = false;

    public int getObjectCount() {
        return this.displayObjectList.size();
    }

    public boolean addObject(final DisplayObject paramDisplayObject) {
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

    public boolean addPlayer(final Player paramPlayer) {
        boolean success = false;
        if (null != paramPlayer) {
            this.playerList.add(paramPlayer);
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

    public boolean isTaken(final int paramX, final int paramY) {
        for (final DisplayObject displayObject : this.displayObjectList) {
            if (displayObject.x == paramX && displayObject.y == paramY) {
                return true;
            }
        }
        return false;
    }

    public void removeObject(final int paramX, final int paramY) {
        for (final DisplayObject displayObject : this.displayObjectList) {
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

    public void movePlayer(final Player paramPlayer, final int paramHorizontalSteps, final int paramVerticalSteps) {
        final int newX = paramPlayer.x + paramHorizontalSteps;
        final int newY = paramPlayer.y + paramVerticalSteps;
        final Field field = VMap.INSTANCE.getField(newX, newY);
        if (null != field && field.acceptPlayer) {
            paramPlayer.x = newX;
            paramPlayer.y = newY;
        }
    }

    public void start() {
        if (!this.enabled) {
            System.out.println("Starting new object executor!");
            this.enabled = true;
            this.startExecutor();
        }
    }

    public void stop() {
        System.out.println("Stopping current object executor!");
        this.enabled = false;
    }

    private void startExecutor() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("New object executor started!");
                while (enabled) {
                    if (!displayObjectList.isEmpty()) {
                        // handle objects and player-object-collision
                        collisionCheck:
                        for (final DisplayObject displayObject : displayObjectList) {
                            for (final Player player : playerList) {
                                if (!(displayObject instanceof Bomb) && displayObject.x == player.x && displayObject.y == player.y) {
                                    EventExecutor.INSTANCE.createEvent(player, displayObject);
                                    break collisionCheck;
                                }
                            }
                        }
                    }

                    // wait 50ms until next check
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
