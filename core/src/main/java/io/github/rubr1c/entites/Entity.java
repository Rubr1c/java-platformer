package io.github.rubr1c.entites;

import com.badlogic.gdx.graphics.Texture;
import io.github.rubr1c.inventory.Inventory;
import io.github.rubr1c.inventory.Item;
import io.github.rubr1c.util.Pos;

public class Entity {

    protected Pos pos;
    protected Pos prevPos; // Previous position for movement direction detection
    protected float speed;
    protected float gravity;
    protected int width;
    protected int height;
    protected float jumpPower;
    protected boolean onGround;
    protected float velocityY;
    protected Inventory inventory;

    protected Texture texture;

    public Entity(Pos pos,
            float speed,
            float gravity,
            int width,
            int height,
            float jumpPower,
            Texture texture,
            Inventory inventory) {
        this.pos = pos;
        this.prevPos = new Pos(pos.x, pos.y); // Initialize prevPos to current pos
        this.speed = speed;
        this.gravity = gravity;
        this.width = width;
        this.height = height;
        this.jumpPower = jumpPower;
        this.onGround = false;
        this.velocityY = 0;
        this.texture = texture;
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void addToInventory(Item item) {
        this.inventory.addItem(item);
    }

    public void addToInventory(Item item, int count) {
        this.inventory.addItem(item, count);
    }

    public void setPos(Pos pos) {
        this.prevPos = this.pos; // Store current position as previous
        this.pos = pos;
    }

    public void setPos(Integer x, Integer y) {
        this.prevPos = this.pos; // Store current position as previous
        this.pos = new Pos(x, y);
    }

    public Pos getPos() {
        return pos;
    }

    public Pos getPrevPos() {
        return prevPos;
    }

    public void setPrevPos(Pos prevPos) {
        this.prevPos = prevPos;
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

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
