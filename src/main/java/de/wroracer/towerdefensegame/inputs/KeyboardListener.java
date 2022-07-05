package de.wroracer.towerdefensegame.inputs;

import de.wroracer.towerdefensegame.Game;
import de.wroracer.towerdefensegame.GameStates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

import static de.wroracer.towerdefensegame.GameStates.*;

public class KeyboardListener implements KeyListener {

    private Game game;

    public KeyboardListener(Game game){
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (gameStates){
            case EDIT -> game.getEditing().keyPressed(e);
            case PLAYING -> game.getPlaying().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
