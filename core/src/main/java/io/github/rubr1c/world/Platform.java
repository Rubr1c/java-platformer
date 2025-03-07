package io.github.rubr1c.world;

import io.github.rubr1c.util.Pos;
import io.github.rubr1c.util.Shape;
import io.github.rubr1c.util.Vector4;

import java.util.ArrayList;
import java.util.List;

public class Platform extends Shape {
    private static List<Vector4> borders = new ArrayList<>();

    public Platform(int x, int y, int width, int height) {
        super(x, y, width, height);
        borders.add(new Vector4(x, y, x + width, y + height));
    }

    public static List<Vector4> getBorders() {
        return borders;
    }
}
