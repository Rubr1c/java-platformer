package io.github.rubr1c.screens;

import com.badlogic.gdx.graphics.Color;
import io.github.rubr1c.entites.Player;
import io.github.rubr1c.inventory.Items;
import io.github.rubr1c.registries.ItemRegistry;
import io.github.rubr1c.world.Platform;

import java.util.ArrayList;
import java.util.List;

public enum Levels {
    LEVEL_1(1) {
        @Override
        public List<Platform> createObstacles() {
            List<Platform> obstacles = new ArrayList<>();
            obstacles.add(new Platform(500, 350, 50, 10, Color.RED));
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
            player.addToInventory(Items.SWORD.get(), 2);
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
}
