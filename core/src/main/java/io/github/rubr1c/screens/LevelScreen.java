package io.github.rubr1c.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import io.github.rubr1c.Main;
import io.github.rubr1c.fonts.Fonts;
import io.github.rubr1c.registries.ItemRegistry;
import io.github.rubr1c.render.Renderer;
import io.github.rubr1c.save.GameData;
import io.github.rubr1c.save.SaveManager;
import io.github.rubr1c.util.Button;

import java.util.ArrayList;
import java.util.List;

public class LevelScreen extends BaseScreen {
    private final Renderer renderer;
    private final float buttonSize = 100;
    private final List<Button> buttons;
    private final BitmapFont font;
    private final GameData gameData;

    public LevelScreen(Main game) {
        super(game);
        renderer = new Renderer();
        renderer.setGame(game);
        buttons = new ArrayList<>();
        gameData = SaveManager.loadGame();

        float spacing = 20;
        int buttonsPerRow = 5;
        float startX = 700;
        float startY = 700;

        for (int i = 0; i < 20; i++) {
            int row = i / buttonsPerRow;
            int col = i % buttonsPerRow;
            float x = startX + (col * (buttonSize + spacing));
            float y = startY - (row * (buttonSize + spacing));

            int levelNumber = i + 1;
            boolean isUnlocked = gameData.isLevelUnlocked(levelNumber);
            buttons.add(
                new Button(
                    String.valueOf(levelNumber),
                    x, y,
                    buttonSize, buttonSize,
                    isUnlocked ? () -> game.setScreen(new GameScreen(game)) : null,
                    isUnlocked ? Color.GRAY : Color.DARK_GRAY
                )
            );
        }

        buttons.add(new Button(
            "back",
            20, 20,
            200, 100,
            () -> game.setScreen(new MainMenuScreen(game)),
            Color.GRAY
        ));

        font = Fonts.MINECRAFT_LG.get();
        ItemRegistry.initItemTextures();
    }

    @Override
    public void render(float delta) {
        renderer.setFont(font);
        renderer.renderButtons(buttons);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        SaveManager.saveGame(gameData);
    }
}
