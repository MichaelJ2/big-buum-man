package at.mnm.bbm.core.objects.bombs;

public class BombFactory {

    public static final synchronized Bomb getBomb(final BombType paramBombType) {
        Bomb bomb;

        switch (paramBombType) {
            case DEFAULT: bomb = new DefaultBomb();
                break;
            case NUKE: bomb = new Nuke();
                break;
            default: bomb = new DefaultBomb();
                break;
        }

        return bomb;
    }

    public static final synchronized Bomb getBomb(final BombType paramBombType, final int paramX, final int paramY) {
        Bomb bomb = getBomb(paramBombType);

        bomb.setX(paramX);
        bomb.setY(paramY);

        return bomb;
    }
}
