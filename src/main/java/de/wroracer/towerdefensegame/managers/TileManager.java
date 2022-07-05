package de.wroracer.towerdefensegame.managers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

import de.wroracer.towerdefensegame.objects.Tile;
import de.wroracer.towerdefensegame.util.Constants.Tiles;
import de.wroracer.towerdefensegame.util.ImgFix;
import de.wroracer.towerdefensegame.util.LoadSave;

public class TileManager {

    public Tile GRASS, WATER, ROAD_LR, ROAD_TB, ROAD_B_TO_R, ROAD_L_TO_B, ROAD_L_TO_T, ROAD_T_TO_R, BL_WATER_CORNER,
            TL_WATER_CORNER, TR_WATER_CORNER, BR_WATER_CORNER, T_WATER, R_WATER, B_WATER, L_WATER, TL_ISLE, TR_ISLE,
            BR_ISLE, BL_ISLE;
    public BufferedImage atlas;
    public ArrayList<Tile> tiles = new ArrayList<>();

    public ArrayList<Tile> roadsS = new ArrayList<>();
    public ArrayList<Tile> roadsC = new ArrayList<>();
    public ArrayList<Tile> corners = new ArrayList<>();
    public ArrayList<Tile> beaches = new ArrayList<>();
    public ArrayList<Tile> islands = new ArrayList<>();

    public TileManager() {
        loadAtlas();
        createTiles();
    }

    private void createTiles() {
        int id = 0;
        tiles.add(GRASS = new Tile(getSprite(9, 0), id++, Tiles.GRASS));
        tiles.add(WATER = new Tile(getAniSprites(0, 0), id++, Tiles.WATER));

        roadsS.add(ROAD_LR = new Tile(getSprite(8, 0), id++, Tiles.ROAD));
        roadsS.add(ROAD_TB = new Tile(ImgFix.rotate(getSprite(8, 0), 90), id++, Tiles.ROAD));

        roadsC.add(ROAD_B_TO_R = new Tile(getSprite(7, 0), id++, Tiles.ROAD));
        roadsC.add(ROAD_L_TO_B = new Tile(ImgFix.rotate(getSprite(7, 0), 90), id++, Tiles.ROAD));
        roadsC.add(ROAD_L_TO_T = new Tile(ImgFix.rotate(getSprite(7, 0), 180), id++, Tiles.ROAD));
        roadsC.add(ROAD_T_TO_R = new Tile(ImgFix.rotate(getSprite(7, 0), 270), id++, Tiles.ROAD));

        corners.add(BL_WATER_CORNER = new Tile(ImgFix.buildRotateimg(getAniSprites(0, 0), getSprite(5, 0), 0), id++,
                Tiles.WATER));
        corners.add(TL_WATER_CORNER = new Tile(ImgFix.buildRotateimg(getAniSprites(0, 0), getSprite(5, 0), 90), id++,
                Tiles.WATER));
        corners.add(TR_WATER_CORNER = new Tile(ImgFix.buildRotateimg(getAniSprites(0, 0), getSprite(5, 0), 180), id++,
                Tiles.WATER));
        corners.add(BR_WATER_CORNER = new Tile(ImgFix.buildRotateimg(getAniSprites(0, 0), getSprite(5, 0), 270), id++,
                Tiles.WATER));

        beaches.add(
                T_WATER = new Tile(ImgFix.buildRotateimg(getAniSprites(0, 0), getSprite(6, 0), 0), id++, Tiles.WATER));
        beaches.add(
                R_WATER = new Tile(ImgFix.buildRotateimg(getAniSprites(0, 0), getSprite(6, 0), 90), id++, Tiles.WATER));
        beaches.add(B_WATER = new Tile(ImgFix.buildRotateimg(getAniSprites(0, 0), getSprite(6, 0), 180), id++,
                Tiles.WATER));
        beaches.add(L_WATER = new Tile(ImgFix.buildRotateimg(getAniSprites(0, 0), getSprite(6, 0), 270), id++,
                Tiles.WATER));

        islands.add(
                TL_ISLE = new Tile(ImgFix.buildRotateimg(getAniSprites(0, 0), getSprite(4, 0), 0), id++, Tiles.WATER));
        islands.add(
                TR_ISLE = new Tile(ImgFix.buildRotateimg(getAniSprites(0, 0), getSprite(4, 0), 90), id++, Tiles.WATER));
        islands.add(BR_ISLE = new Tile(ImgFix.buildRotateimg(getAniSprites(0, 0), getSprite(4, 0), 180), id++,
                Tiles.WATER));
        islands.add(BL_ISLE = new Tile(ImgFix.buildRotateimg(getAniSprites(0, 0), getSprite(4, 0), 270), id++,
                Tiles.WATER));

        tiles.addAll(roadsC);
        tiles.addAll(roadsS);
        tiles.addAll(corners);
        tiles.addAll(beaches);
        tiles.addAll(islands);

        tiles.sort(new Comparator<Tile>() {
            @Override
            public int compare(Tile o1, Tile o2) {
                return o1.getId() - o2.getId();
            }
        });
    }

    private BufferedImage[] getImgs(int fistX, int firstY, int secondX, int secondY) {
        return new BufferedImage[] { getSprite(fistX, firstY), getSprite(secondX, secondY) };
    }

    private void loadAtlas() {
        atlas = LoadSave.getSpriteAtlas();
    }

    public Tile getTile(int id) {
        return tiles.get(id);
    }

    private BufferedImage getSprite(int xCord, int yCord) {
        return atlas.getSubimage(xCord * 32, yCord * 32, 32, 32);
    }

    public BufferedImage getSprite(int id) {
        return tiles.get(id).getSprite();
    }

    public BufferedImage getAniSprite(int id, int animationID) {
        return tiles.get(id).getSprite(animationID);
    }

    public BufferedImage[] getAniSprites(int xCord, int yCord) {
        BufferedImage[] arr = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            arr[i] = getSprite(xCord + i, yCord);
        }
        return arr;
    }

    public ArrayList<Tile> getRoadsS() {
        return roadsS;
    }

    public ArrayList<Tile> getRoadsC() {
        return roadsC;
    }

    public ArrayList<Tile> getCorners() {
        return corners;
    }

    public ArrayList<Tile> getBeaches() {
        return beaches;
    }

    public ArrayList<Tile> getIslands() {
        return islands;
    }

    public boolean isAnimationSprite(int idSprite) {
        return tiles.get(idSprite).isAnimation();
    }
}
