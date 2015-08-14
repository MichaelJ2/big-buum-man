package at.mgm.bbm.core.map;

import at.mgm.bbm.core.Field;
import at.mgm.bbm.core.FieldType;
import at.mgm.bbm.core.fields.FieldFactory;

import java.util.ArrayList;
import java.util.List;

public enum DefaultMap {

    INSTANCE;

    public final List<List<Field>> DEFAULT = new ArrayList<List<Field>>();

    {
        List<Field> row = new ArrayList<Field>();

        // 1st row
        for (int i = 0; i < 9; i++) {
            row.add(FieldFactory.getField(FieldType.BORDER, 100 + (64 * i), 100));
        }

        DEFAULT.add(row);
        row = new ArrayList<Field>();

        // next 7 rows
        for (int i = 0; i < 7; i++) {
            row.add(FieldFactory.getField(FieldType.BORDER, 100, 164 + (64 * i)));
            for (int j = 0; j < 7; j++) {
                row.add(FieldFactory.getField(FieldType.GROUND, 164 + (64 * j), 164 + (64 * i)));
            }
            row.add(FieldFactory.getField(FieldType.BORDER, 100 + (64 * 8), 164 + (64 * i)));

            DEFAULT.add(row);
            row = new ArrayList<Field>();
        }

        // 9th row
        for (int i = 0; i < 9; i++) {
            row.add(FieldFactory.getField(FieldType.BORDER, 100 + (64 * i), 100 + (64 * 8)));
        }

        DEFAULT.add(row);
    }
}
