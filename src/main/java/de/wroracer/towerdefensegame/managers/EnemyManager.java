package de.wroracer.towerdefensegame.managers;

import de.wroracer.towerdefensegame.enemis.*;
import de.wroracer.towerdefensegame.objects.PathPoint;
import de.wroracer.towerdefensegame.scenes.Playing;
import de.wroracer.towerdefensegame.util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static de.wroracer.towerdefensegame.util.Constants.*;
import static de.wroracer.towerdefensegame.util.Constants.Enemies.*;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[] enemyImgs;
    private BufferedImage slowEffect;

    private ArrayList<Enemy> enemies = new ArrayList<>();

    private int hpBarWith = 20;


    private PathPoint start,end;

    public EnemyManager(Playing playing, PathPoint start, PathPoint end){
        this.playing = playing;
        this.start = start;
        this.end = end;
        enemyImgs = new BufferedImage[4];
        /*addEnemy(ORC);
        addEnemy(BAT);
        addEnemy(KNIGHT);
        addEnemy(WOLF);*/
        loadEnemyImgs();
        loadEffectImgs();
    }

    private void loadEffectImgs() {
        slowEffect = LoadSave.getSpriteAtlas().getSubimage(32*9,32*2,32,32);
    }

    private void loadEnemyImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        for (int i = 0; i < 4; i++) {
            enemyImgs[i] = atlas.getSubimage(i * 32,32,32,32);
        }
    }

    public void spawnEnemy(int type) {
        addEnemy(type);
    }

    private void addEnemy(int enemyType){
        int x = start.getxCord() *32;
        int y = start.getyCord() *32;
        switch (enemyType){
            case ORC:
                enemies.add(new Orc(x,y,enemies.size(),this));
                break;
            case BAT:
                enemies.add(new Bat(x,y,enemies.size(),this));
                break;
            case KNIGHT:
                enemies.add(new Knight(x,y,enemies.size(),this));
                break;
            case WOLF:
                enemies.add(new Wolf(x,y,enemies.size(),this));
                break;
        }
    }

    public void update(){

        for (Enemy e : enemies) {
            //is next tile Road(pos, dir)
            if (e.isAlive())
                updateEnemyMove(e);
        }
    }


    private void updateEnemyMove(Enemy e) {
        //e pos
        //e dir
        //tile at new pos
        if (e.getLastDirection() == -1){
            setNewDirectionAndMove(e);
        }
        int newX = (int) ((int) e.getX() + getSpeedAndWidth(e.getLastDirection(),e.getEnemyType()));
        int newY = (int) ((int) e.getY() + getSpeedAndHeight(e.getLastDirection(),e.getEnemyType()));
        if (getTileType(newX,newY) == Tiles.ROAD){
            e.move(Enemies.getSpeed(e.getEnemyType()),e.getLastDirection());
        }else if (isAtEnd(e)){
            e.kill();
            playing.removeOneLive();
            //Reached the end
        }else {
            setNewDirectionAndMove(e);
        }
    }

    private void setNewDirectionAndMove(Enemy e) {
        int dir = e.getLastDirection();
        //move into current tile
        int xCord = (int) (e.getX()/32);
        int yCord = (int) (e.getY()/32);
        fixEnemyOffsetTile(e,dir,xCord,yCord);
        if (isAtEnd(e)){
            return;
        }
        if (dir == Direction.LEFT || dir == Direction.RIGHT){
            int newY = (int) (e.getY() + getSpeedAndHeight(Direction.UP,e.getEnemyType()));
            if (getTileType((int) e.getX(),newY)== Tiles.ROAD){
                e.move(Enemies.getSpeed(e.getEnemyType()),Direction.UP);
            }else {
                e.move(Enemies.getSpeed(e.getEnemyType()),Direction.DOWN);
            }
        }else {
            int newX = (int) (e.getX() + getSpeedAndWidth(Direction.RIGHT,e.getEnemyType()));
            if (getTileType(newX, (int) e.getY())== Tiles.ROAD){
                e.move(Enemies.getSpeed(e.getEnemyType()),Direction.RIGHT);
            }else {
                e.move(Enemies.getSpeed(e.getEnemyType()),Direction.LEFT);
            }
        }
    }

    private void fixEnemyOffsetTile(Enemy e, int dir, int xCord, int yCord) {
        switch (dir){
            case Direction.RIGHT:
                //if (xCord<19){
                //    xCord++;
                //}
                break;
            case Direction.DOWN:
                if (yCord>19){
                    yCord++;
                }
                break;
        }
        e.setPosition(xCord*32,yCord*32);
    }


    private boolean isAtEnd(Enemy e) {
        if (e.getX() == end.getxCord()*32){
            return e.getY() == end.getyCord() * 32;
        }
        return false;
    }

    private int getTileType(int x, int y) {
        return playing.getTileType(x,y);
    }

    private float getSpeedAndWidth(int dir,int enemyType) {
    if (dir == Direction.LEFT)
            return -Enemies.getSpeed(enemyType);
        else if (dir==Direction.RIGHT)
            return Enemies.getSpeed(enemyType)+32;
        return 0;
    }

    private float getSpeedAndHeight(int dir,int enemyType) {
        if (dir == Direction.UP)
            return -Enemies.getSpeed(enemyType);
        else if (dir==Direction.DOWN)
            return Enemies.getSpeed(enemyType)+32;
        return 0;
    }

    public void render(Graphics g){
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                renderEnemy(g, enemy);
                renderHealthBar(g, enemy);
                renderEffects(g,enemy);
            }
        }
    }

    private void renderEffects(Graphics g, Enemy e) {
        if (e.isSlowed()){
            g.drawImage(slowEffect,(int)e.getX(),(int)e.getY(),null);
        }
    }

    private void renderHealthBar(Graphics g, Enemy e) {
        g.setColor(new Color(131, 2, 2));
        g.fillRect((int)e.getX() + 16 - (hpBarWith/2),(int)e.getY() - 10,hpBarWith,3);
        g.setColor(Color.RED);
        g.fillRect((int)e.getX() + 16 - (hpBarWith/2),(int)e.getY() - 10,getNewBarWith(e),3);
    }

    private int getNewBarWith(Enemy e){
        return (int) ( hpBarWith * e.getHealthBarPercent());
    }

    private void renderEnemy(Graphics g, Enemy e) {
        g.drawImage(enemyImgs[e.getEnemyType()],(int) e.getX(),(int)e.getY(),null);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public int getAmountOfAliveEnemies() {
        int size = 0;
        for (Enemy e : enemies) {
            if (e.isAlive())
                size++;
        }
        return size;
    }

    public void rewardPlayer(int enemyType) {
        playing.rewardPlayer(enemyType);
    }

    public void reset() {
        enemies.clear();
    }

    public Playing getPlaying() {
        return playing;
    }
}
