package at.mnm.bbm.core.objects.fields;

public class Barrier extends Field {

    public static final int ID = 7;
    public static final String TEXTURE = "textures/barrier.png";
    public static final boolean LOCKED = false;
    public static final int DURABILITY = 1;
    public static final boolean ACCEPT_PLAYER = true;
    public static final boolean ACCEPT_FIRE = true;
    public static final double SPEED = 2.0;

    protected Barrier() {
        super(FieldType.BARRIER, TEXTURE, LOCKED, DURABILITY, ACCEPT_PLAYER, ACCEPT_FIRE, SPEED);
    }
}
