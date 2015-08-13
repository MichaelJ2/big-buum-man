package at.mgm.bbm.core;

import at.mgm.bbm.core.fields.FieldFactory;
import org.junit.Assert;
import org.junit.Test;

public class TestFields {

    @Test
    public void testField() {

        // GROUND
        Field ground = FieldFactory.getField(FieldType.GROUND);
        Assert.assertEquals(ground.getFieldID(), 0);
        Assert.assertEquals(ground.getFieldType(), FieldType.GROUND);

        // WALL
        Field wall = FieldFactory.getField(FieldType.WALL);
        Assert.assertEquals(wall.getFieldID(), 1);
        Assert.assertEquals(wall.getFieldType(), FieldType.WALL);

        // SPAWN
        Field spawn = FieldFactory.getField(FieldType.SPAWN);
        Assert.assertEquals(spawn.getFieldID(), 2);
        Assert.assertEquals(spawn.getFieldType(), FieldType.SPAWN);
    }
}
