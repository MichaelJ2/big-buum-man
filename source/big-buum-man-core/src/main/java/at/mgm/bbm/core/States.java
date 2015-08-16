package at.mgm.bbm.core;

public class States {

    private States() {
        // defeat instantiation
    }

    // GAME SCREEN STATES
    public static final int SCREEN_MENU = 0;
    public static final int SCREEN_EDITOR = 1;
    public static final int SCREEN_SAVE = 2;
    public static final int SCREEN_LOAD_MAP = 3;
    public static final int SCREEN_NEW_MAP = 4;

    // APPLICATION STATES
    public static final int ACTION_EXIT = -1;

}
