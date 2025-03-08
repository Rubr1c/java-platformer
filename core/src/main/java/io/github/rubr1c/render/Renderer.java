package io.github.rubr1c.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.rubr1c.Main;
import io.github.rubr1c.entites.Player;
import io.github.rubr1c.fonts.Fonts;
import io.github.rubr1c.input.InputHandler;
import io.github.rubr1c.inventory.Item;
import io.github.rubr1c.screens.GameScreen;
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

    public void render(OrthographicCamera camera,
                       InfinitePlatform ground,
                       List<? extends Shape> obs,
                       Player player) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Render infinite platform
        ground.render(shapeRenderer);

        // Render obstacle
        for (Shape shape: obs) {
            shapeRenderer.setColor(shape.getColor());
            shapeRenderer.rect(shape.getX(), shape.getY(), shape.getZ(), shape.getW());
        }


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if (player.getTexture() != null) {
            batch.draw(player.getTexture(), player.getPos().x, player.getPos().y, player.getWidth(), player.getHeight());
        }
        batch.end();

        if (player.isInvIsOpen()) {
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
            shapeRenderer.setColor(new Color(Color.GRAY.r, Color.GRAY.g, Color.GRAY.b, 0.5f));
            drawSlot(player, slotsPerRow, slotSize, startX, startY);

            // Draw borders
            shapeRenderer.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(new Color(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, 0.7f));
            drawSlot(player, slotsPerRow, slotSize, startX, startY);

            // Disable blending after rendering inventory
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
        shapeRenderer.end();



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
            Optional<Item> slotItem = itemsMap.keySet().stream().filter(item -> item.getSlot() == currentSlot).findFirst();

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

        for (Button button: buttons) {
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
                if (game != null) {
                    game.setScreen(new GameScreen(game));
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
