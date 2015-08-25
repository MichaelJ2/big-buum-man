package core.map;

import at.mgm.bbm.core.map.VMap;
import at.mgm.bbm.core.objects.fields.Field;
import at.mgm.bbm.core.objects.fields.FieldFactory;
import at.mgm.bbm.core.objects.fields.FieldType;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class TestMap {

    private static final File MAP_001 = new File("src/test/resources/map-test-001.map.txt");
    private static final File MAP_002 = new File("src/test/resources/map-test-002.map.txt");
    private static final String MAP_003 = "map-test-003.map.txt";

    @Test
    public void testMap() {
        System.out.println("# # #   T E S T I N G   M A P   O B J E C T   # # #");

        printMap();

        VMap.INSTANCE.loadMap(5, 10);

        printMap();

        VMap.INSTANCE.saveMap(MAP_003);

        VMap.INSTANCE.loadMap(new File(MAP_003));

        printMap();
    }

    private void printMap() {
        final StringBuilder sb = new StringBuilder();
        final Field[][] map = VMap.INSTANCE.virtualMap;

        for (final Field[] y : map) {
            for (final Field x : y) {
                sb.append(x.ID + " ");
            }
            System.out.println("\t" + sb.toString());
            sb.setLength(0);
        }
        System.out.println();
    }
}
