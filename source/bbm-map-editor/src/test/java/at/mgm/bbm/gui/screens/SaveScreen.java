package at.mgm.bbm.gui.screens;

import at.mgm.bbm.core.States;
import at.mgm.bbm.core.map.Map;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.HashMap;

public class SaveScreen extends BasicGameState {

    private final String TITLE = "Save map as:";
    private final String SAVE = "save";
    private final String BACK = "back";

    Shape save;
    Shape back;

    private StateBasedGame game;
    private Font font;
    private TextField textField;

    private int TITLE_WIDTH = 0;
    private int TITLE_HEIGHT = 0;

    private int SAVE_WIDTH = 0;
    private int SAVE_HEIGHT = 0;

    private int BACK_WIDTH = 0;
    private int BACK_HEIGHT = 0;

    private boolean flag = true;

    @Override
    public int getID() {
        return States.SCREEN_SAVE;
    }

    @Override
    public void enter(GameContainer var1, StateBasedGame var2) throws SlickException {
        if (null != textField) {
            textField.setText(Map.INSTANCE.getMapName());
        }
    }

    @Override
    public void init(final GameContainer paramGameContainer, final StateBasedGame paramStateBasedGame) throws SlickException {
        game = paramStateBasedGame;
        font = new AngelCodeFont("fonts/font.fnt", new Image("fonts/font.png"));
        textField = new TextField(paramGameContainer, font, 100, 540, 1720 , 140);
        textField.setText(Map.INSTANCE.getMapName());

        TITLE_WIDTH = font.getWidth(TITLE);
        TITLE_HEIGHT = font.getHeight(TITLE);

        SAVE_WIDTH = font.getWidth(SAVE);
        SAVE_HEIGHT = font.getHeight(SAVE);

        BACK_WIDTH = font.getWidth(BACK);
        BACK_HEIGHT = font.getHeight(BACK);

        save = new Rectangle(430 - (SAVE_WIDTH / 2), 800, SAVE_WIDTH, SAVE_HEIGHT);
        back = new Rectangle(1290 - (BACK_WIDTH / 2), 800, BACK_WIDTH, BACK_HEIGHT);
    }

    @Override
    public void render(final GameContainer paramGameContainer, final StateBasedGame paramStateBasedGame, Graphics paramGraphics) throws SlickException {
        textField.render(paramGameContainer, paramGraphics);
        font.drawString(860 - (TITLE_WIDTH / 2), 100, TITLE);
        font.drawString(430 - (SAVE_WIDTH / 2), 800, SAVE);
        font.drawString(1290 - (BACK_WIDTH / 2), 800, BACK);
    }

    @Override
    public void update(GameContainer paramGameContainer, StateBasedGame paramStateBasedGame, int i) throws SlickException {
        Input input = paramGameContainer.getInput();

        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            if (save.contains(mouseX, mouseY)) {
                save();
            } else if (back.contains(mouseX, mouseY)) {
                game.enterState(States.SCREEN_EDITOR);
            }
        } else if (input.isKeyPressed(Input.KEY_ENTER)) {
            save();
        } else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(States.SCREEN_EDITOR);
        }
    }

    private void save() {
        if (Map.INSTANCE.saveMap(textField.getText())) {
            System.out.println("Successfully saved map!");
            game.enterState(States.SCREEN_EDITOR);
        } else {
            System.out.println("Saving map failed!");
        }
    }
}
