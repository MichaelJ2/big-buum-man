package at.mgm.bbm.gui.screens;

import at.mgm.bbm.core.Field;
import at.mgm.bbm.core.FieldType;
import at.mgm.bbm.core.States;
import at.mgm.bbm.core.fields.FieldFactory;
import at.mgm.bbm.core.map.Map;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.lang.Enum;
import java.util.HashMap;
import java.util.List;

public class EditorScreen extends BasicGameState {

    private Map map;

    private final java.util.Map<Enum, Image> TEXTURES = new HashMap<Enum, Image>();
    private final java.util.Map<Enum, Shape> FIELD_TYPES = new HashMap<Enum, Shape>();

    private static final int TYPES_X = 50;
    private static final int TYPES_Y = 50;
    private static int TYPES_OFFSET = 25;
    private final FieldType[] enums = FieldType.values();

    private FieldType choosenType = FieldType.GROUND;
    private StateBasedGame game;

    private final String SAVE = "save";
    private final String BACK = "back";

    private Shape saveButton;
    private Shape backButton;
    Font font;

    @Override
    public int getID() {
        return States.SCREEN_EDITOR;
    }

    @Override
    public void init(GameContainer paramGameContainer, StateBasedGame paramStateBasedGame) throws SlickException {
        game = paramStateBasedGame;
        for (int i = 0; i < enums.length; i++) {
            TEXTURES.put(enums[i], new Image(enums[i].TEXTURE_PATH));
            if (!enums[i].equals(FieldType.BORDER)) {
                FIELD_TYPES.put(enums[i], new Rectangle(TYPES_X, TYPES_Y + ((64 + TYPES_OFFSET) * i), 64, 64));
            }
        }
        map = new Map(null);
        font = new AngelCodeFont("fonts/font.fnt", new Image("fonts/font.png"));
        saveButton = new Rectangle(TYPES_X, 800, font.getWidth(SAVE), font.getHeight(SAVE));
        backButton = new Rectangle(TYPES_X, 920, font.getWidth(BACK), font.getHeight(BACK));
    }

    @Override
    public void render(GameContainer paramGameContainer, StateBasedGame paramStateBasedGame, Graphics paramGraphics) throws SlickException {
        // render field types
        for (int i = 0; i < enums.length; i++) {
            if (!enums[i].equals(FieldType.BORDER)) {
                TEXTURES.get(enums[i]).draw(TYPES_X, TYPES_Y + ((64  + TYPES_OFFSET) * i));
                paramGraphics.drawString(enums[i].name(), TYPES_X + 1, TYPES_Y + ((64  + TYPES_OFFSET) * i));
            }
        }
        // render map
        for (List<Field> fields : map.getMap()) {
            for (Field field : fields) {
                TEXTURES.get(field.getFieldType()).draw(field.x, field.y);
            }
        }
        // render save button
        font.drawString(TYPES_X, 800, SAVE);
        font.drawString(TYPES_X, 920, BACK);
    }

    @Override
    public void update(GameContainer paramGameContainer, StateBasedGame paramStateBasedGame, int delta) throws SlickException {
        Input input = paramGameContainer.getInput();

        int mausX = input.getMouseX();
        int mausY = input.getMouseY();

        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            System.out.println(String.format("%d x %d", mausX, mausY));

            // check map
            List<List<Field>> rows = map.getMap();
            for (int i = 0; i < rows.size(); i++) {
                List<Field> columns = rows.get(i);
                for (int j = 0; j < columns.size(); j++) {
                    Field field = columns.get(j);
                    if (!field.getFieldType().LOCKED && !field.getFieldType().equals(choosenType) && field.checkCollision(mausX, mausY)) {
                        columns.set(j, FieldFactory.getField(choosenType, field.x, field.y));
                    }
                }
            }

            // check if choosing type
            for (int i = 0; i < enums.length; i++) {
                if (!enums[i].equals(FieldType.BORDER)) {
                    if (FIELD_TYPES.get(enums[i]).contains(mausX, mausY)) {
                        choosenType = enums[i];
                    }
                }
            }

            // check save button
            if (saveButton.contains(mausX, mausY)) {
                if (map.saveMap()) {
                    System.out.println("MAP SAVED");
                }
            } else if (backButton.contains(mausX, mausY)) {
                game.enterState(States.SCREEN_MENU);
            }
        } else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(States.SCREEN_MENU);
        }
    }
}
