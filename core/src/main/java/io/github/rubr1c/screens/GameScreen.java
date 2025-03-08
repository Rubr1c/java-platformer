package io.github.rubr1c.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import io.github.rubr1c.entites.Player;
import io.github.rubr1c.fonts.Fonts;
import io.github.rubr1c.input.InputHandler;
import io.github.rubr1c.inventory.Items;
import io.github.rubr1c.registries.ItemRegistry;
import io.github.rubr1c.render.Camera;
import io.github.rubr1c.render.Renderer;
import io.github.rubr1c.util.Settings;
import io.github.rubr1c.world.Border;
import io.github.rubr1c.world.InfinitePlatform;
import io.github.rubr1c.world.Platform;
import io.github.rubr1c.Main;

import java.util.List;

public class GameScreen extends BaseScreen {
    private Player player;
    private InfinitePlatform ground;
    private List<Platform> obs;
    private Camera camera;
    private Renderer renderer;
    private BitmapFont font;
    private final Levels currentLevel;
    private Texture background;

    public GameScreen(Main game, int levelNumber) {
        super(game);
        this.currentLevel = Levels.LEVEL_1;
        initialize();
    }

    public GameScreen(Main game) {
        this(game, 1); // Default to level 1
    }

    private void initialize() {
        camera = new Camera();
        renderer = new Renderer();

        player = new Player();
        player.setPos(currentLevel.getPlayerStartX(), currentLevel.getPlayerStartY());

        ground = new InfinitePlatform(Color.GREEN);
        obs = currentLevel.createObstacles();
        obs.addAll(currentLevel.createBorders());

        currentLevel.initializeItems(player);
        background = currentLevel.getBackground();

        font = Fonts.MINECRAFT_SM.get();
    }


    @Override
    public void render(float delta) {
        InputHandler.HandlePlayer(player);
        renderer.setFont(font);
        camera.update(player);
        ground.update(camera.getCamera());
        renderer.render(background, camera.getCamera(), ground, obs, player);
        Settings.renderCoordinates(player, camera);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        camera.dispose();
    }
}
