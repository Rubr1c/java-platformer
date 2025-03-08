package io.github.rubr1c.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public enum Items {

    SWORD("Test Sword", 1F, 20);

    private final Item item;

    Items(String name,
          float damage,
          int maxUses) {
        this.item = new Item(name, damage, maxUses);
    }

    public Item get() {
        return item;
    }
}
