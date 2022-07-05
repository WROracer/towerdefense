package de.wroracer.towerdefensegame.ui;


import java.awt.*;

public class MyButton {
    private int x, y, width, height;
    private String text;
    private boolean mouseOver;

    private boolean mousePressed;

    private Rectangle bounds;
    private int id;
    public MyButton(int x,int y,int width,int height,String text){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.id = -1;
        initBounds();
    }

    public MyButton(int x,int y,int width,int height,String text,int id){
        this(x,y,width,height,text);
        this.id = id;
    }

    public void resetBooleans(){
        this.mouseOver = false;
        this.mousePressed = false;
    }

    private void initBounds(){
        bounds = new Rectangle(x,y,width,height);
    }

    public void render(Graphics g){
        //Body
        renderBody(g);

        //Border
        renderBorder(g);

        //Text
        renderText(g);
    }

    private void renderText(Graphics g) {
        int w = g.getFontMetrics().stringWidth(text);
        int h = g.getFontMetrics().getHeight();
        g.drawString(text, x - w / 2 + width / 2, y + h / 2 + height / 2);

    }

    private void renderBorder(Graphics g) {
        if (mousePressed){
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
            g.drawRect(x+1, y+1, width-2, height-2);
            g.drawRect(x+2, y+2, width-4, height-4);
        }else {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
        }

    }

    private void renderBody(Graphics g) {
        if (mouseOver)
            g.setColor(Color.GRAY);
        else
            g.setColor(Color.WHITE);

        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void setMouseOver(boolean mouseOver){
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public int getId() {
        return id;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setText(String text) {
        this.text = text;
    }
}
