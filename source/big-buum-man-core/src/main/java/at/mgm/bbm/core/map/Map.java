package at.mgm.bbm.core.map;

import at.mgm.bbm.core.Field;
import at.mgm.bbm.core.FieldType;
import at.mgm.bbm.core.fields.FieldFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public enum Map {

    INSTANCE;

    private final int DEFAULT_START_X = 960 - (9 * FIELD_SIZE / 2);
    private final int DEFAULT_START_Y = 540 - (9 * FIELD_SIZE / 2);

    public static final int FIELD_SIZE = 32;
    private String mapName = "DEFAULT";

    private final List<List<Field>> MAP = new ArrayList<List<Field>>();

    public List<List<Field>> getMap() {
        if (MAP.isEmpty()) {
            initDefaultMap();
        }
        return MAP;
    }

    public String getMapName() {
        return mapName;
    }

    public boolean loadMap(final File paramFile, final int xOffset, final int yOffset) {

        boolean result = false;

        int rows = 0;
        int columns;

        if (null == paramFile) {
            return result;
        }

        List<String> lines = new ArrayList<String>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(paramFile));
            String line;

            while ((line = br.readLine()) != null) {
                lines.add(line);
                rows++;
            }

            columns = lines.get(0).split(",").length;

            MAP.clear();

            int xStartPoint = 960 - xOffset - (rows * FIELD_SIZE / 2);
            int yStartPoint = 540 - yOffset - (columns * FIELD_SIZE / 2);

            for (int i = 0; i < lines.size(); i++) {
                List<Field> fields = new ArrayList<Field>();
                String[] field = lines.get(i).split(",");

                for (int j = 0; j < field.length; j++) {
                    fields.add(FieldFactory.getField(Integer.valueOf(field[j]), xStartPoint + (j * FIELD_SIZE), yStartPoint + (i * FIELD_SIZE)));
                }

                MAP.add(fields);
            }
            mapName = paramFile.getName();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean saveMap(String paramName) {

        boolean result = false;

        if (!paramName.endsWith(".map.txt")) {
            paramName += ".map.txt";
        }

        mapName = paramName;

        System.out.println(String.format("Saving %s ...", paramName));

        try {
            File file = new File(paramName);
            FileWriter fileWriter = new FileWriter(file);

            StringBuilder stringBuilder = new StringBuilder();

            for (List<Field> fields : MAP) {
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

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private void initDefaultMap() {

        createMap(9, 9, 0, 0);

        if (true) {
            return;
        }

        MAP.clear();

        List<Field> row = new ArrayList<Field>();

        // 1st row
        for (int i = 0; i < 9; i++) {
            row.add(FieldFactory.getField(FieldType.BORDER, DEFAULT_START_X + (FIELD_SIZE * i), DEFAULT_START_Y));
        }

        MAP.add(row);
        row = new ArrayList<Field>();

        // next 7 rows
        for (int i = 0; i < 7; i++) {
            row.add(FieldFactory.getField(FieldType.BORDER, DEFAULT_START_X, DEFAULT_START_Y + (FIELD_SIZE * (i + 1))));
            for (int j = 0; j < 7; j++) {
                row.add(FieldFactory.getField(FieldType.GROUND, DEFAULT_START_X + (FIELD_SIZE * (j + 1)), DEFAULT_START_Y + (FIELD_SIZE * (i + 1))));
            }
            row.add(FieldFactory.getField(FieldType.BORDER, DEFAULT_START_X + (FIELD_SIZE * 8), DEFAULT_START_Y + (FIELD_SIZE * (i + 1))));

            MAP.add(row);
            row = new ArrayList<Field>();
        }

        // 9th row
        for (int i = 0; i < 9; i++) {
            row.add(FieldFactory.getField(FieldType.BORDER, DEFAULT_START_X + (FIELD_SIZE * i), DEFAULT_START_Y + (FIELD_SIZE * 8)));
        }

        MAP.add(row);
    }

    public void createMap(final int paramXFieldCount, final int paramYFieldCount, final int paramXOffset, final int paramYOffset) {

        MAP.clear();

        int xStartPoint = 960 - paramXOffset - ((paramXFieldCount + 2) * FIELD_SIZE / 2);
        int yStartPoint = 540 - paramYOffset - ((paramYFieldCount + 2) * FIELD_SIZE / 2);

        List<Field> fields = new ArrayList<Field>();

        // first row contains only borders
        for (int i = 0; i < paramXFieldCount + 2; i++) {
            fields.add(FieldFactory.getField(FieldType.BORDER, xStartPoint + (i * FIELD_SIZE), yStartPoint));
        }
        MAP.add(fields);

        // all other rows start with a border field then are filled with ground fields and end with a border field
        for (int i = 0; i < paramYFieldCount; i++) {
            fields = new ArrayList<Field>();
            fields.add(FieldFactory.getField(FieldType.BORDER, xStartPoint, yStartPoint + ((i + 1) * FIELD_SIZE)));
            for (int j = 0; j < paramXFieldCount; j++) {
                fields.add(FieldFactory.getField(FieldType.GROUND, xStartPoint + ((j + 1) * FIELD_SIZE), yStartPoint + ((i + 1) * FIELD_SIZE)));
            }
            fields.add(FieldFactory.getField(FieldType.BORDER, xStartPoint + ((paramXFieldCount + 1) * FIELD_SIZE), yStartPoint + ((i + 1) * FIELD_SIZE)));
            MAP.add(fields);
        }

        // last row contains only borders
        fields = new ArrayList<Field>();
        for (int i = 0; i < paramXFieldCount + 2; i++) {
            fields.add(FieldFactory.getField(FieldType.BORDER, xStartPoint + (i * FIELD_SIZE), yStartPoint + ((paramYFieldCount + 1) * FIELD_SIZE)));
        }
        MAP.add(fields);
    }

}
