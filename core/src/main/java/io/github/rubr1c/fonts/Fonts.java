package io.github.rubr1c.fonts;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public enum Fonts {

    MINECRAFT_LG("fonts/minecraft_font.ttf", 24),
    MINECRAFT_SM("fonts/minecraft_font.ttf",10);

    private final BitmapFont font;

    Fonts(String path, int size) {
        this.font = FontLoader.loadFont(path, size);
    }

    public BitmapFont get() {
        return this.font;
    }
}
