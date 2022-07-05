package de.wroracer.towerdefensegame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Render {

    private Game game;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();
    private Random random;

    public Render(Game game) {
        this.game = game;
        random = new Random();
    }

    public void render(Graphics g) {
        switch (GameStates.gameStates) {
            case MENU -> game.getMenu().render(g);
            case PLAYING -> game.getPlaying().render(g);
            case SETTINGS -> game.getSettings().render(g);
            case EDIT -> game.getEditing().render(g);
            case GAME_OVER -> game.getGameOver().render(g);
        }
    }

}
