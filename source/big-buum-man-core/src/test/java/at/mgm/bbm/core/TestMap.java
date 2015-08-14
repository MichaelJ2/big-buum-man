package at.mgm.bbm.core;

import at.mgm.bbm.core.fields.FieldFactory;
import at.mgm.bbm.core.map.Map;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestMap {

    private static final File MAP_001 = new File("src/test/resources/map-test-001.txt");

    @Test
    public void testMap() {

        System.out.println("# # #   T E S T I N G   M A P   O B J E C T   # # #");

        Map map = new Map(MAP_001);
        List<List<Field>> result = map.getMap();

        StringBuilder sb = new StringBuilder();

        System.out.println();
        for (List<Field> row : result) {
            for (Field field : row) {
                sb.append(field.getFieldType().ID + " ");
            }
            System.out.println("\t" + sb.toString());
            sb.setLength(0);
        }
        System.out.println();
    }

    @Test
    public void testMapUpdate() {

        System.out.println("# # #   T E S T I N G   M A P   U P D A T E   F U N C T I O N A L I T Y   # # #");

        Map map = new Map(null);

        List<List<Integer>> hashCodes = new ArrayList<List<Integer>>();

        for (List<Field> fields : map.getMap()) {
            List<Integer> hashCodeList = new ArrayList();
            for (Field f : fields) {
                hashCodeList.add(f.hashCode());
            }
            hashCodes.add(hashCodeList);
        }

        int a = map.getMap().get(0).get(0).hashCode();

        map.getMap().get(0).set(0, FieldFactory.getField(FieldType.SPAWN, 0, 0));

        int b = map.getMap().get(0).get(0).hashCode();

        if (a == b) {
            Assert.fail();
        }
    }
}
