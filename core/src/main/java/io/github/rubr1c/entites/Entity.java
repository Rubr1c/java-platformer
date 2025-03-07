package io.github.rubr1c.entites;

import io.github.rubr1c.util.Pos;

public class Entity {

    protected Pos pos;
    protected float speed;
    protected float gravity;
    protected int width;
    protected int height;
    protected float jumpPower;
    protected boolean onGround;
    protected float velocityY;

    public Entity(Pos pos,
                  float speed,
                  float gravity,
                  int width,
                  int height,
                  float jumpPower) {
        this.pos = pos;
        this.speed = speed;
        this.gravity = gravity;
        this.width = width;
        this.height = height;
        this.jumpPower = jumpPower;
        this.onGround = false;
        this.velocityY = 0;
    }

    public void setPos(Pos pos) {
        this.pos = pos;
    }

    public void setPos(Integer x, Integer y) {
        this.pos = new Pos(x, y);
    }

    public Pos getPos() {
        return pos;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getJumpPower() {
        return jumpPower;
    }

    public void setJumpPower(float jumpPower) {
        this.jumpPower = jumpPower;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }
}
