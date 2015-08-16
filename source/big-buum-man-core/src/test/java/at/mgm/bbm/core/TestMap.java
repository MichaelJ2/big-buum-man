package at.mgm.bbm.core;

import at.mgm.bbm.core.map.Map;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class TestMap {

    private static final File MAP_001 = new File("src/test/resources/map-test-001.map.txt");
    private static final File MAP_002 = new File("src/test/resources/map-test-002.map.txt");

    @Test
    public void testMap() {

        System.out.println("# # #   T E S T I N G   M A P   O B J E C T   # # #");

        Map.INSTANCE.loadMap(MAP_001, 0, 0);

        printMap();

        Map.INSTANCE.loadMap(MAP_002, 0, 0);

        printMap();
    }

    private void printMap() {

        List<List<Field>> result = Map.INSTANCE.getMap();

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
}
