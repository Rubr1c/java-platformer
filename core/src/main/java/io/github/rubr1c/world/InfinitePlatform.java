package io.github.rubr1c.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InfinitePlatform {
    private static final int SEGMENT_WIDTH = 1920;
    private static final int PLATFORM_HEIGHT = 150; // Visual height of the platform
    private static final int COLLISION_HEIGHT = 40; // Actual collision height (smaller than visual)
    private static final int COLLISION_OFFSET = 50; // Offset from the top of the texture
    private static final int BUFFER_ZONE = SEGMENT_WIDTH * 2;
    private static final int PLATFORM_Y_POSITION = 250; // Higher Y position for platforms

    private List<Platform> activePlatforms;
    private int lastGeneratedX;
    private Color color;
    private Texture platformTexture;
    private boolean useTexture = true;

    public InfinitePlatform(Color color) {
        this();
        this.color = color;
    }

    public InfinitePlatform() {
        activePlatforms = new ArrayList<>();
        lastGeneratedX = -SEGMENT_WIDTH;
        color = Color.GREEN;

        // Load the platform texture
        try {
            platformTexture = new Texture(Gdx.files.internal("images/beam.png"));
        } catch (Exception e) {
            System.err.println("Failed to load platform texture: " + e.getMessage());
            platformTexture = null;
            useTexture = false;
        }

        generateInitialPlatforms();
    }

    private void generateInitialPlatforms() {
        for (int i = 0; i < 3; i++) {
            generatePlatformSegment();
        }
    }

    private void generatePlatformSegment() {
        // Create the platform with a smaller collision height
        // The collision area starts COLLISION_OFFSET pixels from the top of the visual
        // platform
        Platform platform = new Platform(
                (int) lastGeneratedX,
                PLATFORM_Y_POSITION + COLLISION_OFFSET, // Adjust collision Y position
                SEGMENT_WIDTH,
                COLLISION_HEIGHT, // Use smaller collision height
                color);
        activePlatforms.add(platform);
        lastGeneratedX += SEGMENT_WIDTH;
    }

    public void update(OrthographicCamera camera) {
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
        // If texture is not available, fall back to color rendering
        if (!useTexture || platformTexture == null) {
            shapeRenderer.setColor(color);
            for (Platform platform : activePlatforms) {
                // For debugging, you can show the actual collision area
                shapeRenderer.rect(platform.getX(), platform.getY(), platform.getZ(), platform.getW());

                // Optionally, show the full visual area with a different color/alpha
                // shapeRenderer.setColor(new Color(color.r, color.g, color.b, 0.3f));
                // shapeRenderer.rect(platform.getX(), PLATFORM_Y_POSITION, platform.getZ(),
                // PLATFORM_HEIGHT);
            }
        }
    }

    public void renderTexture(SpriteBatch batch) {
        // Only render textures if available
        if (useTexture && platformTexture != null) {
            for (Platform platform : activePlatforms) {
                // Calculate how many times to repeat the texture to fill the platform width
                float textureWidth = platformTexture.getWidth();
                float textureHeight = platformTexture.getHeight();

                int tilesX = Math.max(1, (int) Math.ceil(platform.getZ() / textureWidth));

                // Draw the texture tiles to cover the entire platform
                // Note: We draw at PLATFORM_Y_POSITION (not platform.getY()) to show the full
                // visual height
                for (int x = 0; x < tilesX; x++) {
                    float tileX = platform.getX() + (x * textureWidth);
                    float tileWidth = Math.min(textureWidth, platform.getX() + platform.getZ() - tileX);

                    // Calculate texture coordinates for partial tiles
                    float u2 = tileWidth / textureWidth;

                    batch.draw(
                            platformTexture,
                            tileX, PLATFORM_Y_POSITION, // Draw at the visual position, not collision position
                            tileWidth, PLATFORM_HEIGHT, // Use full visual height
                            0, 0,
                            u2, 1);
                }

                // Uncomment for debugging - shows the collision area
                // batch.setColor(1, 0, 0, 0.5f);
                // batch.draw(platformTexture, platform.getX(), platform.getY(),
                // platform.getZ(), platform.getW());
                // batch.setColor(1, 1, 1, 1);
            }
        }
    }

    public List<Platform> getActivePlatforms() {
        return activePlatforms;
    }

    public void dispose() {
        if (platformTexture != null) {
            platformTexture.dispose();
        }
    }
}
