package io.github.rubr1c.util;

import com.badlogic.gdx.graphics.Color;

public class Button extends Shape {

    private String text;

    public Button(String text, float x1, float y1, float x2, float y2) {
        super(x1, y1, x2, y2);
        this.text = text;
    }

    public Button(String text, float x1, float y1, float x2, float y2, Color color) {
        super(x1, y1, x2, y2, color);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
