package de.wroracer.towerdefensegame.enemis;

import de.wroracer.towerdefensegame.managers.EnemyManager;
import de.wroracer.towerdefensegame.util.Constants;

import java.awt.*;
import java.util.List;

import static de.wroracer.towerdefensegame.util.Constants.Direction.*;

public abstract class Enemy {

    protected float x,y;
    protected Rectangle bounds;
    protected int health;
    protected int maxHealth;
    protected int id;
    protected int enemyType;
    protected boolean alive = true;
    protected int slowTickLimit = 120;
    protected int slowTick = slowTickLimit;

    protected int lastDirection;

    protected EnemyManager enemyManager;

    //private List<Position> path;

    public Enemy(float x, float y, int id, int enemyType, EnemyManager enemyManager){
        this.x =x;
        this.y = y;
        this.id = id;
        this.enemyType = enemyType;
        bounds = new Rectangle((int)x,(int)y,32,32);
        lastDirection = -1;
        this.enemyManager = enemyManager;
        //path = Astar.astar(Astar.lvlToAstarMaze(enemyManager.getPlaying()),enemyManager.getPlaying().getStartPos(),enemyManager.getPlaying().getEndPos());
        //System.out.println(path);
        setStartHealth();
    }

    private void setStartHealth(){
        health = Constants.Enemies.getStartHealth(enemyType);
        maxHealth = health;
    }

    public float getHealthBarPercent(){
        return health/(float)maxHealth;
    }

    public void hurt(int dmg){
        this.health -= dmg;
        if (health <= 0){
            alive = false;
            enemyManager.rewardPlayer(enemyType);
        }
    };

    public void kill(){
        alive = false;
        health = 0;
    };

    public  void slow(){
        slowTick = 0;
    };

    public void move(float speed,int direction){
        lastDirection = direction;

        if (slowTick < slowTickLimit){
            slowTick++;
            speed *= 0.5f;
        }

        switch (direction){
            case LEFT:
                this.x-=speed;
                break;
            case UP:
                this.y-=speed;
                break;
            case RIGHT:
                this.x+=speed;
                break;
            case DOWN:
                this.y+=speed;
                break;
        }
        updateHitBox();
    }

    private void updateHitBox(){
        bounds.x = (int) x;
        bounds.y = (int) y;
    };

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getHealth() {
        return health;
    }

    public int getId() {
        return id;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public int getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(int lastDirection) {
        this.lastDirection = lastDirection;
    }

   /**
    *  Don´t use this for move
    */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isAlive() {
        return alive;
    }


    public boolean isSlowed(){
        return slowTick<slowTickLimit;
    }

}
