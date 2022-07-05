package de.wroracer.towerdefensegame.managers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import de.wroracer.towerdefensegame.enemis.Enemy;
import de.wroracer.towerdefensegame.objects.Projectile;
import de.wroracer.towerdefensegame.objects.Tower;
import de.wroracer.towerdefensegame.scenes.Playing;
import de.wroracer.towerdefensegame.util.Constants.Projectiles;
import de.wroracer.towerdefensegame.util.Constants.Towers;
import de.wroracer.towerdefensegame.util.LoadSave;

public class ProjectileManager {

    private Playing playing;

    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private BufferedImage[] projIMgs;
    private BufferedImage[] exploImgs;
    private int projId = 0;

    private ArrayList<Explosion> explosions = new ArrayList<>();

    public ProjectileManager(Playing playing) {
        this.playing = playing;
        initImgs();
    }

    private void initImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        projIMgs = new BufferedImage[3];
        for (int i = 0; i < 3; i++) {
            projIMgs[i] = atlas.getSubimage((7 + i) * 32, 32, 32, 32);
        }
        initExplosion(atlas);
    }

    private void initExplosion(BufferedImage atlas) {
        exploImgs = new BufferedImage[7];
        for (int i = 0; i < exploImgs.length; i++) {
            exploImgs[i] = atlas.getSubimage(i * 32, 32 * 2, 32, 32);
        }
    }

    public void newProjectile(Tower t, Enemy e) {
        int type = getProjectileType(t);

        int xDist = (int) (t.getX() - e.getX());
        int yDist = (int) (t.getY() - e.getY());
        int totDist = Math.abs(xDist) + Math.abs(yDist);

        float xPer = Math.abs(xDist) / (float) totDist;

        float xSpeed = xPer * Projectiles.getSpeed(type);
        float ySpeed = Projectiles.getSpeed(type) - xSpeed;

        if (t.getX() > e.getX()) {
            xSpeed *= -1;
        }
        if (t.getY() > e.getY()) {
            ySpeed *= -1;
        }

        float rotate = 0;

        if (type == Projectiles.ARROW) {
            float arcValue = (float) Math.atan(yDist / (float) xDist);
            rotate = (float) Math.toDegrees(arcValue);
            if (xDist < 0) {
                rotate += 180;
            }
        }

        for (Projectile p : projectiles) {
            if (!p.isActive()) {
                if (p.getProjectileType() == type) {
                    p.reuse(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, t.getDmg(), rotate);
                    return;
                }
            }
        }

        projectiles
                .add(new Projectile(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, t.getDmg(), rotate, projId++, type));
    }

    public void update() {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                p.move();
                if (isProjHittingEnemy(p)) {
                    p.setActive(false);
                    if (p.getProjectileType() == Projectiles.BOMB) {
                        explosions.add(new Explosion(p.getPos()));
                        explodeOnEnemies(p);
                    }
                } else if (isProjOutOfBounds(p)) {
                    p.setActive(false);
                }
            }
        }
        for (Explosion e : explosions) {
            if (e.getIndex() < 7)
                e.update();
        }
    }

    private void explodeOnEnemies(Projectile p) {
        playing.getGame().getSoundManager().playSound("bumm");
        for (Enemy e : playing.getEnemyManager().getEnemies()) {
            if (e.isAlive()) {
                float radius = 40.0f;

                float xDist = Math.abs(p.getPos().x - e.getX());
                float yDist = Math.abs(p.getPos().y - e.getY());

                float realDist = (float) Math.hypot(xDist, yDist);

                if (realDist <= radius) {
                    e.hurt(p.getDmg());
                }
            }
        }
    }

    private boolean isProjOutOfBounds(Projectile p) {
        if (p.getPos().getX() >= 0)
            if (p.getPos().getX() <= 640)
                if (p.getPos().getY() >= 0)
                    if (p.getPos().getY() <= 800)
                        return false;
        return true;
    }

    private boolean isProjHittingEnemy(Projectile p) {
        for (Enemy e : playing.getEnemyManager().getEnemies()) {
            if (e.isAlive()) {
                if (e.getBounds().contains(p.getPos())) {
                    e.hurt(p.getDmg());
                    if (p.getProjectileType() == Projectiles.CHAINS) {
                        e.slow();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (Projectile p : projectiles) {
            if (p.isActive()) {
                if (p.getProjectileType() == Projectiles.ARROW) {
                    g2d.translate(p.getPos().x, p.getPos().y);
                    g2d.rotate(Math.toRadians(p.getRotation()));
                    g2d.drawImage(projIMgs[p.getProjectileType()], -16, -16, null);
                    g2d.rotate(-Math.toRadians(p.getRotation()));
                    g2d.translate(-p.getPos().x, -p.getPos().y);
                } else {
                    g2d.drawImage(projIMgs[p.getProjectileType()], (int) p.getPos().x - 16, (int) p.getPos().y - 16,
                            null);
                }
            }
        }

        renderExplosions(g2d);

    }

    private void renderExplosions(Graphics2D g2d) {
        for (Explosion e : explosions) {
            if (e.getIndex() < 7)
                g2d.drawImage(exploImgs[e.getIndex()], (int) e.getPos().x - 16, (int) e.getPos().y - 16, null);
        }
    }

    private int getProjectileType(Tower t) {
        switch (t.getTowerType()) {
            case Towers.ARCHER:
                return Projectiles.ARROW;
            case Towers.CANON:
                return Projectiles.BOMB;
            case Towers.WIZARD:
                return Projectiles.CHAINS;
            default:
                return 0;
        }
    }

    public void reset() {
        projectiles.clear();
        explosions.clear();
        projId = 0;
    }

    public class Explosion {

        private Point2D.Float pos;

        private int exploTick = 0, exploIndex = 0;

        public Explosion(Point2D.Float pos) {
            this.pos = pos;
        }

        public void update() {
            exploTick++;
            if (exploTick >= 12) {
                exploTick = 0;
                exploIndex++;
            }
        }

        public Point2D.Float getPos() {
            return pos;
        }

        public int getIndex() {
            return exploIndex;
        }
    }
}
