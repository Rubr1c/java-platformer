package io.github.rubr1c.world;

import com.badlogic.gdx.graphics.Color;
import io.github.rubr1c.util.Shape;
import io.github.rubr1c.util.Vector4;

import java.util.ArrayList;
import java.util.List;

public class Platform extends Shape {
    private static List<Vector4> BORDERS = new ArrayList<>();

    public Platform(int x, int y, int width, int height) {
        super(x, y, width, height);
        BORDERS.add(new Vector4(x, y, x + width, y + height));
    }

    public Platform(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        BORDERS.add(new Vector4(x, y, x + width, y + height));
    }

    public static List<Vector4> getBorders() {
        return BORDERS;
    }
}
