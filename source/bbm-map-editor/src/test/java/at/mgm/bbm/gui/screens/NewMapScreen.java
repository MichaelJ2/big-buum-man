package at.mgm.bbm.gui.screens;

import at.mgm.bbm.core.States;
import at.mgm.bbm.core.map.Map;
import at.mgm.bbm.gui.Resources;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class NewMapScreen extends BasicGameState {

    private final String TITLE = "Create new map";
    private final String OK = "ok";
    private final String BACK = "back";
    private final String X = "X";

    private Shape ok;
    private Shape back;

    private StateBasedGame game;
    private Font font;
    private TextField xField;
    private TextField yField;

    private int TITLE_X = 0;

    private int OK_X = 0;
    private int OK_Y = 0;

    private int BACK_X = 0;
    private int BACK_Y = 0;

    private int X_X = 0;

    private boolean invalid = false;
    private String reason = "invalid input";
    private int REASON_X = 0;

    @Override
    public int getID() {
        return States.SCREEN_NEW_MAP;
    }

    @Override
    public void init(final GameContainer paramGameContainer, final StateBasedGame paramStateBasedGame) throws SlickException {
        game = paramStateBasedGame;

        font = Resources.INSTANCE.font;

        xField = new TextField(paramGameContainer, font, 510, 540, 300 , 140);
        yField = new TextField(paramGameContainer, font, 1060, 540, 300 , 140);

        xField.setBackgroundColor(Color.white);
        yField.setBackgroundColor(Color.white);

        xField.setBorderColor(Color.black);
        yField.setBorderColor(Color.black);

        xField.setText("" + 9);
        yField.setText("" + 9);

        TITLE_X = font.getWidth(TITLE) / 2;

        OK_X = font.getWidth(OK);
        OK_Y = font.getHeight(OK);

        BACK_X = font.getWidth(BACK);
        BACK_Y = font.getHeight(BACK);

        X_X = font.getWidth(X) / 2;

        REASON_X = font.getWidth(reason);

        ok = new Rectangle(1290 - (OK_X / 2), 800, OK_X, OK_Y);
        back = new Rectangle(430 - (BACK_X / 2), 800, BACK_X, BACK_Y);
    }

    @Override
    public void render(final GameContainer paramGameContainer, final StateBasedGame paramStateBasedGame, Graphics paramGraphics) throws SlickException {
        xField.render(paramGameContainer, paramGraphics);
        yField.render(paramGameContainer, paramGraphics);
        font.drawString(960 - TITLE_X, 100, TITLE);
        font.drawString(1290 - (OK_X / 2), 800, OK);
        font.drawString(430 - (BACK_X / 2), 800, BACK);
        font.drawString(960 - X_X, 540, X);

        if (invalid) {
            font.drawString(1920 - REASON_X, 900, reason);
        }
    }

    @Override
    public void update(final GameContainer paramGameContainer, final StateBasedGame paramStateBasedGame, final int delta) throws SlickException {
        Input input = paramGameContainer.getInput();

        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            if (ok.contains(mouseX, mouseY)) {
                createMap(xField.getText(), yField.getText());
            } else if (back.contains(mouseX, mouseY)) {
                game.enterState(States.SCREEN_MENU);
            }
            System.out.println(String.format("%d x %d", mouseX, mouseY));
        } else if (input.isKeyPressed(Input.KEY_ENTER)) {
            createMap(xField.getText(), yField.getText());
        } else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(States.SCREEN_MENU);
        }

        REASON_X = font.getWidth(reason);
    }

    private void createMap(final String paramX, final String paramY) {
        try {
            int xx = Integer.valueOf(xField.getText());
            int yy = Integer.valueOf(yField.getText());
            if (xx > 22 || yy > 22) {
                reason = "max 22 fields";
                invalid = true;
            } else if(xx < 3 || yy < 3) {
                reason = "min 3 fields";
                invalid = true;
            } else {
                Map.INSTANCE.createMap(xx, yy, 0, 0);
                invalid = false;
                game.enterState(States.SCREEN_EDITOR);
            }
        } catch (NumberFormatException e) {
            reason = "invalid input";
            invalid = true;
        }
    }
}
