package at.mnm.bbm.core.objects.extras;

import at.mnm.bbm.core.objects.DisplayObject;
import at.mnm.bbm.core.objects.GameObjectType;
import at.mnm.bbm.core.objects.characters.Player;

public abstract class Extra extends DisplayObject {

    public static final String TEXTURE = "textures/extra.png";
    public static final int DEFAULT_DURATION = 10000;

    public final ExtraType extraType;
    protected long duration = DEFAULT_DURATION;

    // TODO: store all extra stats like walk speed, reload time, effect type (stun, slow), etc.

    /**
     * Protected constructor only accessible for ExtraFactory.
     *
     * @param paramExtraType The type of extra.
     */
    protected Extra(final ExtraType paramExtraType) {
        super(GameObjectType.EXTRA, 0, 0);
        this.extraType = paramExtraType;
        this.initEvent();
    }

    protected abstract void initEvent();

    /**
     * Override to handle type specific boosts or nerfs on the player.
     *
     * @param paramPlayer The player to boost or nerf.
     */
    public abstract void updatePlayerStats(final Player paramPlayer);
}
