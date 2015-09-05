package core.fields;

import at.mnm.bbm.core.objects.fields.*;
import org.junit.Assert;
import org.junit.Test;

public class TestFields {

    @Test
    public void testField() {

        // GROUND
        Field border = FieldFactory.getField(FieldType.BORDER);
        Assert.assertEquals(border.ID, Border.ID);
        Assert.assertEquals(border.fieldType, FieldType.BORDER);

        // GROUND
        Field ground = FieldFactory.getField(FieldType.GROUND);
        Assert.assertEquals(ground.ID, Ground.ID);
        Assert.assertEquals(ground.fieldType, FieldType.GROUND);

        // WALL
        Field wall = FieldFactory.getField(FieldType.WALL);
        Assert.assertEquals(wall.ID, Wall.ID);
        Assert.assertEquals(wall.fieldType, FieldType.WALL);

        // SPAWN
        Field spawn = FieldFactory.getField(FieldType.SPAWN);
        Assert.assertEquals(spawn.ID, Spawn.ID);
        Assert.assertEquals(spawn.fieldType, FieldType.SPAWN);
    }
}
