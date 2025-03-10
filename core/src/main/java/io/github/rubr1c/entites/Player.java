package io.github.rubr1c.entites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import io.github.rubr1c.inventory.Inventory;
import io.github.rubr1c.util.Pos;
import io.github.rubr1c.util.TextureUtil;

public class Player extends Entity {
    private boolean invIsOpen;

    public Player(Pos pos,
                  float speed,
                  float gravity,
                  int width,
                  int height,
                  float jumpPower,
                  Texture texture,
                  Inventory inventory) {
        super(pos, speed, gravity, width, height, jumpPower, texture, null);
        invIsOpen = false;
    }

    public Player() {
        this(new Pos(1000, 0),
            500F,
            9.81F,
            50, 50,
            600,
            new Texture(Gdx.files.internal("images/player.png")),
            new Inventory(20));
    }

    public boolean isInvIsOpen() {
        return invIsOpen;
    }

    public void setInvIsOpen(boolean invIsOpen) {
        this.invIsOpen = invIsOpen;
    }
}
