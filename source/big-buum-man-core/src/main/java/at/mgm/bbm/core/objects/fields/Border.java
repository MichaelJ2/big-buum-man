package at.mgm.bbm.core.objects.fields;

public class Border extends Field {

    public static final int ID = 0;
    public static final String TEXTURE = "textures/border.png";
    public static final boolean LOCKED = true;
    public static final int DURABILITY = -1;
    public static final boolean ACCEPT_PLAYER = false;
    public static final boolean ACCEPT_FIRE = false;
    public static final double SPEED = 0.0;

    protected Border() {
        super(FieldType.BORDER, TEXTURE, LOCKED, DURABILITY, ACCEPT_PLAYER, ACCEPT_FIRE, SPEED);
    }
}
