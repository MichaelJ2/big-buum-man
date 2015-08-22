package at.mgm.bbm.core;

import at.mgm.bbm.core.map.ObjectMap;
import at.mgm.bbm.core.objects.DisplayObject;
import at.mgm.bbm.core.objects.gameobjects.GameObjectType;
import at.mgm.bbm.core.objects.gameobjects.Player;
import org.junit.Assert;
import org.junit.Test;

public class TestEvents {

    private static final int PLAYER_X = 1;
    private static final int PLAYER_Y = 1;

    @Test
    public void testBombEvent() {
        Player player = new Player(PLAYER_X, PLAYER_Y);
        player.placeBomb();
        player.placeBomb();
        player.placeBomb();
        player.placeBomb();

        Assert.assertEquals(ObjectMap.INSTANCE.getObjectCount(), 1);

        Assert.assertTrue(ObjectMap.INSTANCE.isTaken(PLAYER_X, PLAYER_Y));
        final DisplayObject object = ObjectMap.INSTANCE.getObject(PLAYER_X, PLAYER_Y);
        Assert.assertEquals(object.objectType, GameObjectType.BOMB);

        try {
            Thread.sleep(2010);
            Assert.assertFalse(ObjectMap.INSTANCE.isTaken(PLAYER_X, PLAYER_Y));
        } catch (final InterruptedException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}
