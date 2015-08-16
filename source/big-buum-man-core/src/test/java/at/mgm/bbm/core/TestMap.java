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

        System.out.println("Print 9x9 map:");
        printMap();

        System.out.println("Print 9x7 map:");
        Map.INSTANCE.loadMap(MAP_001, 0, 0);

        printMap();

        System.out.println("Print 7x7 map:");
        Map.INSTANCE.loadMap(MAP_002, 0, 0);

        printMap();

        System.out.println("Print 3x5 map:");
        Map.INSTANCE.createMap(3, 5, 0, 0);

        printMap();

        System.out.println("Print 11x9 map:");
        Map.INSTANCE.createMap(11, 9, 0, 0);

        printMap();
    }

    private void printMap() {

        StringBuilder sb = new StringBuilder();

        for (List<Field> row : Map.INSTANCE.getMap()) {
            for (Field field : row) {
                sb.append(field.getFieldType().ID + " ");
            }
            System.out.println("\t" + sb.toString());
            sb.setLength(0);
        }
        System.out.println();
    }
}
