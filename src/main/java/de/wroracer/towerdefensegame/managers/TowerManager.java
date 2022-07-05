package de.wroracer.towerdefensegame.managers;

import de.wroracer.towerdefensegame.enemis.Enemy;
import de.wroracer.towerdefensegame.objects.Tower;
import de.wroracer.towerdefensegame.scenes.Playing;
import de.wroracer.towerdefensegame.util.LoadSave;
import de.wroracer.towerdefensegame.util.Utilz;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static de.wroracer.towerdefensegame.util.Constants.Towers.*;

public class TowerManager {

    private Playing playing;
    private BufferedImage[] towerImgs;

    private ArrayList<Tower> towers = new ArrayList<>();

    private int towerAmount = 0;


    public TowerManager(Playing playing){
        this.playing = playing;

        loadTowerImgs();

        initTowers();
    }

    private void initTowers() {

    }

    private void loadTowerImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        towerImgs = new BufferedImage[3];
        for (int i = 0;i<3;i++){
            towerImgs[i] = atlas.getSubimage((4+i)*32,32,32,32);
        }
    }

    public void addTower(Tower tower,int x,int y) {
        towers.add(new Tower(x,y,towerAmount++,tower.getTowerType()));
    }

    public void removeTower(Tower tower) {
        int index = -1;
        for (int i = 0; i < towers.size(); i++) {
            if (towers.get(i).getId() == tower.getId()){
                index = i;
            }
        }
        towers.remove(index);
    }

    public void upgradeTower(Tower tower) {
        for (Tower t : towers) {
            if (t.getId() == tower.getId()){
                t.upgradeTower();
            }
        }
    }
    
    public void render(Graphics g){
        for (Tower tower : towers) {
            g.drawImage(towerImgs[tower.getTowerType()],tower.getX(),tower.getY(),null);
        }
    }

    public Tower getTowerAt(int x, int y) {
        for (Tower t : towers) {
            if (t.getX() == x && t.getY() == y)
                return t;
        }
        return null;
    }

    public void update() {
        for (Tower t : towers) {
            t.update();
            attackEnemyIfClose(t);
        }
    }

    private void attackEnemyIfClose(Tower t) {
            for (Enemy e : playing.getEnemyManager().getEnemies()) {
                if (e.isAlive())
                    if (isEnemyInRange(t,e)){
                        if (t.isCooldownOver()){
                            if (t.getTowerType() == WIZARD){
                                if (!e.isSlowed()){
                                    playing.shootEnemy(t, e);
                                    t.resetCoolDown();
                                }
                            }else {
                                playing.shootEnemy(t, e);
                                t.resetCoolDown();
                            }
                        }
                    }
            }
    }

    private boolean isEnemyInRange(Tower t, Enemy e) {
        int range = Utilz.getHypoDistance(t.getX(),t.getY(),e.getX(),e.getY());
        return range < t.getRange();
    }

    public BufferedImage[] getTowerImgs() {
        return towerImgs;
    }


    public void reset() {
        towers.clear();
        towerAmount = 0;
    }
}
