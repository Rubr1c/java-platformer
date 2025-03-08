package io.github.rubr1c.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.rubr1c.Main;
import io.github.rubr1c.entites.Player;
import io.github.rubr1c.fonts.Fonts;
import io.github.rubr1c.input.InputHandler;
import io.github.rubr1c.inventory.Item;
import io.github.rubr1c.screens.GameScreen;
import io.github.rubr1c.screens.LevelScreen;
import io.github.rubr1c.util.Button;
import io.github.rubr1c.util.Shape;
import io.github.rubr1c.world.InfinitePlatform;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Renderer {
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private Main game;

    public Renderer() {
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        this.font = Fonts.MINECRAFT_LG.get();
    }

    public void render(Texture background,
            OrthographicCamera camera,
            InfinitePlatform ground,
            List<? extends Shape> obs,
            Player player) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // Draw background with increased size (2x wider and taller)
        float bgWidth = Gdx.graphics.getWidth() * 2.5f;
        float bgHeight = Gdx.graphics.getHeight() * 2.5f;
        // Center the background on the camera position
        float bgX = camera.position.x - bgWidth / 2;
        float bgY = camera.position.y - bgHeight / 2;
        batch.draw(background, bgX, bgY, bgWidth, bgHeight);
        batch.end();

        // First render all shapes that don't use textures
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Shape shape : obs) {
            // Skip borders as they'll be rendered with textures
            if (!(shape instanceof io.github.rubr1c.world.Border)) {
                shapeRenderer.setColor(shape.getColor());
                shapeRenderer.rect(shape.getX(), shape.getY(), shape.getZ(), shape.getW());
            }
        }

        ground.render(shapeRenderer);
        shapeRenderer.end();

        // Now render all borders with textures
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Draw borders with texture
        for (Shape shape : obs) {
            if (shape instanceof io.github.rubr1c.world.Border) {
                io.github.rubr1c.world.Border border = (io.github.rubr1c.world.Border) shape;
                if (border.isUseTexture() && io.github.rubr1c.world.Border.getBorderTexture() != null) {
                    // Get the border texture
                    Texture borderTexture = io.github.rubr1c.world.Border.getBorderTexture();

                    // Calculate how many times to repeat the texture
                    float textureWidth = borderTexture.getWidth();
                    float textureHeight = borderTexture.getHeight();

                    // Calculate the number of tiles needed to cover the border
                    int tilesX = Math.max(1, (int) Math.ceil(border.getZ() / textureWidth));
                    int tilesY = Math.max(1, (int) Math.ceil(border.getW() / textureHeight));

                    // Draw the texture tiles to cover the entire border
                    for (int x = 0; x < tilesX; x++) {
                        for (int y = 0; y < tilesY; y++) {
                            // Calculate the position for this tile
                            float tileX = border.getX() + (x * textureWidth);
                            float tileY = border.getY() + (y * textureHeight);

                            // Calculate the width and height for this tile (handle edge cases)
                            float tileWidth = Math.min(textureWidth, border.getX() + border.getZ() - tileX);
                            float tileHeight = Math.min(textureHeight, border.getY() + border.getW() - tileY);

                            // Calculate texture coordinates for partial tiles
                            float u2 = tileWidth / textureWidth;
                            float v2 = tileHeight / textureHeight;

                            // Draw the tile
                            batch.draw(
                                    borderTexture,
                                    tileX, tileY,
                                    tileWidth, tileHeight,
                                    0, 0,
                                    u2, v2);
                        }
                    }
                } else {
                    // Fallback to color rendering if texture is not available
                    batch.end();
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(border.getColor());
                    shapeRenderer.rect(border.getX(), border.getY(), border.getZ(), border.getW());
                    shapeRenderer.end();
                    batch.begin();
                }
            }
        }

        // Draw player
        if (player.getTexture() != null) {
            batch.draw(player.getTexture(),
                    player.getPos().x,
                    player.getPos().y,
                    player.getWidth(),
                    player.getHeight());
        }
        batch.end();

        if (player.isInvIsOpen() && player.getInventory() != null) {
            Map<Item, Integer> itemsMap = player.getInventory().getItems();

            // Calculate inventory dimensions
            int slotsPerRow = 10;
            int slotSize = 50;
            int totalWidth = slotsPerRow * slotSize;
            int totalHeight = ((player.getInventory().getSlotCount() - 1) / slotsPerRow + 1) * slotSize;

            // Calculate starting position to center inventory
            float startX = camera.position.x - totalWidth / 2;
            float startY = camera.position.y - totalHeight / 2 + 200;
            // Enable blending for transparency
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            // Draw filled rectangles
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(new Color(Color.GRAY.r, Color.GRAY.g, Color.GRAY.b, 0.5f));
            drawSlot(player, slotsPerRow, slotSize, startX, startY);

            // Draw borders
            shapeRenderer.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(new Color(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, 0.7f));
            drawSlot(player, slotsPerRow, slotSize, startX, startY);

            // Disable blending after rendering inventory
            Gdx.gl.glDisable(GL20.GL_BLEND);
            shapeRenderer.end();
        }
    }

    private void drawSlot(Player player,
            int slotsPerRow,
            int slotSize,
            float startX, float startY) {
        Map<Item, Integer> itemsMap = player.getInventory().getItems();

        // First pass: Draw all slot backgrounds
        for (int i = 0; i < player.getInventory().getSlotCount(); i++) {
            int row = i / slotsPerRow;
            int col = i % slotsPerRow;
            float slotX = startX + col * slotSize;
            float slotY = startY + row * slotSize;
            shapeRenderer.rect(slotX, slotY, slotSize, slotSize);
        }

        // End shape renderer and begin batch for all textures and text
        shapeRenderer.end();
        batch.begin();

        // Second pass: Draw all items and their counts
        for (int i = 0; i < player.getInventory().getSlotCount(); i++) {
            int row = i / slotsPerRow;
            int col = i % slotsPerRow;
            float slotX = startX + col * slotSize;
            float slotY = startY + row * slotSize;

            int currentSlot = i;
            Optional<Item> slotItem = itemsMap.keySet().stream().filter(item -> item.getSlot() == currentSlot)
                    .findFirst();

            if (slotItem.isPresent() && slotItem.get().getTexture() != null) {
                Item item = slotItem.get();

                // Draw item texture
                float itemSize = slotSize * 0.8f;
                float itemX = slotX + (slotSize - itemSize) / 2;
                float itemY = slotY + (slotSize - itemSize) / 2;
                batch.draw(item.getTexture(), itemX, itemY, itemSize, itemSize);

                // Draw item count at bottom right
                int itemCount = itemsMap.get(item);
                if (itemCount > 1) {
                    String countText = String.valueOf(itemCount);
                    float countX = slotX + slotSize - 15;
                    float countY = slotY + 15;
                    font.setColor(Color.WHITE);
                    font.draw(batch, countText, countX, countY);
                }
            }
        }

        batch.end();
        // Resume shape renderer for the next operation
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    }

    public void setGame(Main game) {
        this.game = game;
    }

    public void renderButtons(List<Button> buttons) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (Button button : buttons) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(button.getColor());
            shapeRenderer.rect(button.getX(), button.getY(), button.getZ(), button.getW());
            shapeRenderer.end();

            batch.begin();
            font.setColor(Color.WHITE);
            float textX = button.getX() + (button.getZ() - font.draw(batch, button.getText(), 0, 0).width) / 2;
            float textY = button.getY() + (button.getW() + font.getLineHeight()) / 2;
            font.draw(batch, button.getText(), textX, textY);
            batch.end();

            InputHandler.HandleButtonClick(button, () -> {
                if (game != null && button.getAction() != null) {
                    button.getAction().run();
                }
            });
        }

    }

    public void dispose() {
        shapeRenderer.dispose();
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }
}
