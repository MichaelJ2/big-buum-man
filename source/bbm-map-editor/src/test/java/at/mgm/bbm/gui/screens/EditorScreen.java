package at.mgm.bbm.gui.screens;

import at.mgm.bbm.core.Field;
import at.mgm.bbm.core.FieldType;
import at.mgm.bbm.core.States;
import at.mgm.bbm.core.fields.FieldFactory;
import at.mgm.bbm.core.map.Map;
import at.mgm.bbm.gui.Resources;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.HashMap;
import java.util.List;

public class EditorScreen extends BasicGameState {

    private final java.util.Map<Integer, Image> TEXTURES = new HashMap<Integer, Image>();
    private final java.util.Map<Integer, Shape> FIELD_TYPES = new HashMap<Integer, Shape>();

    private static final int TYPES_X = 50;
    private static final int TYPES_Y = 50;
    private static int TYPES_OFFSET = 25;
    private final FieldType[] enums = FieldType.values();

    private FieldType chosenType = FieldType.GROUND;
    private StateBasedGame game;

    private final String SAVE = "save";
    private final String LOAD = "load";
    private final String BACK = "back";

    private final int SAVE_X = 700;
    private final int LOAD_X = 820;
    private final int BACK_X = 940;

    private Shape saveButton;
    private Shape loadButton;
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
            TEXTURES.put(enums[i].ID, new Image(enums[i].TEXTURE));
            if (!enums[i].equals(FieldType.BORDER)) {
                FIELD_TYPES.put(enums[i].ID, new Rectangle(TYPES_X, TYPES_Y + ((Map.FIELD_SIZE + TYPES_OFFSET) * i), Map.FIELD_SIZE, Map.FIELD_SIZE));
            }
        }
        font = Resources.INSTANCE.font;
        saveButton = new Rectangle(TYPES_X, SAVE_X, font.getWidth(SAVE), font.getHeight(SAVE));
        loadButton = new Rectangle(TYPES_X, LOAD_X, font.getWidth(LOAD), font.getHeight(LOAD));
        backButton = new Rectangle(TYPES_X, BACK_X, font.getWidth(BACK), font.getHeight(BACK));
    }

    @Override
    public void render(GameContainer paramGameContainer, StateBasedGame paramStateBasedGame, Graphics paramGraphics) throws SlickException {
        // render field types
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].ID != FieldType.BORDER.ID) {
                TEXTURES.get(enums[i].ID).draw(TYPES_X, TYPES_Y + ((Map.FIELD_SIZE  + TYPES_OFFSET) * i));
                paramGraphics.drawString(enums[i].name(), TYPES_X + 1, TYPES_Y + ((Map.FIELD_SIZE  + TYPES_OFFSET) * i));
            }
        }
        // render map
        for (List<Field> fields : Map.INSTANCE.getMap()) {
            for (Field field : fields) {
                TEXTURES.get(field.ID).draw(field.x, field.y);
            }
        }
        // render save button
        font.drawString(TYPES_X, SAVE_X, SAVE);
        font.drawString(TYPES_X, LOAD_X, LOAD);
        font.drawString(TYPES_X, BACK_X, BACK);
    }

    @Override
    public void update(GameContainer paramGameContainer, StateBasedGame paramStateBasedGame, int delta) throws SlickException {
        Input input = paramGameContainer.getInput();

        int mausX = input.getMouseX();
        int mausY = input.getMouseY();

        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {

            // check map
            List<List<Field>> rows = Map.INSTANCE.getMap();
            for (int i = 0; i < rows.size(); i++) {
                List<Field> columns = rows.get(i);
                for (int j = 0; j < columns.size(); j++) {
                    Field field = columns.get(j);
                    if (!field.isLocked && field.ID != chosenType.ID && field.checkCollision(mausX, mausY)) {
                        columns.set(j, FieldFactory.getField(chosenType, field.x, field.y));
                        System.out.println(chosenType.name() + (field.fieldType != chosenType));
                    }
                }
            }

            // check if choosing type
            for (int i = 0; i < enums.length; i++) {
                if (!enums[i].equals(FieldType.BORDER)) {
                    if (FIELD_TYPES.get(enums[i].ID).contains(mausX, mausY)) {
                        chosenType = enums[i];
                        System.out.println(enums[i].name());
                    }
                }
            }

            // check save button
            if (saveButton.contains(mausX, mausY)) {
                game.enterState(States.SCREEN_SAVE);
            } else if (loadButton.contains(mausX, mausY)) {
                game.enterState(States.SCREEN_LOAD_MAP);
            } else if (backButton.contains(mausX, mausY)) {
                game.enterState(States.SCREEN_MENU);
            }
        } else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(States.SCREEN_MENU);
        }
    }
}
