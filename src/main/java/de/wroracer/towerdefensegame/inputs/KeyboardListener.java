package de.wroracer.towerdefensegame.inputs;

import static de.wroracer.towerdefensegame.GameStates.gameStates;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import de.wroracer.towerdefensegame.Game;

public class KeyboardListener implements KeyListener {

    private Game game;

    public KeyboardListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (gameStates) {
            case EDIT -> game.getEditing().keyPressed(e);
            case PLAYING -> game.getPlaying().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
