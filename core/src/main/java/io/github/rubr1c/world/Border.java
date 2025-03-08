package io.github.rubr1c.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import io.github.rubr1c.util.Shape;

import java.util.ArrayList;
import java.util.List;

public class Border extends Platform {
    private static Texture borderTexture;
    private boolean useTexture = true;

    // Keep track of all border instances
    private static List<Border> allBorders = new ArrayList<>();

    static {
        // Load the texture once for all borders
        try {
            borderTexture = new Texture(Gdx.files.internal("images/border.png"));
            // Set texture to repeat for tiling
            borderTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
        } catch (Exception e) {
            System.err.println("Failed to load border texture: " + e.getMessage());
            borderTexture = null;
        }
    }

    public Border(int x, int y, int width, int height) {
        super(x, y, width, height);
        allBorders.add(this);
    }

    public Border(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        allBorders.add(this);
    }

    public static Texture getBorderTexture() {
        return borderTexture;
    }

    public boolean isUseTexture() {
        return useTexture;
    }

    public void setUseTexture(boolean useTexture) {
        this.useTexture = useTexture;
    }

    /**
     * Get all border instances
     *
     * @return List of all border instances
     */
    public static List<Border> getAllBorders() {
        return allBorders;
    }

    /**
     * Clear all border instances (useful when changing levels)
     */
    public static void clearAllBorders() {
        allBorders.clear();
    }

    /**
     * Reload the border texture if needed
     */
    public static void reloadTexture() {
        try {
            if (borderTexture != null) {
                borderTexture.dispose();
            }
            borderTexture = new Texture(Gdx.files.internal("images/border.png"));
            borderTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
        } catch (Exception e) {
            System.err.println("Failed to reload border texture: " + e.getMessage());
            borderTexture = null;
        }
    }

    /**
     * Dispose of the texture when no longer needed
     */
    public static void disposeTexture() {
        if (borderTexture != null) {
            borderTexture.dispose();
            borderTexture = null;
        }
    }
}
