package io.github.rubr1c.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.rubr1c.entites.Player;
import io.github.rubr1c.fonts.Fonts;
import io.github.rubr1c.render.Camera;

public class Settings {
    private static boolean showCoordinates = true;
    private static BitmapFont font = Fonts.MINECRAFT_LG.get();
    private static SpriteBatch batch = new SpriteBatch();

    public static void toggleCoordinates() {
        showCoordinates = !showCoordinates;
    }

    public static boolean isShowingCoordinates() {
        return showCoordinates;
    }

    public static void renderCoordinates(Player player, Camera camera) {
        if (!showCoordinates) return;
        batch.setProjectionMatrix(camera.getCamera().combined);
        batch.begin();
        font.setColor(Color.WHITE);
        String coordinates = String.format("X: %.2f, Y: %.2f", player.getPos().x, player.getPos().y);
        float textX = camera.getCamera().position.x - 950; // Position relative to camera
        float textY = camera.getCamera().position.y + 525; // Position at top of screen
        font.draw(batch, coordinates, textX, textY);
        batch.end();
    }
}
