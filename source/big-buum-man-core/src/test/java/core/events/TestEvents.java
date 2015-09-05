package core.events;

import at.mnm.bbm.core.map.ObjectMap;
import at.mnm.bbm.core.objects.DisplayObject;
import at.mnm.bbm.core.objects.bombs.Bomb;
import at.mnm.bbm.core.objects.extras.Extra;
import at.mnm.bbm.core.objects.GameObjectType;
import at.mnm.bbm.core.objects.characters.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestEvents {

    private static final int PLAYER_X = 5;
    private static final int PLAYER_Y = 5;

    private Player player = null;

    @Before
    public void init() {
        player = new Player(PLAYER_X, PLAYER_Y);
    }

    @Test
    public void testEvents() {
        System.out.println();
        this.testPlayerMovement();
        this.testBomb();
        this.testPlaceMultipleBombs();
        this.testPowerUp();
        this.testPowerDown();
    }

    public void testPlayerMovement() {
        System.out.println("# # #   T E S T I N G   P L A Y E R   M O V E M E N T   # # #");

        // check player's current location
        Assert.assertEquals(PLAYER_X, player.getX());
        Assert.assertEquals(PLAYER_Y, player.getY());

        System.out.println(String.format("Player's old location was %d x %x", player.getX(), player.getY()));

        // move player
        player.move(1, 0, true);

        // check if player was moved successfully
        Assert.assertEquals(PLAYER_X + 1, player.getX());
        Assert.assertEquals(PLAYER_Y, player.getY());

        System.out.println(String.format("Player's new location is %d x %x", player.getX(), player.getY()));

        // reset player's location
        player.setX(PLAYER_X);
        player.setY(PLAYER_Y);

        System.out.println();
    }

    public void testBomb() {
        System.out.println("# # #   T E S T I N G   B O M B   # # #");

        // place multiple bombs
        player.placeBomb();
        player.placeBomb();
        player.placeBomb();
        player.placeBomb();

        // check if only on bomb is placed at the field
        Assert.assertEquals(1, ObjectMap.INSTANCE.getObjectCount());
        Assert.assertTrue(ObjectMap.INSTANCE.isTaken(PLAYER_X, PLAYER_Y));
        final DisplayObject object = ObjectMap.INSTANCE.getObject(PLAYER_X, PLAYER_Y);
        Assert.assertEquals(GameObjectType.BOMB, object.objectType);

        // wait until bomb explodes
        this.waitTime(Bomb.DEFAULT_TIMER + 10);

        // check if bomb was removed after detonation
        Assert.assertEquals(0, ObjectMap.INSTANCE.getObjectCount());

        // check if the bomb was removed after detonation
        Assert.assertFalse(ObjectMap.INSTANCE.isTaken(PLAYER_X, PLAYER_Y));

        // wait until the player's inventory is full again
        this.waitTime(Player.DEFAULT_BOMB_REFILL_COOLDOWN);

        System.out.println();
    }

    public void testPlaceMultipleBombs() {
        System.out.println("# # #   T E S T I N G   P L A Y E R   M O V E M E N T   A N D   B O M B S   # # #");

        // place first bomb
        player.placeBomb();

        // check if first bomb is placed
        Assert.assertEquals(1, ObjectMap.INSTANCE.getObjectCount());

        // move player
        player.move(1, 0, true);

        // place second bomb at new location
        player.placeBomb();

        // check if second bomb is placed
        Assert.assertEquals(2, ObjectMap.INSTANCE.getObjectCount());

        // move player
        player.move(1, 0, true);

        // place third bomb at new location
        player.placeBomb();

        // check if third bomb is placed
        Assert.assertEquals(3, ObjectMap.INSTANCE.getObjectCount());

        // move player
        player.move(1, 0, true);

        // place fourth bomb at new location
        player.placeBomb();

        // check if fourth bomb is is NOT placed
        Assert.assertEquals(3, ObjectMap.INSTANCE.getObjectCount());

        // wait until the bombs detonated
        this.waitTime(Bomb.DEFAULT_TIMER + 10);

        // check if all bombs were removed after their detonation
        Assert.assertEquals(0, ObjectMap.INSTANCE.getObjectCount());

        // wait until the player's inventory is full again
        this.waitTime(Player.DEFAULT_BOMB_REFILL_COOLDOWN * 3);

        // check if all bombs were removed after their detonation
        Assert.assertEquals(3, player.bombs);

        // reset player's location
        player.setX(PLAYER_X);
        player.setY(PLAYER_Y);

        System.out.println();
    }

    public void testPowerUp() {
        System.out.println("# # #   T E S T I N G   P O W E R U P   # # #");

        // check if player's stats are default
        Assert.assertEquals(Bomb.DEFAULT_RANGE, player.getBombRange());
        Assert.assertEquals(Bomb.DEFAULT_TIMER, player.getBombTimer());

        // place multiple PowerUps
        // TODO

        // check if only one PowerUp is placed at the field
        Assert.assertEquals(1, ObjectMap.INSTANCE.getObjectCount());

        // player has to move to get the PowerUp
        player.move(0, 0, true);

        this.waitTime(ObjectMap.UPDATE_INTERVAL * 2);

        // check if PowerUp was removed from the map
        Assert.assertFalse(ObjectMap.INSTANCE.isTaken(PLAYER_X, PLAYER_Y));

        // check PowerUp boosts
        Assert.assertEquals(Bomb.RANGE_MAX, player.getBombRange());
        Assert.assertEquals(Bomb.TIMER_MIN, player.getBombTimer());

        // wait until the boost has expired
        this.waitTime(Extra.DEFAULT_DURATION + 10);

        // check if player stats are reset to default after the duration exceeded
        Assert.assertEquals(Bomb.DEFAULT_RANGE, player.getBombRange());
        Assert.assertEquals(Bomb.DEFAULT_TIMER, player.getBombTimer());

        System.out.println();
    }

    public void testPowerDown() {
        System.out.println("# # #   T E S T I N G   P O W E R D O W N   # # #");

        // check if player's stats are default
        Assert.assertEquals(Bomb.DEFAULT_RANGE, player.getBombRange());
        Assert.assertEquals(Bomb.DEFAULT_TIMER, player.getBombTimer());

        // place multiple PowerUps
        // TODO

        // check if only PowerUp is placed at the field
        Assert.assertEquals(1, ObjectMap.INSTANCE.getObjectCount());

        // player has to move to get the PowerUp
        player.move(0, 0, true);

        this.waitTime(ObjectMap.UPDATE_INTERVAL * 2);

        // check if PowerDown was removed from the map
        Assert.assertFalse(ObjectMap.INSTANCE.isTaken(PLAYER_X, PLAYER_Y));

        // check PowerDown nerfs
        Assert.assertEquals(Bomb.RANGE_MIN, player.getBombRange());
        Assert.assertEquals(Bomb.TIMER_MAX, player.getBombTimer());

        // wait until the nerf has expired
        this.waitTime(Extra.DEFAULT_DURATION + 10);

        // check if player stats are reset to default after the duration exceeded
        Assert.assertEquals(Bomb.DEFAULT_RANGE, player.getBombRange());
        Assert.assertEquals(Bomb.DEFAULT_TIMER, player.getBombTimer());

        System.out.println();
    }

    private void waitTime(final long paramMillis) {
        try {
            Thread.sleep(paramMillis);
        } catch (final InterruptedException e) {
            Assert.fail(String.format("Failed to wait for next Assertion!\n%s", e.getMessage()));
        }
    }
}
