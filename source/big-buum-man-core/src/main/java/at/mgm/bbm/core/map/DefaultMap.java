package at.mgm.bbm.core.map;

import at.mgm.bbm.core.Field;
import at.mgm.bbm.core.FieldType;
import at.mgm.bbm.core.fields.FieldFactory;

import java.util.ArrayList;
import java.util.List;

public enum DefaultMap {

    INSTANCE;

    private final int START_X = 672;
    private final int START_Y = 252;

    public final List<List<Field>> DEFAULT = new ArrayList<List<Field>>();

    {
        List<Field> row = new ArrayList<Field>();

        // 1st row
        for (int i = 0; i < 9; i++) {
            row.add(FieldFactory.getField(FieldType.BORDER, START_X + (64 * i), START_Y));
        }

        DEFAULT.add(row);
        row = new ArrayList<Field>();

        // next 7 rows
        for (int i = 0; i < 7; i++) {
            row.add(FieldFactory.getField(FieldType.BORDER, START_X, START_Y + (64 * (i + 1))));
            for (int j = 0; j < 7; j++) {
                row.add(FieldFactory.getField(FieldType.GROUND, START_X + (64 * (j + 1)), START_Y + (64 * (i + 1))));
            }
            row.add(FieldFactory.getField(FieldType.BORDER, START_X + (64 * 8), START_Y + (64 * (i + 1))));

            DEFAULT.add(row);
            row = new ArrayList<Field>();
        }

        // 9th row
        for (int i = 0; i < 9; i++) {
            row.add(FieldFactory.getField(FieldType.BORDER, START_X + (64 * i), START_Y + (64 * 8)));
        }

        DEFAULT.add(row);
    }
}
