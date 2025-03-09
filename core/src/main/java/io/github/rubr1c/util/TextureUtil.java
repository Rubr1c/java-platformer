package io.github.rubr1c.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class TextureUtil {
    public static Texture getBlank() {
        Pixmap pixmap = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture blankTexture = new Texture(pixmap);
        pixmap.dispose();
        return blankTexture;
    }

    public static Texture loadFrom(String path) {
        return new Texture(
                Gdx.files.internal(path));
    }

    /**
     * Create a texture with a solid color
     *
     * @param color  The color to use
     * @param width  The width of the texture
     * @param height The height of the texture
     * @return A new texture with the specified color
     */
    public static Texture createColorTexture(Color color, int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture colorTexture = new Texture(pixmap);
        pixmap.dispose();
        return colorTexture;
    }
}
