package io.github.rubr1c.util;

public abstract class Shape {

    protected Vector4 area;

    public Shape(
        float x1, float y1,
        float x2, float y2
    ) {
        this.area = new Vector4(x1, y1, x2, y2);
    }

    public float getX() {
        return area.getX();
    }
    public float getY() {
        return area.getY();
    }
    public float getZ() {
        return area.getZ();
    }
    public float getW() {
        return area.getW();
    }
}
