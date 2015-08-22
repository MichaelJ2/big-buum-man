package at.mgm.bbm.core.objects.fields;

public class Ice extends Field {

    public static final int ID = 6;
    public static final String TEXTURE = "textures/ice.png";
    public static final boolean LOCKED = false;
    public static final int DURABILITY = 1;
    public static final boolean ACCEPT_PLAYER = true;
    public static final boolean ACCEPT_FIRE = true;
    public static final double SPEED = 2.0;

    protected Ice() {
        super(FieldType.ICE, TEXTURE, LOCKED, DURABILITY, ACCEPT_PLAYER, ACCEPT_FIRE, SPEED);
    }
}
