package io.github.rubr1c.entites;


import io.github.rubr1c.util.Pos;

public enum Entities {
    PLAYER(new Pos(0, 0), 1000F, 9.81F, 20, 20, 500);


    private final Entity entity;

    Entities(Pos pos,
            float speed,
            float gravity,
            int width,
            int height,
            float jumpPower) {
        this.entity = new Entity(
            pos, speed, gravity, width, height, jumpPower
        );
    }

    public Entity get() {
        return this.entity;
    }
}
