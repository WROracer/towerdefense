package de.wroracer.towerdefensegame.objects;

import java.awt.geom.Point2D;

public class Projectile {
    private Point2D.Float pos;
    private int id, projectileType, dmg;
    private boolean active = true;

    private float xSpeed, ySpeed, rotation;

    public Projectile(float x, float y, float xSpeed, float ySpeed, int dmg, float rotation, int id,
            int projectileType) {
        this.pos = new Point2D.Float(x, y);
        this.id = id;
        this.projectileType = projectileType;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.dmg = dmg;
        this.rotation = rotation;
    }

    public void move() {
        pos.x += xSpeed;
        pos.y += ySpeed;
    }

    public int getId() {
        return id;
    }

    public Point2D.Float getPos() {
        return pos;
    }

    public int getProjectileType() {
        return projectileType;
    }

    public void setPos(Point2D.Float pos) {
        this.pos = pos;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getDmg() {
        return dmg;
    }

    public float getRotation() {
        return rotation;
    }

    public void reuse(int x, int y, float xSpeed, float ySpeed, int dmg, float rotate) {
        this.pos = new Point2D.Float(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.dmg = dmg;
        this.rotation = rotate;
        active = true;
    }
}
