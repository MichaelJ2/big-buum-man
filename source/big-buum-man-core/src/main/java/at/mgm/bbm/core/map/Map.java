package at.mgm.bbm.core.map;

import at.mgm.bbm.core.Field;
import at.mgm.bbm.core.fields.FieldFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
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

    public boolean saveMap() {
        try {
            File file = new File("map.txt");
            FileWriter fileWriter = new FileWriter(file);

            StringBuilder stringBuilder = new StringBuilder();

            for (List<Field> fields : map) {
                for (Field field : fields) {
                    stringBuilder.append(field.getFieldType().ID);
                    stringBuilder.append(",");
                }
                if (stringBuilder.length() > 0) {
                    stringBuilder.setLength(stringBuilder.length() - 1);
                }
                stringBuilder.append("\n");
                fileWriter.write(stringBuilder.toString());
                stringBuilder.setLength(0);
            }

            fileWriter.flush();
            fileWriter.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
