package at.bbm.core.objects.bombs;

import at.bbm.core.objects.bombs.bomb.DefaultBomb;
import at.bbm.core.objects.bombs.bomb.Nuke;

public class BombFactory {

    public static final synchronized Bomb getBomb(final BombType paramBombType) {
        Bomb bomb = null;

        /* TODO:
        switch (paramBombType) {
            case DEFAULT: bomb = new DefaultBomb();
                break;
            case NUKE: bomb = new Nuke();
                break;
            default: bomb = new DefaultBomb();
                break;
        } */

        return bomb;
    }
}
