package de.wroracer.towerdefensegame.scenes;

import de.wroracer.towerdefensegame.Game;
import de.wroracer.towerdefensegame.GameStates;
import de.wroracer.towerdefensegame.ui.MyButton;

import java.awt.*;
import java.awt.event.KeyEvent;

import static de.wroracer.towerdefensegame.GameStates.*;

public class GameOver extends GameScene implements SceneMethods{


    private MyButton bMenu,bReplay;

    public GameOver(Game game) {
        super(game);

        initBtns();
    }

    private void initBtns() {
        int w = 150;
        int h = w /3;
        int x = 640 / 2 - w / 2;
        int y = 300;
        int yOffset = 100;
        bMenu = new MyButton(x,y,w,h,"Menu");
        bReplay = new MyButton(x,y+yOffset,w,h,"Replay");
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        //Game over Text
        g.setFont(new Font("LucidaSans",Font.BOLD,50));
        g.setColor(Color.RED);
        g.drawString("Game Over!",160,80);

        //buttons
        //replay
        //menu
        g.setFont(new Font("LucidaSans",Font.BOLD,20));
        bReplay.render(g);
        bMenu.render(g);
    }

    private void replayGame() {
        //reset everything
        resetAll();
        //change state
        setGameStates(PLAYING);
    }
    private void resetAll(){
        getGame().getPlaying().resetEverything();
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x,y)){
            setGameStates(MENU);
            resetAll();
        }else if (bReplay.getBounds().contains(x,y)){
            replayGame();
        }
    }


    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(bMenu.getBounds().contains(x,y));
        bReplay.setMouseOver(bReplay.getBounds().contains(x,y));
    }

    @Override
    public void mousePressed(int x, int y) {
        bMenu.setMousePressed(bMenu.getBounds().contains(x,y));
        bReplay.setMousePressed(bReplay.getBounds().contains(x,y));
    }

    @Override
    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bReplay.resetBooleans();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }
}
