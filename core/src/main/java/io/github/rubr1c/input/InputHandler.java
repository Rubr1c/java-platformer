package io.github.rubr1c.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.rubr1c.entites.Player;
import io.github.rubr1c.util.Button;
import io.github.rubr1c.util.Pos;

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

    /**
     * Handle checkpoint teleportation when R key is pressed
     *
     * @param player            The player to teleport
     * @param currentCheckpoint The checkpoint position to teleport to
     * @return true if teleportation was triggered, false otherwise
     */
    public static boolean HandleCheckpointTeleport(Player player, Pos currentCheckpoint) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R) && currentCheckpoint != null) {
            // Create a new Pos object to avoid reference issues
            Pos checkpointPos = new Pos(currentCheckpoint.x, currentCheckpoint.y);
            player.setPos(checkpointPos);
            player.setVelocityY(0); // Reset vertical velocity to prevent falling/jumping after teleport
            return true;
        }
        return false;
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
