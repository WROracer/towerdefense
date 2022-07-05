package de.wroracer.towerdefensegame.scenes;

import de.wroracer.towerdefensegame.Game;
import de.wroracer.towerdefensegame.ui.MyButton;

import static de.wroracer.towerdefensegame.GameStates.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Menu extends GameScene implements SceneMethods {

    private MyButton btnPlay,btnEdit, btnSettings, btnExit;

    public Menu(Game game) {
        super(game);
        initBtns();
    }

    private void initBtns() {
        int w = 150;
        int h = w /2;
        int x = 640 / 2 - w / 2;
        int y = 150;
        int yOffset = 100;
       btnPlay = new MyButton(x,y,w,h,"Play");
       btnEdit = new MyButton(x,y+yOffset,w,h,"Edit");
       btnSettings = new MyButton(x,y+yOffset*2,w,h,"Settings");
       btnExit = new MyButton(x,y+yOffset*3,w,h,"Quit");
    }

    @Override
    public void render(Graphics g) {
        renderButtons(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (btnPlay.getBounds().contains(x,y)){
            setGameStates(PLAYING);
        }else if(btnSettings.getBounds().contains(x,y)){
            setGameStates(SETTINGS);
        }else if (btnExit.getBounds().contains(x,y)){
            System.exit(0);
        }else if (btnEdit.getBounds().contains(x,y)){
            setGameStates(EDIT);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        btnPlay.setMouseOver(btnPlay.getBounds().contains(x, y));
        btnSettings.setMouseOver(btnSettings.getBounds().contains(x,y));
        btnExit.setMouseOver(btnExit.getBounds().contains(x,y));
        btnEdit.setMouseOver(btnEdit.getBounds().contains(x,y));
    }

    @Override
    public void mousePressed(int x, int y) {
        btnPlay.setMousePressed(btnPlay.getBounds().contains(x, y));
        btnSettings.setMousePressed(btnSettings.getBounds().contains(x,y));
        btnExit.setMousePressed(btnExit.getBounds().contains(x,y));
        btnEdit.setMousePressed(btnEdit.getBounds().contains(x,y));
    }

    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    private void resetButtons() {
        btnPlay.resetBooleans();
        btnExit.resetBooleans();
        btnSettings.resetBooleans();
        btnEdit.resetBooleans();
    }

    private void renderButtons(Graphics g) {
        btnPlay.render(g);
        btnEdit.render(g);
        btnSettings.render(g);
        btnExit.render(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void update() {
    }
}
