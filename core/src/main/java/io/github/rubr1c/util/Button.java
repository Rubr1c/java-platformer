package io.github.rubr1c.util;

import com.badlogic.gdx.graphics.Color;

import java.util.function.Function;

public class Button extends Shape {

    private String text;
    private Runnable action;

    public Button(String text,
                  float x1,
                  float y1,
                  float x2,
                  float y2,
                  Runnable action) {
        super(x1, y1, x2, y2);
        this.text = text;
        this.action = action;
    }

    public Button(String text,
                  float x1,
                  float y1,
                  float x2,
                  float y2,
                  Runnable action,
                  Color color) {
        super(x1, y1, x2, y2, color);
        this.text = text;
        this.action = action;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Runnable getAction() {
        return action;
    }

    public void setAction(Runnable action) {
        this.action = action;
    }
}
