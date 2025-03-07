package io.github.rubr1c.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InfinitePlatform {
    private static final float SEGMENT_WIDTH = 1920f;
    private static final float PLATFORM_HEIGHT = 300f;
    private static final float BUFFER_ZONE = SEGMENT_WIDTH * 2;

    private List<Platform> activePlatforms;
    private float lastGeneratedX;
    private Color color;

    public InfinitePlatform(Color color) {
        this();
        this.color = color;
    }

    public InfinitePlatform() {
        activePlatforms = new ArrayList<>();
        lastGeneratedX = -SEGMENT_WIDTH;
        generateInitialPlatforms();
    }

    private void generateInitialPlatforms() {
        for (int i = 0; i < 3; i++) {
            generatePlatformSegment();
        }
    }

    private void generatePlatformSegment() {
        Platform platform = new Platform((int)lastGeneratedX, 0, (int)SEGMENT_WIDTH, (int)PLATFORM_HEIGHT, color);
        activePlatforms.add(platform);
        lastGeneratedX += SEGMENT_WIDTH;
    }

    public void update(OrthographicCamera camera) {
        // Generate platforms ahead
        while (lastGeneratedX - camera.position.x < BUFFER_ZONE) {
            generatePlatformSegment();
        }

        Iterator<Platform> iterator = activePlatforms.iterator();
        while (iterator.hasNext()) {
            Platform platform = iterator.next();
            if (platform.getX() + SEGMENT_WIDTH < camera.position.x - BUFFER_ZONE) {
                iterator.remove();
            }
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.GREEN);
        for (Platform platform : activePlatforms) {
            shapeRenderer.rect(platform.getX(), platform.getY(), platform.getZ(), platform.getW());
        }
    }

    public List<Platform> getActivePlatforms() {
        return activePlatforms;
    }
}
