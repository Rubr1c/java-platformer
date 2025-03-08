package io.github.rubr1c.registries;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import io.github.rubr1c.inventory.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class ItemRegistry {
    private static final Map<String, Item> ITEMS = new HashMap<>();

    public static void register(String id, Item item) {
        ITEMS.put(id, item);
    }

    public static Item getItem(String id) {
        return ITEMS.get(id);
    }

    public static void initItemTextures() {
        for (String item: ITEMS.keySet()) {
            try {
                ITEMS.get(item).setTexture(new Texture(Gdx.files.internal(String.format("items/%S.png", item))));
            } catch (Exception e) {
                Pixmap pixmap = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
                pixmap.setColor(Color.WHITE);
                pixmap.fill();
                Texture blankTexture = new Texture(pixmap);
                pixmap.dispose();
                ITEMS.get(item).setTexture(blankTexture);
            }
        }
    }
}
