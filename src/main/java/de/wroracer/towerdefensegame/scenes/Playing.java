package de.wroracer.towerdefensegame.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import de.wroracer.towerdefensegame.Game;
import de.wroracer.towerdefensegame.enemis.Enemy;
import de.wroracer.towerdefensegame.managers.EnemyManager;
import de.wroracer.towerdefensegame.managers.ProjectileManager;
import de.wroracer.towerdefensegame.managers.TowerManager;
import de.wroracer.towerdefensegame.managers.WaveManager;
import de.wroracer.towerdefensegame.objects.PathPoint;
import de.wroracer.towerdefensegame.objects.Tower;
import de.wroracer.towerdefensegame.ui.ActionBar;
import de.wroracer.towerdefensegame.util.Constants;
import de.wroracer.towerdefensegame.util.LoadSave;

public class Playing extends GameScene implements SceneMethods {

    private int[][] level;

    private ActionBar actionBar;
    private int mouseX, mouseY;

    private EnemyManager enemyManager;
    private TowerManager towerManager;
    private ProjectileManager projManager;
    private WaveManager waveManager;

    private int goldTick;

    private Tower selectedTower;

    private PathPoint start, end;

    private boolean gamePaused;

    public Playing(Game game) {
        super(game);
        loadDefaultLevel();
        actionBar = new ActionBar(0, 640, 640, 160, this);
        enemyManager = new EnemyManager(this, start, end);
        towerManager = new TowerManager(this);
        projManager = new ProjectileManager(this);
        waveManager = new WaveManager(this);
    }

    private void loadDefaultLevel() {
        level = LoadSave.getLevelData();
        ArrayList<PathPoint> points = LoadSave.getLevelPathPoints();
        start = points.get(0);
        end = points.get(1);
        LoadSave.saveLevel(level, start, end);
    }

    public void update() {

        if (!gamePaused) {

            updateTick();
            waveManager.update();

            //Gold Tick
            goldTick++;
            if (goldTick % (60 * 3) == 0) {
                actionBar.addGold(1);
            }

            if (isAllEnemysDead()) {
                if (isThereMoreWaves()) {
                    waveManager.startWaveTimer();
                    if (isWaveTimerOver()) {
                        waveManager.increaseWaveIndex();
                        enemyManager.getEnemies().clear();
                        waveManager.resetEnemyIndex();
                    }
                }
            }

            if (isTimeForNewEnemy()) {
                spawnEnemy();
            }
            enemyManager.update();
            towerManager.update();
            projManager.update();
        }

    }

    private boolean isWaveTimerOver() {
        return waveManager.isWaveTimerOver();
    }

    private boolean isThereMoreWaves() {
        return waveManager.isThereMoreWaves();
    }

    private boolean isAllEnemysDead() {
        if (waveManager.isThereMoreEnemiesInWave()) {
            return false;
        }
        for (Enemy e : enemyManager.getEnemies()) {
            if (e.isAlive()) {
                return false;
            }
        }
        return true;
    }

    private void spawnEnemy() {
        enemyManager.spawnEnemy(waveManager.getNextEnemy());
    }

