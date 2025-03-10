package io.github.rubr1c.util;

public class Vector4 {
    private float x, y, z, w;

    public Vector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4(Pos p1, Pos p2) {
        this.x = p1.x;
        this.y = p1.y;
        this.z = p2.x;
        this.w = p2.y;
    }

    public float getY() {
        return y;
    }

    public float getW() {
        return w;
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }
}
