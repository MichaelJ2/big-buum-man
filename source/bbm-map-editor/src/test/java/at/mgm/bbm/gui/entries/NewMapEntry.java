package at.mgm.bbm.gui.entries;

import at.mgm.bbm.core.States;

public class NewMapEntry extends Entry {

    public NewMapEntry(final int paramX, final int paramY, final int paramHeight, final int paramWidth) {
        super(paramX, paramY, paramHeight, paramWidth, States.SCREEN_NEW_MAP, null);
        System.out.println(String.format("%d x %x - %d * %d", paramX, paramY, paramHeight, paramWidth));
    }

}
