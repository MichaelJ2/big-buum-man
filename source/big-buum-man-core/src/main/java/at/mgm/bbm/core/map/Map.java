package at.mgm.bbm.core.map;

import at.mgm.bbm.core.Field;
import at.mgm.bbm.core.fields.FieldFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 14.08.2015.
 */
public class Map {

    private List<List<Field>> map = new ArrayList<List<Field>>();

    private Map() {
        //
    }

    public Map(final File paramFile) {
        readMap(paramFile);
    }

    public List<List<Field>> getMap() {
        return map;
    }

    private void readMap(final File paramFile) {

        if (null == paramFile) {
            map = DefaultMap.INSTANCE.DEFAULT;
            return;
        }

        List<String> lines = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(paramFile));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            for (int i = 0; i < lines.size(); i++) {
                List<Field> fields = new ArrayList<Field>();
                String[] columns = lines.get(i).split(",");
                for (int j = 0; j < columns.length; j++) {
                    fields.add(FieldFactory.getField(Integer.valueOf(columns[j]), i * j * 64, i * j * 64));
                }
                map.add(fields);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
