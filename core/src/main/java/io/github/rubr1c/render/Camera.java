package io.github.rubr1c.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import io.github.rubr1c.entites.Entity;

public class Camera {
    private OrthographicCamera camera;
    private final float lerp = 0.1f;

    public Camera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void update(Entity target) {
        // Smoothly interpolate camera position towards target
        float targetX = target.getPos().x;
        float targetY = target.getPos().y;

        camera.position.x += (targetX - camera.position.x) * lerp;
        camera.position.y += (targetY - camera.position.y) * lerp;

        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void dispose() {
    }
}
