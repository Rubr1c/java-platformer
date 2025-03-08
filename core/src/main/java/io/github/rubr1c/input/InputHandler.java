package io.github.rubr1c.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.rubr1c.entites.Player;
import io.github.rubr1c.util.Button;

public class InputHandler {

    public static void HandlePlayer(Player player) {
        Movements.applyGravity(player);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            Movements.moveLeft(player);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            Movements.moveRight(player);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Movements.jump(player);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            player.setInvIsOpen(!player.isInvIsOpen());
        }
    }

    public static void HandleButtonClick(Button button, Runnable action) {
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Flip Y coordinate

            if (touchX >= button.getX() && touchX <= button.getX() + button.getZ() &&
                touchY >= button.getY() && touchY <= button.getY() + button.getW()) {
                action.run();
            }
        }
    }
}
