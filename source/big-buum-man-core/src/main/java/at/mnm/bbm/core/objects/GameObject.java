package at.mnm.bbm.core.objects;

public abstract class GameObject {

    private int x;
    private int y;

    protected GameObject(final int paramX, final int paramY) {
        this.x = paramX;
        this.y = paramY;
    }

    public int getX() {
        return this.x;
    }

    public final void setX(int paramX) {
        if (paramX >= 0) {
            this.x = paramX;
        }
    }

    public final int getY() {
        return this.y;
    }

    public final void setY(int paramY) {
        if (paramY >= 0) {
            this.y = paramY;
        }
    }

    public final void setCoordinates(final int paramX, final int paramY) {
        if (paramX >= 0 && paramY >= 0) {
            this.x = paramX;
            this.y = paramY;
        }
    }

    public final boolean checkCollision(final int paramX, final int paramY) {
        return (paramX == this.x && paramY == this.y);
    }
}
