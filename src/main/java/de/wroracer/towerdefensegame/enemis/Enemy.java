package de.wroracer.towerdefensegame.enemis;

import static de.wroracer.towerdefensegame.util.Constants.Direction.DOWN;
import static de.wroracer.towerdefensegame.util.Constants.Direction.LEFT;
import static de.wroracer.towerdefensegame.util.Constants.Direction.RIGHT;
import static de.wroracer.towerdefensegame.util.Constants.Direction.UP;

import java.awt.Rectangle;
import java.util.List;

import de.wroracer.towerdefensegame.managers.EnemyManager;
import de.wroracer.towerdefensegame.objects.PathPoint;
import de.wroracer.towerdefensegame.util.Constants;
import de.wroracer.towerdefensegame.util.Utilz;
import de.wroracer.towerdefensegame.util.Constants.Enemies;
import de.wroracer.towerdefensegame.util.astar.Astar;

public abstract class Enemy {

    protected float x, y;
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

    private List<PathPoint> path;

    public Enemy(float x, float y, int id, int enemyType, EnemyManager enemyManager) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.enemyType = enemyType;
        bounds = new Rectangle((int) x, (int) y, 32, 32);
        lastDirection = -1;
        this.enemyManager = enemyManager;

        int[][] aStarLevel = Utilz.toAstarLevel(enemyManager.getLevel(),
                enemyManager.getPlaying().getGame().getTileManager());

        path = Astar.aStar(aStarLevel, enemyManager.getPlaying().getStart(), enemyManager.getPlaying().getEnd());
        // System.out.println(path);
        setStartHealth();
    }

    public void update() {
        // calculate path to next point
        int direction;

        if (path.size() <= 0) {
            this.alive = false;
            this.kill();
            return;
        }

        int nX = (int) Math.floor(x / 32);
        int nY = (int) Math.floor((y / 32) + 0);

        PathPoint nextPoint = path.get(0);
        if (nextPoint.getX() == nX) {
            if (nextPoint.getY() < nY) {
                direction = UP;
            } else {
                direction = DOWN;
            }
        } else if (nextPoint.getY() == nY) {
            if (nextPoint.getX() < nX) {
                direction = LEFT;
            } else {
                direction = RIGHT;
            }
        } else {
            if (nextPoint.getX() < nX) {
                if (nextPoint.getY() < nY) {
                    direction = UP;
                } else {
                    direction = DOWN;
                }
            } else {
                if (nextPoint.getY() < nY) {
                    direction = LEFT;
                } else {
                    direction = RIGHT;
                }
            }
        }

        if (nX == nextPoint.getX() && nY == nextPoint.getY()) {
            path.remove(0);
            update();
            return;
        }

        move(Enemies.getSpeed(enemyType), direction);

    }

    private boolean isOpposite(int direction) {
        if (direction == -1 || lastDirection == -1) {
            return false;
        }
        return (direction == LEFT && lastDirection == RIGHT) || (direction == RIGHT && lastDirection == LEFT)
                || (direction == UP && lastDirection == DOWN) || (direction == DOWN && lastDirection == UP);
    }

    private void setStartHealth() {
        health = Constants.Enemies.getStartHealth(enemyType);
        maxHealth = health;
    }

    public float getHealthBarPercent() {
        return health / (float) maxHealth;
    }

    public void hurt(int dmg) {
        this.health -= dmg;
        if (health <= 0) {
            alive = false;
            enemyManager.rewardPlayer(enemyType);
        }
    };

    public void kill() {
        alive = false;
        health = 0;
    };

    public void slow() {
        slowTick = 0;
    };

    public void move(float speed, int direction) {
        lastDirection = direction;

        if (slowTick < slowTickLimit) {
            slowTick++;
            speed *= 0.5f;
        }

        switch (direction) {
            case LEFT:
                this.x -= speed;
                break;
            case UP:
                this.y -= speed;
                break;
            case RIGHT:
                this.x += speed;
                break;
            case DOWN:
                this.y += speed;
                break;
        }
        updateHitBox();
    }

    private void updateHitBox() {
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

    public boolean isSlowed() {
        return slowTick < slowTickLimit;
    }

}
