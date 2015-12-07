package at.bbm.core.utils;

import at.bbm.core.objects.fields.Field;
import at.bbm.core.objects.fields.FieldFactory;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public enum FileUtils {

    INSTANCE;

    public File[] getMaps(final String paramDir) {

        File dir = new File(paramDir);
        FileFilter fileFilter = new WildcardFileFilter("*.map.txt");
        File[] files = dir.listFiles(fileFilter);

        return files;
    }

    public Field[][] loadMap(final File paramFile) {

        Field[][] result = null;

        int rows;
        int columns;

        if (null == paramFile) {
            return result;
        }

        try {
            final BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(paramFile), "UTF-8"));

            final List<String> lines = new ArrayList<String>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

            rows = lines.size();
            columns = lines.get(0).split(",").length;

            result = new Field[rows][columns];

            for (int y = 0; y < lines.size(); y++) {
                final String[] fields = lines.get(y).split(",");
                for (int x = 0; x < fields.length; x++) {
                    try {
                        final Integer field = Integer.valueOf(fields[x]);
                        result[y][x] = FieldFactory.getField(field);
                    } catch (final NumberFormatException e) {
                        e.printStackTrace();
                        result = null;
                        return result;
                    }
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public boolean saveMap(final Field[][] paramMap) {

        boolean result = false;

        return result;
    }

}
