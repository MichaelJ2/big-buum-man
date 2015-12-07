package at.bbm.core.map;

public class Location {

    public int x = 0;
    public int y = 0;

    public Location() {

    }

    public Location(final int paramX, final int paramY) {
        this.x = paramX;
        this.y = paramY;
    }

    public boolean checkCollision(final Location paramLocation) {
        return this.x == paramLocation.x && this.y == paramLocation.y;
    }

}
