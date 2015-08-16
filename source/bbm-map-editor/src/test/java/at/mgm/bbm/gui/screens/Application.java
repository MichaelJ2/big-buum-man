package at.mgm.bbm.gui.screens;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Application extends StateBasedGame {

    public Application(String paramName) {
        super(paramName);
    }

    @Override
    public void initStatesList(final GameContainer paramGameContainer) throws SlickException {
        addState(new MenuScreen());
        addState(new LoadMap());
        addState(new EditorScreen());
        addState(new SaveScreen());
        addState(new NewMapScreen());
    }
}
