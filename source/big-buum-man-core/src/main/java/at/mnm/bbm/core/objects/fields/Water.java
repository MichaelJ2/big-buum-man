package at.mnm.bbm.core.objects.fields;

public class Water extends Field {

    public static final int ID = 5;
    public static final String TEXTURE = "textures/water.png";
    public static final boolean LOCKED = false;
    public static final int DURABILITY = 3;
    public static final boolean ACCEPT_PLAYER = true;
    public static final boolean ACCEPT_FIRE = true;
    public static final double SPEED = 0.5;

    protected Water() {
        super(FieldType.WATER, TEXTURE, LOCKED, DURABILITY, ACCEPT_PLAYER, ACCEPT_FIRE, SPEED);
    }
}
