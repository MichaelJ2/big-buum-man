package at.mgm.bbm.core.objects.gameobjects;

public enum GameObjectType {

    PLAYER(Player.TEXTURE),
    BOMB(Bomb.TEXTURE),
    POWERUP(PowerUp.TEXTURE),
    POWERDOWN(PowerDown.TEXTURE);

    public final String TEXTURE;

    GameObjectType(final String paramTexture) {
        this.TEXTURE = paramTexture;
    }
}
