package io.github.rubr1c;

import com.badlogic.gdx.Game;
import io.github.rubr1c.screens.MainMenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        if (screen != null) {
            screen.dispose();
        }
    }
}
