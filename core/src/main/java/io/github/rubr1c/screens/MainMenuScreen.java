package io.github.rubr1c.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;
import io.github.rubr1c.Main;
import io.github.rubr1c.fonts.Fonts;
import io.github.rubr1c.render.Renderer;
import io.github.rubr1c.util.Button;

public class MainMenuScreen extends BaseScreen {
    private final Renderer renderer;
    private final float buttonHeight = 100;
    private final float buttonWidth = 200;
    private final List<Button> buttons;
    private final BitmapFont font;

    public MainMenuScreen(Main game) {
        super(game);
        renderer = new Renderer();
        renderer.setGame(game);
        buttons = new ArrayList<>();
        buttons.add(new Button("Play", (Gdx.graphics.getWidth() - buttonWidth) / 2,
                            (Gdx.graphics.getHeight() - buttonHeight) / 2,
                             buttonWidth, buttonHeight, () -> game.setScreen(new LevelScreen(game)),
                             Color.GRAY));

        font = Fonts.MINECRAFT_LG.get();

    }

    @Override
    public void render(float delta) {
        renderer.setFont(font);
        renderer.renderButtons(buttons);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
