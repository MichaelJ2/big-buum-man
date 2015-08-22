package at.mgm.bbm.core.objects.fields;

public class Ground extends Field {

    public static final int ID = 1;
    public static final String TEXTURE = "textures/ground.png";
    public static final boolean LOCKED = false;
    public static final int DURABILITY = -1;
    public static final boolean ACCEPT_PLAYER = true;
    public static final boolean ACCEPT_FIRE = true;
    public static final double SPEED = 1.0;

    protected Ground() {
        super(FieldType.GROUND, TEXTURE, LOCKED, DURABILITY, ACCEPT_PLAYER, ACCEPT_FIRE, SPEED);
    }
}