    private boolean isTimeForNewEnemy() {
        if (waveManager.isTimeForNewEnemy()) {
            return waveManager.isThereMoreEnemiesInWave();
        }
        return false;
    }

    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower = selectedTower;
    }

    @Override
    public void render(Graphics g) {
        renderLevel(g);
        actionBar.render(g);

        enemyManager.render(g);
        towerManager.render(g);
        projManager.render(g);

        renderSelectedTower(g);
        renderHighlight(g);

    }

    private void renderHighlight(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(mouseX, mouseY, 32, 32);
    }

    private void renderSelectedTower(Graphics g) {
        if (selectedTower != null) {
            if (isTileGrass(mouseX, mouseY)) {
                if (getTowerAt(mouseX, mouseY) == null) {
                    g.setColor(new Color(0, 255, 4, 150));
                    g.fillRect(mouseX, mouseY, 32, 32);
                    g.drawImage(towerManager.getTowerImgs()[selectedTower.getTowerType()], mouseX, mouseY, null);
                } else {
                    g.setColor(new Color(255, 115, 0, 150));
                    g.fillRect(mouseX, mouseY, 32, 32);
                    g.drawImage(towerManager.getTowerImgs()[selectedTower.getTowerType()], mouseX, mouseY, null);
                }
            } else {
                g.setColor(new Color(255, 0, 0, 150));
                g.fillRect(mouseX, mouseY, 32, 32);
                g.drawImage(towerManager.getTowerImgs()[selectedTower.getTowerType()], mouseX, mouseY, null);
            }
        }
    }

    private void renderLevel(Graphics g) {
        for (int y = 0; y < level.length; y++) {
            for (int x = 0; x < level[y].length; x++) {
                int id = level[y][x];
                if (isAnimation(id)) {
                    g.drawImage(getSprite(id, animatinIndex), x * 32, y * 32, null);
                } else
                    g.drawImage(getSprite(id), x * 32, y * 32, null);
            }
        }
    }

    public int getTileType(int x, int y) {
        int xCord = x / 32;
        int yCord = y / 32;

        if (xCord < 0 || xCord > 19) {
            return 0;
        }
        if (yCord < 0 || yCord > 19) {
            return 0;
        }

        int id = level[y / 32][x / 32];
        return getGame().getTileManager().getTile(id).getTileType();
    }

    public int getTileTypeByCord(int xCord, int yCord) {
        if (xCord < 0 || xCord > 19) {
            return 0;
        }
        if (yCord < 0 || yCord > 19) {
            return 0;
        }

        int id = level[yCord][xCord];
        return getGame().getTileManager().getTile(id).getTileType();
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (y >= 640) {
            actionBar.mouseClicked(x, y);
        } else {
            if (selectedTower != null) {
                if (isTileGrass(mouseX, mouseY)) {
                    if (getTowerAt(mouseX, mouseY) == null) {
                        towerManager.addTower(selectedTower, mouseX, mouseY);
                        removeGold(selectedTower.getTowerType());
                        selectedTower = null;
                    }
                }
            } else {
                //get tower if exits on xy
                Tower t = getTowerAt(mouseX, mouseY);
                actionBar.displayTower(t);

            }
        }
    }

    private void removeGold(int towerType) {
        actionBar.playForTower(towerType);
    }

    public void removeTower(Tower tower) {
        towerManager.removeTower(tower);
    }

    public void upgradeTower(Tower tower) {
        towerManager.upgradeTower(tower);
    }

    private Tower getTowerAt(int mouseX, int mouseY) {
        return towerManager.getTowerAt(mouseX, mouseY);
    }

    private boolean isTileGrass(int x, int y) {
        int tileType = getTileType(x, y);
        return tileType == Constants.Tiles.GRASS;
    }

    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }

    public boolean isGamePaused() {
        return gamePaused;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            selectedTower = null;
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (y >= 640) {
            actionBar.mouseMoved(x, y);
        } else {
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (y >= 640) {
            actionBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        actionBar.mouseReleased(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    public void rewardPlayer(int enemyType) {
        actionBar.addGold(Constants.Enemies.getReward(enemyType));
    }

    public void setLevel(int[][] level) {
        this.level = level;
    }

    public TowerManager getTowerManager() {
        return towerManager;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public WaveManager getWaveManager() {
        return waveManager;
    }

    public void shootEnemy(Tower t, Enemy e) {
        projManager.newProjectile(t, e);
    }

    public void removeOneLive() {
        actionBar.removeOneLive();
    }

    public void resetEverything() {
        actionBar.resetEverything();

        //managers
        enemyManager.reset();
        towerManager.reset();
        projManager.reset();
        waveManager.reset();
        mouseX = 0;
        mouseY = 0;
        selectedTower = null;
        goldTick = 0;
        gamePaused = false;
    }

    public int[][] getLevel() {
        return this.level;
    }

    public PathPoint getStart() {
        return start;
    }

    public PathPoint getEnd() {
        return end;
    }
}
