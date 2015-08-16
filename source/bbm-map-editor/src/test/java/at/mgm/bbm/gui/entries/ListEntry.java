package at.mgm.bbm.gui.entries;

import java.io.File;

/**
 * Created by admin on 16.08.2015.
 */
public class ListEntry extends Entry {

    public int x;
    public int y;
    public String text;

    public ListEntry(int paramX, int paramY, int paramWidth, int paramHeight, int paramID, final Object paramObject) {
        super(paramX, paramY, paramWidth, paramHeight, paramID, paramObject);
        x = paramX;
        y = paramY;

        if (paramObject instanceof File) {
            text = ((File) paramObject).getName();
        } else {
            text = "null";
        }
    }
}
