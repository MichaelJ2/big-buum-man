package at.mgm.bbm.core;

import at.mgm.bbm.core.fields.FieldFactory;
import org.junit.Assert;
import org.junit.Test;

public class TestFields {

    @Test
    public void testField() {
        Assert.assertEquals(FieldFactory.getField(FieldType.GROUND).getFieldID(), 0);
        Assert.assertEquals(FieldFactory.getField(FieldType.WALL).getFieldID(), 1);
        Assert.assertEquals(FieldFactory.getField(FieldType.SPAWN).getFieldID(), 2);
    }
}
