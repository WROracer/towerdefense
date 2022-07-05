package de.wroracer.towerdefensegame.scenes;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import de.wroracer.towerdefensegame.Game;

public class GameScene {
    private Game game;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();

    private Random random;
    protected int tick;
    protected int animatinIndex;
    protected int ANIMATION_SPEED = 25;

    public GameScene(Game game) {
        this.game = game;
        random = new Random();
    }

    public Random getRandom() {
        return random;
    }

    public Game getGame() {
        return game;
    }

    public ArrayList<BufferedImage> getSprites() {
        return sprites;
    }

    protected void updateTick() {
        tick++;
        if (tick >= ANIMATION_SPEED) {
            tick = 0;
            animatinIndex++;
            if (animatinIndex >= 4) {
                animatinIndex = 0;
            }
        }
    }

    protected BufferedImage getSprite(int spriteID, int animationIndex) {
        return getGame().getTileManager().getAniSprite(spriteID, animationIndex);
    }

    protected BufferedImage getSprite(int spriteID) {
        return getGame().getTileManager().getSprite(spriteID);
    }

    protected boolean isAnimation(int idSprite) {
        return getGame().getTileManager().isAnimationSprite(idSprite);
    }
}
