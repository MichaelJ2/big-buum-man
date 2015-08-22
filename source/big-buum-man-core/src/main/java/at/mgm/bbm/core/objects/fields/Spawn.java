package at.mgm.bbm.core.objects.fields;

public class Spawn extends Field {

    public static final int ID = 2;
    public static final String TEXTURE = "textures/spawn.png";
    public static final boolean LOCKED = false;
    public static final int DURABILITY = -1;
    public static final boolean ACCEPT_PLAYER = true;
    public static final boolean ACCEPT_FIRE = true;
    public static final double SPEED = 1.0;

    protected Spawn() {
        super(FieldType.SPAWN, TEXTURE, LOCKED, DURABILITY, ACCEPT_PLAYER, ACCEPT_FIRE, SPEED);
    }
}