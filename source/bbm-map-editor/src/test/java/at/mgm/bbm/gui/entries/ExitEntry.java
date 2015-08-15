package at.mgm.bbm.gui.entries;

import at.mgm.bbm.core.States;

public class ExitEntry extends Entry {

    public ExitEntry(final int paramX, final int paramY, final int paramHeight, final int paramWidth) {
        super(paramX, paramY, paramHeight, paramWidth, States.ACTION_EXIT);
        System.out.println(String.format("%d x %x - %d * %d", paramX, paramY, paramHeight, paramWidth));
    }

}
