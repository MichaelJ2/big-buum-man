package at.mgm.bbm.gui.screens;

import at.mgm.bbm.core.FileUtils;
import at.mgm.bbm.core.States;
import at.mgm.bbm.core.map.Map;
import at.mgm.bbm.gui.Resources;
import at.mgm.bbm.gui.entries.Entry;
import at.mgm.bbm.gui.entries.ListEntry;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoadMap extends BasicGameState {

    private final String TITLE = "Choose a map";
    private StateBasedGame game;

    private List<ListEntry> entries = new ArrayList<ListEntry>();
    private Font font;
    private Font font2;

    @Override
    public int getID() {
        return States.SCREEN_LOAD_MAP;
    }

    @Override
    public void init(final GameContainer paramGameContainer, final StateBasedGame paramStateBasedGame) throws SlickException {
        game = paramStateBasedGame;

        File[] files = FileUtils.INSTANCE.getMaps(".");

        font  = paramGameContainer.getDefaultFont();
        font2 = Resources.INSTANCE.font;

        int x = 100;

        for (int i = 0; i < files.length; i++) {
            entries.add(new ListEntry(x, 300 + (20 * i), font.getWidth(files[i].getName()), font.getHeight(files[i].getName()), i, files[i]));
        }
    }

    @Override
    public void render(final GameContainer paramGameContainer, final StateBasedGame paramStateBasedGame, final Graphics paramGraphics) throws SlickException {
        font2.drawString(960 - (font2.getWidth(TITLE) / 2), 100, TITLE);
        for (ListEntry entry : entries) {
            font.drawString(entry.x, entry.y, entry.text);
        }
    }

    @Override
    public void update(final GameContainer paramGameContainer, final StateBasedGame paramStateBasedGame, int i) throws SlickException {
        Input input = paramGameContainer.getInput();

        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            for (ListEntry entry : entries) {
                if (entry.checkCollision(mouseX, mouseY)) {
                    System.out.println(entry.text);
                    File map = (File) entry.getObject();
                    Map.INSTANCE.loadMap(map, 0, 0);
                    game.enterState(States.SCREEN_EDITOR);
                }
            }
        } else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(States.SCREEN_MENU);
        }
    }
}
