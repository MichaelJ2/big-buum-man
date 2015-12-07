package at.bbm.core.map;

import at.bbm.core.utils.FileUtils;
import org.junit.Test;

import java.io.File;

public class TestMap {

    private static final String TEST_MAPS_DIR = "src/test/resources";

    @Test
    public void testMap() {
        final File[] maps = FileUtils.INSTANCE.getMaps(TEST_MAPS_DIR);
        for (final File map : maps) {
            System.out.println(map.getName());
        }
        MapObject mapObject = new MapObject();
        mapObject.loadDefaultMap();
        mapObject.printToCmd();
        if (maps.length == 0) {
            System.out.println(String.format("No map found in: %s", new File(TEST_MAPS_DIR).getAbsolutePath()));
        } else {
            mapObject.loadMap(maps[0]);
            mapObject.printToCmd();
        }
    }
}
