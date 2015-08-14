package at.mgm.bbm.core.screens;

import at.mgm.bbm.core.Field;
import at.mgm.bbm.core.FieldType;
import at.mgm.bbm.core.map.Map;
import org.newdawn.slick.*;

import java.util.HashMap;
import java.util.List;

public class EditorScreen extends BasicGame {

    private Map map;

    private java.util.Map<Enum, Image> textures = new HashMap<Enum, Image>();

    public EditorScreen() {
        super("Map Editor");
    }

    @Override
    public void init(GameContainer paramGameContainer) throws SlickException {
        for (Enum e : FieldType.values()) {
            textures.put(e, new Image(FieldType.valueOf(e.name()).TEXTURE_PATH));
        }
        map = new Map(null);
    }

    @Override
    public void update(GameContainer paramGameContainer, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer paramGameContainer, Graphics paramGraphics) throws SlickException {
        for (List<Field> fields : map.getMap()) {
            for (Field field : fields) {
                textures.get(field.getFieldType()).draw(field.x, field.y);
            }
        }
    }
}
