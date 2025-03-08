package io.github.rubr1c.input;

import com.badlogic.gdx.Gdx;
import io.github.rubr1c.entites.Entity;
import io.github.rubr1c.util.Pos;
import io.github.rubr1c.util.Vector4;
import io.github.rubr1c.world.Border;
import io.github.rubr1c.world.Platform;

public class Movements {

    // Bounce parameters
    private static final float BOUNCE_FACTOR_X = 100f; // Original bounce multiplier
    private static final float BOUNCE_SPEED_MODIFIER = 0.5f; // Slows down the bounce speed
    private static final float BOUNCE_VELOCITY_Y = 100f; // Small upward velocity on bounce

    /**
     * Check if an entity is currently colliding with any platform border
     *
     * @param object The entity to check
     * @return true if colliding, false otherwise
     */
    public static <T extends Entity> boolean isCollidingWithBorder(T object) {
        for (Vector4 border : Platform.getBorders()) {
            if (object.getPos().x < border.getZ() &&
                    object.getPos().x + object.getWidth() > border.getX() &&
                    object.getPos().y < border.getW() &&
                    object.getPos().y + object.getHeight() > border.getY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the entity is colliding with a specific border
     *
     * @param object The entity to check
     * @param border The border to check against
     * @return true if colliding, false otherwise
     */
    public static <T extends Entity> boolean isCollidingWithSpecificBorder(T object, Vector4 border) {
        return object.getPos().x < border.getZ() &&
                object.getPos().x + object.getWidth() > border.getX() &&
                object.getPos().y < border.getW() &&
                object.getPos().y + object.getHeight() > border.getY();
    }

    /**
     * Check if a Vector4 is from a Border class instance
     *
     * @param borderVector The Vector4 to check
     * @return true if it's from a Border, false if it's from a regular Platform
     */
    public static boolean isBorderVector(Vector4 borderVector) {
        // Check if this Vector4 is from a Border instance by comparing with all Border
        // vectors
        for (Border border : Border.getAllBorders()) {
            // Create a Vector4 representing the border's area
            float borderX = border.getX();
            float borderY = border.getY();
            float borderZ = borderX + border.getZ(); // Right edge (x + width)
            float borderW = borderY + border.getW(); // Top edge (y + height)

            // If the coordinates are very close (allowing for floating point precision
            // issues)
            if (Math.abs(borderVector.getX() - borderX) < 0.1f &&
                    Math.abs(borderVector.getY() - borderY) < 0.1f &&
                    Math.abs(borderVector.getZ() - borderZ) < 0.1f &&
                    Math.abs(borderVector.getW() - borderW) < 0.1f) {
                return true;
            }
        }
        return false;
    }

    /**
     * Resolve collision by moving the entity to a valid position
     * For borders, apply a bounce effect
     *
     * @param object The entity to resolve collision for
     */
    public static <T extends Entity> void resolveCollision(T object) {
        // Store original velocity for bounce calculation
        float originalVelocityX = 0;
        boolean movingRight = object.getPos().x > object.getPrevPos().x;
        boolean movingLeft = object.getPos().x < object.getPrevPos().x;

        if (movingRight) {
            originalVelocityX = object.getSpeed() * BOUNCE_SPEED_MODIFIER; // Apply speed modifier
        } else if (movingLeft) {
            originalVelocityX = -object.getSpeed() * BOUNCE_SPEED_MODIFIER; // Apply speed modifier
        }

        for (Vector4 border : Platform.getBorders()) {
            if (isCollidingWithSpecificBorder(object, border)) {
                // Calculate overlap on each side
                float leftOverlap = border.getZ() - object.getPos().x;
                float rightOverlap = object.getPos().x + object.getWidth() - border.getX();
                float topOverlap = object.getPos().y + object.getHeight() - border.getY();
                float bottomOverlap = border.getW() - object.getPos().y;

                // Find the smallest overlap
                float minOverlap = Math.min(Math.min(leftOverlap, rightOverlap), Math.min(topOverlap, bottomOverlap));

                // Check if this is a border (not a regular platform)
                boolean isBorder = isBorderVector(border);

                // Resolve based on the smallest overlap
                if (minOverlap == leftOverlap) {
                    object.getPos().x = border.getZ();
                    if (isBorder && movingLeft) {
                        // Bounce right with small upward velocity
                        object.getPos().x += originalVelocityX * -BOUNCE_FACTOR_X * Gdx.graphics.getDeltaTime();
                        object.setVelocityY(BOUNCE_VELOCITY_Y);
                    }
                } else if (minOverlap == rightOverlap) {
                    object.getPos().x = border.getX() - object.getWidth();
                    if (isBorder && movingRight) {
                        // Bounce left with small upward velocity
                        object.getPos().x += originalVelocityX * -BOUNCE_FACTOR_X * Gdx.graphics.getDeltaTime();
                        object.setVelocityY(BOUNCE_VELOCITY_Y);
                    }
                } else if (minOverlap == topOverlap) {
                    object.getPos().y = border.getY() - object.getHeight();
                    object.setVelocityY(0);
                } else if (minOverlap == bottomOverlap) {
                    object.getPos().y = border.getW();
                    object.setVelocityY(0);
                    // Immediately set onGround to true when landing on a platform
                    object.setOnGround(true);
                }

                // Only resolve one collision at a time
                break;
            }
        }
    }

    public static <T extends Entity> void moveLeft(T object) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float newX = object.getPos().x - object.getSpeed() * deltaTime;

        // Store previous position
        object.setPrevPos(new Pos(object.getPos().x, object.getPos().y));

        // Apply movement
        object.getPos().x = newX;

        // Check for collision after movement
        if (isCollidingWithBorder(object)) {
            // If collision occurred, resolve it
            resolveCollision(object);
        }
    }

    public static <T extends Entity> void moveRight(T object) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float newX = object.getPos().x + object.getSpeed() * deltaTime;

        // Store previous position
        object.setPrevPos(new Pos(object.getPos().x, object.getPos().y));

        // Apply movement
        object.getPos().x = newX;

        // Check for collision after movement
        if (isCollidingWithBorder(object)) {
            // If collision occurred, resolve it
            resolveCollision(object);
        }
    }

    public static <T extends Entity> void applyGravity(T object) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        object.setVelocityY(object.getVelocityY() - object.getGravity());
        float newY = object.getPos().y + object.getVelocityY() * deltaTime;

        // Store previous position and velocity
        object.setPrevPos(new Pos(object.getPos().x, object.getPos().y));
        float originalVelocityY = object.getVelocityY();

        // Apply movement
        object.getPos().y = newY;

        // Check for collision after movement
        if (isCollidingWithBorder(object)) {
            // If collision occurred, resolve it
            resolveCollision(object);

            // If we were falling (negative velocity) and now we're not moving vertically,
            // we've likely hit the ground
            if (originalVelocityY < 0 && object.getVelocityY() == 0) {
                object.setOnGround(true);
            }
        } else {
            // If not colliding with any platform, the entity is not on ground
            object.setOnGround(false);
        }
    }

    public static <T extends Entity> void jump(T object) {
        if (object.isOnGround()) {
            // Simply apply jump velocity - no additional checks
            object.setVelocityY(object.getJumpPower());
            object.setOnGround(false);
        }
    }
}
