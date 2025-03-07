package io.github.rubr1c.movement;

import com.badlogic.gdx.Gdx;
import io.github.rubr1c.entites.Entity;
import io.github.rubr1c.util.Pos;
import io.github.rubr1c.util.Vector4;
import io.github.rubr1c.world.Platform;

public class Movements {

    public static <T extends Entity> void moveLeft(T object) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        object.getPos().x -= object.getSpeed() * deltaTime;
    }

    public static <T extends Entity> void moveRight(T object) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        object.getPos().x += object.getSpeed() * deltaTime;
    }

    public static <T extends Entity> void applyGravity(T object) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        object.setVelocityY(object.getVelocityY() - object.getGravity());
        float newY = object.getPos().y + object.getVelocityY() * deltaTime;

        boolean onGround = false;
        for (Vector4 border : Platform.getBORDERS()) {
            float futureY = newY;
            if (object.getVelocityY() < 0 && border.isCollidingBottom(new Pos(object.getPos().x, futureY), object.getWidth(), object.getHeight())) {
                newY = border.getW();
                object.setVelocityY(0);
                onGround = true;
                break;
            } else if (object.getVelocityY() > 0 && border.isCollidingTop(new Pos(object.getPos().x, futureY), object.getWidth(), object.getHeight())) {
                newY = border.getY() - object.getHeight();
                object.setVelocityY(0);
                break;
            }
        }

        object.getPos().y = newY;

        if (object.getPos().y <= 0) {
            object.getPos().y = 0;
            object.setVelocityY(0);
            onGround = true;
        }

        object.setOnGround(onGround);
    }

    public static <T extends Entity> void jump(T object) {
        if (object.isOnGround()) {
            for (Vector4 border : Platform.getBORDERS()) {
                if (border.isCollidingTop(object.getPos(), object.getWidth(), object.getHeight())) {
                    return;
                }
            }
            object.setVelocityY(object.getJumpPower());
        }
    }
}
