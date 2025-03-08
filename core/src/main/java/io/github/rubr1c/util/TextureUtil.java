package io.github.rubr1c.util;

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
}
