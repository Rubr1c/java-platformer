package io.github.rubr1c.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.rubr1c.util.Shape;
import io.github.rubr1c.util.Vector4;

import java.util.ArrayList;
import java.util.List;

public class Platform extends Shape {
    private static List<Vector4> BORDERS = new ArrayList<>();
    private static Texture platformTexture;
    private boolean useTexture = true;

    static {
        // Load the texture once for all platforms
        try {
            platformTexture = new Texture(Gdx.files.internal("images/platform.png"));
            // Set texture filtering for better stretching quality
            platformTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } catch (Exception e) {
            System.err.println("Failed to load platform texture: " + e.getMessage());
            platformTexture = null;
        }
    }

    public Platform(int x, int y, int width, int height) {
        super(x, y, width, height);
        BORDERS.add(new Vector4(x, y, x + width, y + height));
    }

    public Platform(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        BORDERS.add(new Vector4(x, y, x + width, y + height));
    }

    public static List<Vector4> getBorders() {
        return BORDERS;
    }

    public static Texture getPlatformTexture() {
        return platformTexture;
    }

    public boolean isUseTexture() {
        return useTexture;
    }

    public void setUseTexture(boolean useTexture) {
        this.useTexture = useTexture;
    }

    /**
     * Render the platform with a texture using the provided SpriteBatch
     *
     * @param batch The SpriteBatch to use for rendering
     */
    public void renderWithTexture(SpriteBatch batch) {
        if (useTexture && platformTexture != null) {
            batch.draw(
                    platformTexture,
                    getX(), getY(), // Position
                    getZ(), getW() // Size (width, height)
            );
        }
    }

    /**
     * Dispose of the texture when no longer needed
     */
    public static void disposeTexture() {
        if (platformTexture != null) {
            platformTexture.dispose();
            platformTexture = null;
        }
    }
}
