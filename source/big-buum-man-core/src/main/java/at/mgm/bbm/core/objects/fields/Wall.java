package at.mgm.bbm.core.objects.fields;

public class Wall extends Field {

    public static final int ID = 3;
    public static final String TEXTURE = "textures/wall.png";
    public static final boolean LOCKED = false;
    public static final int DURABILITY = 1;
    public static final boolean ACCEPT_PLAYER = false;
    public static final boolean ACCEPT_FIRE = true;
    public static final double SPEED = 0.0;

    protected Wall() {
        super(FieldType.WALL, TEXTURE, LOCKED, DURABILITY, ACCEPT_PLAYER, ACCEPT_FIRE, SPEED);
    }
}
