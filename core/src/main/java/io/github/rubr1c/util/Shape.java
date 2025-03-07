package io.github.rubr1c.util;

import com.badlogic.gdx.graphics.Color;

public abstract class Shape {

    protected Vector4 area;
    protected Color color;

    public Shape(
        float x1, float y1,
        float x2, float y2
    ) {
        this.area = new Vector4(x1, y1, x2, y2);
        this.color = Color.WHITE;
    }

    public Shape(
        float x1, float y1,
        float x2, float y2,
        Color color
    ) {
        this.area = new Vector4(x1, y1, x2, y2);
        this.color = color;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
