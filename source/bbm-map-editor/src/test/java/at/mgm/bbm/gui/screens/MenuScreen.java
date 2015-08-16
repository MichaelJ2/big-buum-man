package at.mgm.bbm.gui.screens;

import at.mgm.bbm.core.States;
import at.mgm.bbm.gui.entries.Entry;
import at.mgm.bbm.gui.entries.ExitEntry;
import at.mgm.bbm.gui.entries.LoadMapEntry;
import at.mgm.bbm.gui.entries.NewMapEntry;
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
    private final String EXIT = "Exit";

    private final List<Entry> menu_entries = new ArrayList<Entry>();

    @Override
    public int getID() {
        return States.SCREEN_MENU;
    }

    @Override
    public void init(final GameContainer paramGameContainer, final StateBasedGame paramStateBasedGame) throws SlickException {
        System.out.println("MenuScreen#init()");
        game = paramStateBasedGame;
        font = new AngelCodeFont("fonts/font.fnt", new Image("fonts/font.png"));
        menu_entries.add(new NewMapEntry((1920 / 2) - (font.getWidth(NEW_MAP) / 2), 400, font.getWidth(NEW_MAP), font.getHeight(NEW_MAP)));
        menu_entries.add(new LoadMapEntry((1920 / 2) - (font.getWidth(LOAD_MAP) / 2), 550, font.getWidth(LOAD_MAP), font.getHeight(LOAD_MAP)));
        menu_entries.add(new ExitEntry((1920 / 2) - (font.getWidth(EXIT) / 2), 700, font.getWidth(EXIT), font.getHeight(EXIT)));
    }

    @Override
    public void render(final GameContainer paramGameContainer, final StateBasedGame paramStateBasedGame, final Graphics paramGraphics) throws SlickException {
        font.drawString((1920 / 2) - (font.getWidth(TITLE) / 2), 100, TITLE);
        font.drawString((1920 / 2) - (font.getWidth(NEW_MAP) / 2), 400, NEW_MAP);
        font.drawString((1920 / 2) - (font.getWidth(LOAD_MAP) / 2), 550, LOAD_MAP);
        font.drawString((1920 / 2) - (font.getWidth(EXIT) / 2), 700, EXIT);
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
                        case States.SCREEN_EDITOR: game.enterState(States.SCREEN_EDITOR);
                            break;
                        case States.SCREEN_MAP_CHOOSING: game.enterState(States.SCREEN_MAP_CHOOSING);
                            break;
                        case States.ACTION_EXIT: paramGameContainer.exit();
                            break;
                        default:
                            break;
                    }
                }
            }
        } else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            paramGameContainer.exit();
        }
    }
}
