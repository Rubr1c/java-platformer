package io.github.rubr1c;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.rubr1c.entites.Entities;
import io.github.rubr1c.entites.Entity;
import io.github.rubr1c.input.InputHandler;
import io.github.rubr1c.movement.Movements;
import io.github.rubr1c.render.Camera;
import io.github.rubr1c.world.InfinitePlatform;
import io.github.rubr1c.world.Platform;
import io.github.rubr1c.render.Renderer;

import java.util.ArrayList;
import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private Entity player;
    private InfinitePlatform ground;
    private List<Platform> obs;
    private Camera camera;
    private Renderer renderer;

    @Override
    public void create() {
        camera = new Camera();
        renderer = new Renderer();

        player = Entities.PLAYER.get();
        player.setPos(200, 400);

        ground = new InfinitePlatform(Color.GREEN);
        obs = new ArrayList<>();
        obs.add(new Platform(500, 350, 50, 10, Color.RED));
    }

    @Override
    public void render() {
        InputHandler.HandlePlayer(player);

        camera.update(player);
        ground.update(camera.getCamera());

        renderer.render(camera.getCamera(), ground, obs, player);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
