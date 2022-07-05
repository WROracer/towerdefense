package de.wroracer.towerdefensegame.scenes;

import de.wroracer.towerdefensegame.Game;
import de.wroracer.towerdefensegame.objects.PathPoint;
import de.wroracer.towerdefensegame.objects.Tile;
import de.wroracer.towerdefensegame.ui.ToolBar;
import de.wroracer.towerdefensegame.util.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static de.wroracer.towerdefensegame.util.Constants.Tiles.ROAD;

public class Editing extends GameScene implements SceneMethods{

    private int[][] level;
    private Tile selectedTile;
    private boolean drawSelect = false;

    private int mouseX,mouseY;

    private ToolBar toolBar;

    private PathPoint start,end;


    public Editing(Game game) {
        super(game);
        createDefaultLevel();
        toolBar = new ToolBar(0,640,640,160,this);
        loadDefaultLevel();
    }

    private void createDefaultLevel() {
        int[] arr = new int[400];
        for (int i = 0; i < arr.length;i++){
            arr[i] = 0;
        }
        LoadSave.createLevel(arr);

    }

    private void loadDefaultLevel() {
        level = LoadSave.getLevelData();
        ArrayList<PathPoint> points = LoadSave.getLevelPathPoints();
        start = points.get(0);
        end = points.get(1);
    }


    private void changeTile(int x, int y) {
        if (selectedTile != null) {
            int tileX = x / 32;
            int tileY = y / 32;
            if (selectedTile.getId() >=0) {
                if (level[tileY][tileX] != selectedTile.getId()) {
                    level[tileY][tileX] = selectedTile.getId();
                }
            }else {
                int id = level[tileY][tileX];
                if (getGame().getTileManager().getTile(id).getTileType() == ROAD){
                    if (selectedTile.getId() == -1){
                        start = new PathPoint(tileX,tileY);
                    }else {
                        end = new PathPoint(tileX,tileY);
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {

       renderLevel(g);
        toolBar.render(g);
        drawSelectedTile(g);
        drawPathPoints(g);
    }

    private void drawPathPoints(Graphics g) {
        if (start != null){
            g.drawImage(toolBar.getPathStart(),start.getxCord()*32,start.getyCord()*32,null);
        }
        if (end != null){
            g.drawImage(toolBar.getPathEnd(),end.getxCord()*32,end.getyCord()*32,null);
        }
    }

    @Override
    public void update() {
        updateTick();
    }


    private void renderLevel(Graphics g){
        for (int y = 0; y < level.length; y++) {
            for (int x = 0; x < level[y].length; x++) {
                int id = level[y][x];
                if (isAnimation(id)){
                    g.drawImage(getSprite(id,animatinIndex), x * 32, y * 32, null);
                }else
                    g.drawImage(getSprite(id), x * 32, y * 32, null);
            }
        }
    }

    private void drawSelectedTile(Graphics g) {
        if (selectedTile != null && drawSelect){
            int tileX = mouseX / 32;
            int tileY = mouseY / 32;
            if (selectedTile.getId() >=0) {
                g.drawImage(selectedTile.getSprite(),mouseX,mouseY,32,32,null);
            }else {
                int id = level[tileY][tileX];
                if (getGame().getTileManager().getTile(id).getTileType() == ROAD){
                    g.drawImage(selectedTile.getSprite(),mouseX,mouseY,32,32,null);
                }
            }

        }
    }

    public void setSelectedTile(Tile selectedTile){
        this.selectedTile = selectedTile;
        drawSelect = true;
    }

    public void saveLevel() {
        LoadSave.saveLevel(level,start,end);
        getGame().getPlaying().setLevel(level);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (y>=640){
            toolBar.mouseClicked(x,y);
        }else {
            changeTile(mouseX,mouseY);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (y>=640){
            toolBar.mouseMoved(x,y);
            drawSelect = false;
        }else {
            mouseX = (x / 32)*32;
            mouseY = (y / 32)*32;
            drawSelect = true;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (y>=640){
            toolBar.mousePressed(x,y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        if (y>=640){
            toolBar.mouseReleased(x,y);
        }
    }

    @Override
    public void mouseDragged(int x, int y) {
        if (!(y>=640)){
            changeTile(x,y);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R){
            toolBar.rotateSprite();
        }
    }

}
