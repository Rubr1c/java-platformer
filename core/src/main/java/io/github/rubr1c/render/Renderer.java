package io.github.rubr1c.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.rubr1c.entites.Entity;
import io.github.rubr1c.util.Shape;
import io.github.rubr1c.world.InfinitePlatform;
import io.github.rubr1c.world.Platform;

import java.util.List;

public class Renderer {
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;

    public Renderer() {
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
    }

    public void render(OrthographicCamera camera,
                       InfinitePlatform ground,
                       List<? extends Shape> obs,
                       Entity player) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Render infinite platform
        ground.render(shapeRenderer);

        // Render obstacle
        for (Shape shape: obs) {
            shapeRenderer.setColor(shape.getColor());
            shapeRenderer.rect(shape.getX(), shape.getY(), shape.getZ(), shape.getW());
        }

        shapeRenderer.end();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if (player.getTexture() != null) {
            batch.draw(player.getTexture(), player.getPos().x, player.getPos().y, player.getWidth(), player.getHeight());
        }
        batch.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
