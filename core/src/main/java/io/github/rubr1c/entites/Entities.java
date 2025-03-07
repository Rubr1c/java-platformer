package io.github.rubr1c.entites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import io.github.rubr1c.util.Pos;

public enum Entities {
    PLAYER(new Pos(0, 0),
        500F,
        9.81F,
        50, 50,
        500,
        new Texture(Gdx.files.internal("images/player.png")));


    private final Entity entity;

    Entities(Pos pos,
             float speed,
             float gravity,
             int width,
             int height,
             float jumpPower,
             Texture texture) {
        this.entity = new Entity(
            pos, speed, gravity, width, height, jumpPower, texture
        );
    }

    public Entity get() {
        return this.entity;
    }
}
