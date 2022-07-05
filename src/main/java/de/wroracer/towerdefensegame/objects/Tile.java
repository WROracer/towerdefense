package de.wroracer.towerdefensegame.objects;

import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage[] sprites;
    private int id;
    private int tileType;

    public Tile(BufferedImage sprite, int id, int tileType) {
        this.sprites = new BufferedImage[] { sprite };
        this.id = id;
        this.tileType = tileType;
    }

    public Tile(BufferedImage[] sprites, int id, int tileType) {
        this.sprites = sprites;
        this.tileType = tileType;
        this.id = id;
    }

    public BufferedImage getSprite() {
        return sprites[0];
    }

    public BufferedImage getSprite(int animationIndex) {
        return sprites[animationIndex];
    }

    public int getId() {
        return id;
    }

    public boolean isAnimation() {
        return sprites.length > 1;
    }

    public int getTileType() {
        return tileType;
    }
}
