package io.github.rubr1c.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import io.github.rubr1c.entites.Player;
import io.github.rubr1c.fonts.Fonts;
import io.github.rubr1c.input.InputHandler;
import io.github.rubr1c.inventory.Items;
import io.github.rubr1c.registries.ItemRegistry;
import io.github.rubr1c.render.Camera;
import io.github.rubr1c.render.Renderer;
import io.github.rubr1c.world.InfinitePlatform;
import io.github.rubr1c.world.Platform;
import io.github.rubr1c.Main;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends BaseScreen {
    private Player player;
    private InfinitePlatform ground;
    private List<Platform> obs;
    private Camera camera;
    private Renderer renderer;
    private BitmapFont font;

    public GameScreen(Main game) {
        super(game);
        initialize();
    }

    private void initialize() {
        camera = new Camera();
        renderer = new Renderer();

        player = new Player();
        player.setPos(200, 400);

        ground = new InfinitePlatform(Color.GREEN);
        obs = new ArrayList<>();
        obs.add(new Platform(500, 350, 50, 10, Color.RED));
        ItemRegistry.register("d_sword", Items.SWORD.get());
        player.addToInventory(Items.SWORD.get(), 2);
        ItemRegistry.initItemTextures();
        font = Fonts.MINECRAFT_SM.get();
    }

    @Override
    public void render(float delta) {
        InputHandler.HandlePlayer(player);
        renderer.setFont(font);
        camera.update(player);
        ground.update(camera.getCamera());

        renderer.render(camera.getCamera(), ground, obs, player);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        camera.dispose();
    }
}
