package io.github.rubr1c.inventory;

import com.badlogic.gdx.graphics.Texture;

public class Item {
    private String name;
    private float damage;
    private int uses;
    private int maxUses;
    private Texture texture;
    private int slot;

    public Item(String name,
                float damage,
                int maxUses) {
        this.name = name;
        this.damage = damage;
        this.uses = 0;
        this.maxUses = maxUses;
        this.slot = -1;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    public int getMaxUses() {
        return maxUses;
    }

    public void setMaxUses(int maxUses) {
        this.maxUses = maxUses;
    }
}
