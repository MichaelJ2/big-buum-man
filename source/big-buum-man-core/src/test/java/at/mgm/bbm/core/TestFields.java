package at.mgm.bbm.core;

import at.mgm.bbm.core.fields.*;
import org.junit.Assert;
import org.junit.Test;

public class TestFields {

    @Test
    public void testField() {

        // GROUND
        Field border = FieldFactory.getField(FieldType.BORDER, 0, 0);
        Assert.assertEquals(border.ID, Border.ID);
        Assert.assertEquals(border.fieldType, FieldType.BORDER);

        // GROUND
        Field ground = FieldFactory.getField(FieldType.GROUND, 0, 0);
        Assert.assertEquals(ground.ID, Ground.ID);
        Assert.assertEquals(ground.fieldType, FieldType.GROUND);

        // WALL
        Field wall = FieldFactory.getField(FieldType.WALL, 0, 0);
        Assert.assertEquals(wall.ID, Wall.ID);
        Assert.assertEquals(wall.fieldType, FieldType.WALL);

        // SPAWN
        Field spawn = FieldFactory.getField(FieldType.SPAWN, 0, 0);
        Assert.assertEquals(spawn.ID, Spawn.ID);
        Assert.assertEquals(spawn.fieldType, FieldType.SPAWN);
    }
}
