package at.mgm.bbm.core;

import at.mgm.bbm.core.utils.FileUtils;
import org.junit.Test;

import java.io.File;

public class TestFileUtils {

    private static final String MAPS_DIR = ".";

    @Test
    public void testFileUtils() {
        File[] results = FileUtils.INSTANCE.getMaps(MAPS_DIR);
        System.out.println(String.format("Found %d maps:", results.length));
        for (File result : results) {
            System.out.println(result.getName());
        }
    }
}
