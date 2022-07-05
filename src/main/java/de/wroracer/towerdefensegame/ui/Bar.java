package de.wroracer.towerdefensegame.ui;

import java.awt.Color;
import java.awt.Graphics;

public class Bar {

    protected int x, y, with, height;

    public Bar(int x, int y, int with, int height) {
        this.x = x;
        this.y = y;
        this.with = with;
        this.height = height;
    }

    protected void renderButtonFeedback(Graphics g, MyButton tb) {
        //Border
        g.setColor(Color.BLACK);

        //Mouse over
        if (tb.isMouseOver()) {
            g.setColor(Color.WHITE);
        }
        g.drawRect(tb.getX(), tb.getY(), tb.getWidth(), tb.getHeight());

        //Mouse Pressed
        if (tb.isMousePressed()) {
            g.drawRect(tb.getX() + 1, tb.getY() + 1, tb.getWidth() - 2, tb.getHeight() - 2);
            g.drawRect(tb.getX() + 2, tb.getY() + 2, tb.getWidth() - 4, tb.getHeight() - 4);
        }
    }
}
