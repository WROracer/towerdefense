package de.wroracer.towerdefensegame.scenes;

import de.wroracer.towerdefensegame.Game;
import de.wroracer.towerdefensegame.ui.MyButton;

import java.awt.*;
import java.awt.event.KeyEvent;

import static de.wroracer.towerdefensegame.GameStates.*;

public class Settings  extends GameScene implements SceneMethods {
    public Settings(Game game) {
        super(game);
        initButtons();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 640, 640);
        bMenu.render(g);
    }

    private MyButton bMenu;

    private void initButtons() {
        bMenu = new MyButton(2, 2, 100, 30,"Menu");

    }

    @Override
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x,y)){
            setGameStates(MENU);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(bMenu.getBounds().contains(x, y));
    }

    @Override
    public void mousePressed(int x, int y) {
        bMenu.setMousePressed(bMenu.getBounds().contains(x, y));
    }

    @Override
    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void update() {
    }
}
