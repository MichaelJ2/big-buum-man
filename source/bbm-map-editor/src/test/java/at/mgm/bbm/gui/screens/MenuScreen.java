package at.mgm.bbm.gui.screens;

import at.mgm.bbm.core.States;
import at.mgm.bbm.gui.Resources;
import at.mgm.bbm.gui.entries.Entry;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

public class MenuScreen extends BasicGameState {

    private StateBasedGame game;
    private Font font;

    private final String TITLE = "Big Buum Man - Map Editor";
    private final String NEW_MAP = "New Map";
    private final String LOAD_MAP = "Load Map";
    private final String CONTINUE = "Continue";
    private final String EXIT = "Exit";

    private int TITLE_X = 0;

    private int NEW_MAP_X = 0;
    private int NEW_MAP_Y = 0;

    private int LOAD_MAP_X = 0;
    private int LOAD_MAP_Y = 0;

    private int CONTINUE_X = 0;
    private int CONTINUE_Y = 0;

    private int EXIT_X = 0;
    private int EXIT_Y = 0;

    private final List<Entry> menu_entries = new ArrayList<Entry>();

    @Override
    public int getID() {
        return States.SCREEN_MENU;
    }

    @Override
    public void init(final GameContainer paramGameContainer, final StateBasedGame paramStateBasedGame) throws SlickException {
        game = paramStateBasedGame;

        font = Resources.INSTANCE.font;

        TITLE_X = font.getWidth(TITLE) / 2;

        NEW_MAP_X = font.getWidth(NEW_MAP);
        NEW_MAP_Y = font.getHeight(NEW_MAP);

        LOAD_MAP_X = font.getWidth(LOAD_MAP);
        LOAD_MAP_Y = font.getHeight(LOAD_MAP);

        CONTINUE_X = font.getWidth(CONTINUE);
        CONTINUE_Y = font.getHeight(CONTINUE);

        EXIT_X = font.getWidth(EXIT);
        EXIT_Y = font.getHeight(EXIT);

        menu_entries.add(new Entry(States.SCREEN_NEW_MAP, 960 - (NEW_MAP_X / 2), 500, NEW_MAP_X, NEW_MAP_Y, null));
        menu_entries.add(new Entry(States.SCREEN_LOAD_MAP, 960 - (LOAD_MAP_X / 2), 650, LOAD_MAP_X, LOAD_MAP_Y, null));
        menu_entries.add(new Entry(States.SCREEN_EDITOR, 960 - (CONTINUE_X / 2), 800, CONTINUE_X, CONTINUE_Y, null));
        menu_entries.add(new Entry(States.ACTION_EXIT, 960 - (EXIT_X / 2), 950, EXIT_X, EXIT_Y, null));


    }

    @Override
    public void render(final GameContainer paramGameContainer, final StateBasedGame paramStateBasedGame, final Graphics paramGraphics) throws SlickException {
        font.drawString(960 - TITLE_X, 100, TITLE);
        font.drawString(960 - (NEW_MAP_X / 2), 500, NEW_MAP);
        font.drawString(960 - (LOAD_MAP_X / 2), 650, LOAD_MAP);
        font.drawString(960 - (CONTINUE_X / 2), 800, CONTINUE);
        font.drawString(960 - (EXIT_X / 2), 950, EXIT);
    }

    @Override
    public void update(final GameContainer paramGameContainer, final StateBasedGame paramStateBasedGame, final int delta) throws SlickException {
        Input input = paramGameContainer.getInput();

        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            for (Entry entry : menu_entries) {
                if (entry.checkCollision(mouseX, mouseY)) {
                    switch (entry.ID) {
                        case States.ACTION_EXIT: paramGameContainer.exit();
                            break;
                        default: game.enterState(entry.ID);
                            break;
                    }
                }
            }
        } else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            paramGameContainer.exit();
        }
    }
}
