package io.github.rubr1c.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import io.github.rubr1c.entites.Player;
import io.github.rubr1c.inventory.Items;
import io.github.rubr1c.registries.ItemRegistry;
import io.github.rubr1c.world.Border;
import io.github.rubr1c.world.Platform;

import java.util.ArrayList;
import java.util.List;

public enum Levels {
    LEVEL_1(1) {
        @Override
        public List<Platform> createObstacles() {
            List<Platform> obstacles = new ArrayList<>();
            obstacles.add(new Platform(500, 300, 50, 10, Color.RED));
            return obstacles;
        }

        @Override
        public int getPlayerStartX() {
            return 200;
        }

        @Override
        public int getPlayerStartY() {
            return 400;
        }

        @Override
        public void initializeItems(Player player) {
            ItemRegistry.register("d_sword", Items.SWORD.get());
            // player.addToInventory(Items.SWORD.get(), 2);
        }

        @Override
        public List<Border> createBorders() {
            List<Border> borders = new ArrayList<>();
            // Left border - positioned just before the first platform
            borders.add(new Border(0, -100, 100, 2000, Color.GRAY));
            // Right border - positioned after the platform
            borders.add(new Border(2000, -100, 100, 2000, Color.GRAY));
            return borders;
        }

        @Override
        public Texture getBackground() {
            return new Texture(Gdx.files.internal("images/bg.png"));
        }
    };

    private final int levelNumber;

    Levels(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public abstract List<Platform> createObstacles();

    public abstract int getPlayerStartX();

    public abstract int getPlayerStartY();

    public abstract void initializeItems(Player player);

    public abstract List<Border> createBorders();

    public abstract Texture getBackground();
}
