package io.github.rubr1c;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.rubr1c.entites.Entities;
import io.github.rubr1c.entites.Entity;
import io.github.rubr1c.util.Movements;
import io.github.rubr1c.world.Platform;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private Entity player;
    private Platform ground;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();

        player = Entities.PLAYER.get();
        player.setPos(200, 240);

        ground = new Platform(0, 200, 1920, 20);
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            Movements.moveLeft(player);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            Movements.moveRight(player);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Movements.jump(player);
        }

        Movements.applyGravity(player);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(ground.getX(), ground.getY(), ground.getZ(), ground.getW());
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(player.pos.x, player.pos.y, player.getWidth(), player.getHeight());
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
