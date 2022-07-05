package de.wroracer.towerdefensegame.ui;

import static de.wroracer.towerdefensegame.GameStates.MENU;
import static de.wroracer.towerdefensegame.GameStates.setGameStates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.wroracer.towerdefensegame.objects.Tile;
import de.wroracer.towerdefensegame.scenes.Editing;
import de.wroracer.towerdefensegame.util.LoadSave;

public class ToolBar extends Bar {

    private Editing editing;

    public ToolBar(int x, int y, int with, int height, Editing editing) {
        super(x, y, with, height);
        this.editing = editing;
        initImgs();
        initButtons();
    }

    /* private ArrayList<MyButton> tileButtons = new ArrayList<>();*/
    private Map<MyButton, ArrayList<Tile>> map = new HashMap<>();
    private MyButton bGrass, bWater, bRoadS, bRoadC, bWaterC, bWaterB, bWaterI;
    private MyButton currentButton;
    private int currentIndex = 0;

    private Tile selectedTile;

    private MyButton bMenu, bSave;

    private BufferedImage pathStart, pathEnd;
    private MyButton bPathStart, bPathEnd;

    private void initImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        pathStart = atlas.getSubimage(7 * 32, 2 * 32, 32, 32);
        pathEnd = atlas.getSubimage(8 * 32, 2 * 32, 32, 32);
    }

    private void initButtons() {
        bMenu = new MyButton(2, 642, 100, 30, "Menu");
        bSave = new MyButton(2, 674, 100, 30, "Save");

        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (w * 1.1f);
        int i = 0;

        bGrass = new MyButton(xStart, yStart, w, h, "Grass", i++);
        bWater = new MyButton(xStart + xOffset, yStart, w, h, "Water", i++);

        initMapButton(bRoadS, editing.getGame().getTileManager().getRoadsS(), xStart, yStart, xOffset, w, h, i++);
        initMapButton(bRoadC, editing.getGame().getTileManager().getRoadsC(), xStart, yStart, xOffset, w, h, i++);
        initMapButton(bWaterC, editing.getGame().getTileManager().getCorners(), xStart, yStart, xOffset, w, h, i++);
        initMapButton(bWaterB, editing.getGame().getTileManager().getBeaches(), xStart, yStart, xOffset, w, h, i++);
        initMapButton(bWaterI, editing.getGame().getTileManager().getIslands(), xStart, yStart, xOffset, w, h, i++);

        bPathStart = new MyButton(xStart, yStart + xOffset, w, h, "Pathstart", i++);
        bPathEnd = new MyButton(xStart + xOffset, yStart + xOffset, w, h, "Pathend", i++);
    }

    private void initMapButton(MyButton b, ArrayList<Tile> list, int x, int y, int xOff, int w, int h, int id) {
        b = new MyButton(x + xOff * id, y, w, h, "", id);
        map.put(b, list);
    }

    public void rotateSprite() {
        currentIndex++;
        if (currentIndex >= map.get(currentButton).size()) {
            currentIndex = 0;
        }
        selectedTile = map.get(currentButton).get(currentIndex);
        editing.setSelectedTile(selectedTile);
    }

    public void render(Graphics g) {
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, with, height);
        bMenu.render(g);
        bSave.render(g);

        drawPathButton(g, bPathStart, pathStart);
        drawPathButton(g, bPathEnd, pathEnd);

        drawSelectedTile(g);

        drawNormalButton(g, bGrass);
        drawNormalButton(g, bWater);
        drawMapButtons(g);
    }

    private void drawPathButton(Graphics g, MyButton btn, BufferedImage img) {
        g.drawImage(img, btn.getX(), btn.getY(), btn.getHeight(), btn.getWidth(), null);

        renderButtonFeedback(g, btn);
    }

    private void drawNormalButton(Graphics g, MyButton tb) {
        //Sprites
        g.drawImage(getButtImage(tb.getId()), tb.getX(), tb.getY(), tb.getHeight(), tb.getWidth(), null);

        renderButtonFeedback(g, tb);
    }

    private void drawMapButtons(Graphics g) {
        for (Map.Entry<MyButton, ArrayList<Tile>> entry : map.entrySet()) {
            MyButton tb = entry.getKey();
            BufferedImage img = entry.getValue().get(0).getSprite();

            //Sprites
            g.drawImage(img, tb.getX(), tb.getY(), tb.getHeight(), tb.getWidth(), null);
            renderButtonFeedback(g, tb);
        }
    }

    private void drawSelectedTile(Graphics g) {
        if (selectedTile != null) {
            g.drawImage(selectedTile.getSprite(), 550, 650, 50, 50, null);
            g.setColor(Color.black);
            g.drawRect(550, 650, 50, 50);
        }
    }

    private BufferedImage getButtImage(int id) {
        return editing.getGame().getTileManager().getSprite(id);
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            setGameStates(MENU);
        } else if (bSave.getBounds().contains(x, y)) {
            saveLevel();
        } else if (bGrass.getBounds().contains(x, y)) {
            selectedTile = editing.getGame().getTileManager().getTile(bGrass.getId());
            editing.setSelectedTile(selectedTile);
        } else if (bWater.getBounds().contains(x, y)) {
            selectedTile = editing.getGame().getTileManager().getTile(bWater.getId());
            editing.setSelectedTile(selectedTile);
        } else if (bPathStart.getBounds().contains(x, y)) {
            selectedTile = new Tile(pathStart, -1, -1);
            editing.setSelectedTile(selectedTile);
        } else if (bPathEnd.getBounds().contains(x, y)) {
            selectedTile = new Tile(pathEnd, -2, -2);
            editing.setSelectedTile(selectedTile);
        } else {
            for (MyButton b : map.keySet()) {
                if (b.getBounds().contains(x, y)) {
                    selectedTile = map.get(b).get(0);
                    editing.setSelectedTile(selectedTile);
                    currentButton = b;
                    currentIndex = 0;
                }
            }
        }
    }

    private void saveLevel() {
        editing.saveLevel();
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(bMenu.getBounds().contains(x, y));
        bSave.setMouseOver(bSave.getBounds().contains(x, y));
        bWater.setMouseOver(bWater.getBounds().contains(x, y));
        bGrass.setMouseOver(bGrass.getBounds().contains(x, y));
        bPathEnd.setMouseOver(bPathEnd.getBounds().contains(x, y));
        bPathStart.setMouseOver(bPathStart.getBounds().contains(x, y));
        for (MyButton b : map.keySet()) {
            b.setMouseOver(b.getBounds().contains(x, y));
        }

    }

    public void mousePressed(int x, int y) {
        bMenu.setMousePressed(bMenu.getBounds().contains(x, y));
        bSave.setMousePressed(bSave.getBounds().contains(x, y));
        bWater.setMousePressed(bWater.getBounds().contains(x, y));
        bGrass.setMousePressed(bGrass.getBounds().contains(x, y));
        bPathEnd.setMousePressed(bPathEnd.getBounds().contains(x, y));
        bPathStart.setMousePressed(bPathStart.getBounds().contains(x, y));
        for (MyButton b : map.keySet()) {
            b.setMousePressed(b.getBounds().contains(x, y));
        }
    }

    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bSave.resetBooleans();
        bWater.resetBooleans();
        bGrass.resetBooleans();
        bPathEnd.resetBooleans();
        bPathStart.resetBooleans();
        for (MyButton b : map.keySet()) {
            b.resetBooleans();
        }
    }

    public BufferedImage getPathEnd() {
        return pathEnd;
    }

    public BufferedImage getPathStart() {
        return pathStart;
    }
}
