package at.mgm.bbm.gui.screens;

import at.mgm.bbm.core.FileUtils;
import at.mgm.bbm.core.States;
import at.mgm.bbm.core.map.Map;
import at.mgm.bbm.gui.Resources;
import at.mgm.bbm.gui.entries.Entry;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoadMap extends BasicGameState {

    private final String TITLE = "Choose a map";
    private final String BACK = "back";

    private int TITLE_X = 0;

    private int BACK_X = 0;
    private int BACK_Y = 0;

    private StateBasedGame game;

    private List<Entry> entries = new ArrayList<Entry>();
    private Font font;
    private Font font2;

    private Entry back;

    @Override
    public int getID() {
        return States.SCREEN_LOAD_MAP;
    }

    @Override
    public void enter(GameContainer var1, StateBasedGame var2) throws SlickException {
        File[] files = FileUtils.INSTANCE.getMaps(".");

        entries.clear();

        int x = 100;

        for (int i = 0; i < files.length; i++) {
            entries.add(new Entry(i, x, 300 + (20 * i), font.getWidth(files[i].getName()), font.getHeight(files[i].getName()), files[i]));
        }
    }

    @Override
    public void init(final GameContainer paramGameContainer, final StateBasedGame paramStateBasedGame) throws SlickException {
        game = paramStateBasedGame;

        font  = paramGameContainer.getDefaultFont();
        font2 = Resources.INSTANCE.font;

        TITLE_X = font2.getWidth(TITLE) / 2;

        BACK_X = font2.getWidth(BACK);
        BACK_Y = font2.getHeight(BACK);

        back = new Entry(States.ACTION_EXIT, 960 - (BACK_X / 2), 950, BACK_X, BACK_Y, null);
    }

    @Override
    public void render(final GameContainer paramGameContainer, final StateBasedGame paramStateBasedGame, final Graphics paramGraphics) throws SlickException {
        font2.drawString(960 - TITLE_X, 100, TITLE);
        for (Entry entry : entries) {
            font.drawString(entry.x, entry.y, entry.text);
        }
        font2.drawString(960 - (BACK_X / 2), 950, BACK);
    }

    @Override
    public void update(final GameContainer paramGameContainer, final StateBasedGame paramStateBasedGame, int i) throws SlickException {
        Input input = paramGameContainer.getInput();

        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            for (Entry entry : entries) {
                if (entry.checkCollision(mouseX, mouseY)) {
                    System.out.println(entry.text);
                    File map = (File) entry.getObject();
                    Map.INSTANCE.loadMap(map, 0, 0);
                    game.enterState(States.SCREEN_EDITOR);
                }
            }
            if (back.checkCollision(mouseX, mouseY)) {
                game.enterState(States.SCREEN_MENU);
            }
        } else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(States.SCREEN_MENU);
        }
    }
}
