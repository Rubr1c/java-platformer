package io.github.rubr1c.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.rubr1c.entites.Entity;
import io.github.rubr1c.movement.Movements;

public class InputHandler {

    public static void HandlePlayer(Entity player) {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            Movements.moveLeft(player);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            Movements.moveRight(player);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Movements.jump(player);
        }

        Movements.applyGravity(player);
    }
}
