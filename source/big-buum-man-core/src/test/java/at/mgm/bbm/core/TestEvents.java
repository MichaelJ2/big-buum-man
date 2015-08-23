package at.mgm.bbm.core;

import at.mgm.bbm.core.events.PlacePowerDown;
import at.mgm.bbm.core.events.PlacePowerUp;
import at.mgm.bbm.core.map.ObjectMap;
import at.mgm.bbm.core.objects.DisplayObject;
import at.mgm.bbm.core.objects.gameobjects.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestEvents {

    private static final int PLAYER_X = 1;
    private static final int PLAYER_Y = 1;

    private Player player = null;

    @Before
    public void init() {
        this.player = new Player(PLAYER_X, PLAYER_Y);
    }

    @Test
    public void testEvents() {
        System.out.println("");
        System.out.println("");
        System.out.println("# # #   T E S T I N G   E V E N T S   # # #");
        System.out.println("");
        System.out.println("");
        this.testPlaceBomb();
        this.testPowerUp();
        this.testPowerDown();
    }

    public void testPlaceBomb() {
        System.out.println("# # #   T E S T I N G   B O M B   # # #");
        player.placeBomb();
        player.placeBomb();
        player.placeBomb();
        player.placeBomb();

        Assert.assertEquals(1, ObjectMap.INSTANCE.getObjectCount());

        Assert.assertTrue(ObjectMap.INSTANCE.isTaken(PLAYER_X, PLAYER_Y));
        final DisplayObject object = ObjectMap.INSTANCE.getObject(PLAYER_X, PLAYER_Y);
        Assert.assertEquals(GameObjectType.BOMB, object.objectType);

        try {
            Thread.sleep(Bomb.TIMER_DEFAULT + 10);
            Assert.assertFalse(ObjectMap.INSTANCE.isTaken(PLAYER_X, PLAYER_Y));
        } catch (final InterruptedException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    public void testPowerUp() {
        System.out.println("# # #   T E S T I N G   P O W E R U P   # # #");
        Assert.assertEquals(Bomb.RANGE_DEFAULT, player.getBombRange());
        Assert.assertEquals(Bomb.TIMER_DEFAULT, player.getBombTimer());

        // place PowerUp
        new PlacePowerUp(new PowerUp(PLAYER_X, PLAYER_Y));
        new PlacePowerUp(new PowerUp(PLAYER_X, PLAYER_Y));
        new PlacePowerUp(new PowerUp(PLAYER_X, PLAYER_Y));

        Assert.assertEquals(1, ObjectMap.INSTANCE.getObjectCount());

        // check PowerUp boosts
        try {
            Thread.sleep(ObjectMap.UPDATE_INTERVAL * 2);
            Assert.assertFalse(ObjectMap.INSTANCE.isTaken(PLAYER_X, PLAYER_Y));
            Assert.assertEquals(Bomb.RANGE_MAX, player.getBombRange());
            Assert.assertEquals(Bomb.TIMER_MIN, player.getBombTimer());
        } catch (final InterruptedException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

        // check PowerUp duration and if player stats are reset to default after duration exceeded
        try {
            Thread.sleep(PowerUp.DEFAULT_DURATION + 10);
            Assert.assertEquals(Bomb.RANGE_DEFAULT, player.getBombRange());
            Assert.assertEquals(Bomb.TIMER_DEFAULT, player.getBombTimer());
        } catch (final InterruptedException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    public void testPowerDown() {
        System.out.println("# # #   T E S T I N G   P O W E R D O W N   # # #");
        Assert.assertEquals(Bomb.RANGE_DEFAULT, player.getBombRange());
        Assert.assertEquals(Bomb.TIMER_DEFAULT, player.getBombTimer());

        // place PowerDown
        new PlacePowerDown(new PowerDown(PLAYER_X, PLAYER_Y));
        new PlacePowerDown(new PowerDown(PLAYER_X, PLAYER_Y));
        new PlacePowerDown(new PowerDown(PLAYER_X, PLAYER_Y));

        Assert.assertEquals(1, ObjectMap.INSTANCE.getObjectCount());

        // check PowerDown nerfs
        try {
            Thread.sleep(ObjectMap.UPDATE_INTERVAL * 2);
            Assert.assertFalse(ObjectMap.INSTANCE.isTaken(PLAYER_X, PLAYER_Y));
            Assert.assertEquals(Bomb.RANGE_MIN, player.getBombRange());
            Assert.assertEquals(Bomb.TIMER_MAX, player.getBombTimer());
        } catch (final InterruptedException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

        // check PowerDown duration and if player stats are reset to default after duration exceeded
        try {
            Thread.sleep(PowerDown.DEFAULT_DURATION + 10);
            Assert.assertEquals(Bomb.RANGE_DEFAULT, player.getBombRange());
            Assert.assertEquals(Bomb.TIMER_DEFAULT, player.getBombTimer());
        } catch (final InterruptedException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}
