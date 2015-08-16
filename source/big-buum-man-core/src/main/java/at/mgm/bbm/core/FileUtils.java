package at.mgm.bbm.core;

import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.FileFilter;

public enum FileUtils {

    INSTANCE;

    public File[] getMaps(final String paramDir) {

        File dir = new File(paramDir);
        FileFilter fileFilter = new WildcardFileFilter("*.map.txt");
        File[] files = dir.listFiles(fileFilter);

        return files;
    }

}
