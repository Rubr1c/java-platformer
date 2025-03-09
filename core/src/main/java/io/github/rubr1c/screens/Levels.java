package io.github.rubr1c.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import io.github.rubr1c.entites.Player;
import io.github.rubr1c.inventory.Items;
import io.github.rubr1c.registries.ItemRegistry;
import io.github.rubr1c.util.Pos;
import io.github.rubr1c.util.TextureUtil;
import io.github.rubr1c.world.Border;
import io.github.rubr1c.world.Platform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Levels {
    LEVEL_1(1) {
        @Override
        public List<Platform> createObstacles() {
            List<Platform> obstacles = new ArrayList<>();
            obstacles.add(new Platform(500, 400, 60, 10));
            obstacles.add(new Platform(700, 450, 60, 10));
            obstacles.add(new Platform(800, 475, 60, 10));
            obstacles.add(new Platform(1000, 550, 60, 10));
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
        public Pos getPlayerStartPos() {
            return new Pos(200, 400);
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
            borders.add(new Border(-200, -100, 300, 2000));
            // Right border - positioned after the platform
            borders.add(new Border(5000, -100, 300, 2000));
            return borders;
        }

        @Override
        public Texture getBackground() {
            return TextureUtil.loadFrom("images/bg.png");
        }

        @Override
        public Map<Pos, Boolean> getCheckpoints() {
            Map<Pos, Boolean> checkpoints = new HashMap<>();
            checkpoints.put(new Pos(1025, 580), false);
            return checkpoints;
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

    public abstract Map<Pos, Boolean> getCheckpoints();

    public abstract Pos getPlayerStartPos();
}
